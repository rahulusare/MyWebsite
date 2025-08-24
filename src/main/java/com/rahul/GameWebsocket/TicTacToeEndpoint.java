package com.rahul.GameWebsocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.rahul.model.*;

@ServerEndpoint("/tictactoe")
public class TicTacToeEndpoint {

	// Active sessions and player info
	private static final Set<Session> sessions = ConcurrentHashMap.newKeySet();
	private static final Map<Session, PlayerInfo> playersInfo = new ConcurrentHashMap<>();

	// Game management
	private static final Map<String, GameSession> pendingGames = new ConcurrentHashMap<>();
	private static final Map<Session, Session> opponents = new ConcurrentHashMap<>();

	private static final AtomicInteger gameCounter = new AtomicInteger(1);

	// --------------------- WebSocket Events ---------------------

	@OnOpen
	public void onOpen(Session session) {
		sessions.add(session);
		//System.out.println("Joined Game: " + session);

	}

	@OnMessage
	public void onMessage(String message, Session session) {
		try {
			JsonObject json = Json.createReader(new java.io.StringReader(message)).readObject();
			String type = json.getString("type");

			switch (type) {

			case "join":
				String gameId = json.getString("gameId");
				String username = json.getString("username");
				String userId = json.getString("userId");
				System.out.println(gameId + " " + username + " " + userId);
				System.out.println("111111111111111111111111111111111111111");
				GameSession game = pendingGames.get(gameId);
				
				 PlayerInfo newPlayer = new PlayerInfo(userId, username, gameId, session);
				    playersInfo.put(session, newPlayer);

				if (game == null) {
					game = new GameSession();
					game.player1 = session;
					game.gameId = gameId;
					pendingGames.put(gameId, game);
				} else {
					game.player2 = session;
					opponents.put(game.player1, game.player2);
					opponents.put(game.player2, game.player1);

					sendAssignSymbols(game);
					notifyGameStart(game);
				}
				break;

			case "playAgain":
				handlePlayAgain(session);
				break;

			case "move":
				Session opponentM = opponents.get(session);
				if (opponentM != null && opponentM.isOpen()) {
					opponentM.getBasicRemote().sendText(message);
				}
				break;

			case "reset":
				Session opponentR = opponents.get(session);
				if (opponentR != null && opponentR.isOpen()) {
					opponentR.getBasicRemote().sendText("{\"type\":\"reset\"}");
				}
				break;
				
			case "chat":
				String text = json.getString("text");
				Session opponentSession = opponents.get(session);
				PlayerInfo from = playersInfo.get(session);
				if(opponentSession != null && opponentSession.isOpen()) {
					JsonObject chatMsg = Json.createObjectBuilder()
							.add("type", "chat")
							.add("from", from.getUsername())
							.add("text", text)
							.build();
					
					opponentSession.getBasicRemote().sendText(chatMsg.toString());
				}
				break;
				
			// When opponent responds with OK or Cancel
			case "playAgainResponse":
				boolean accepted = json.getBoolean("accepted");
				Session opponent = opponents.get(session);

				if (opponent != null) {
					PlayerInfo player = playersInfo.get(session);
					PlayerInfo oppPlayer = playersInfo.get(opponent);

					if (accepted && oppPlayer.isPlayAgainRequested()) {
						// Both agreed → create new game
						String newGameId = "GAME" + gameCounter.getAndIncrement();
						GameSession newGame = new GameSession();
						newGame.player1 = session;
						newGame.player2 = opponent;
						newGame.gameId = newGameId;
						pendingGames.put(newGameId, newGame);

						// Reset flags
						player.setPlayAgainRequested(false);
						oppPlayer.setPlayAgainRequested(false);

						// Assign opponents
						opponents.put(session, opponent);
						opponents.put(opponent, session);

						// Start new game
						sendAssignSymbols(newGame);
						notifyGameStart(newGame);
					} else if (!accepted) {
						JsonObject dec = Json.createObjectBuilder()
								.add("type", "playAgainDeclined")
								.add("message", "Your opponent declined to play again.")
								.build();
				
						opponent.getBasicRemote().sendText(dec.toString());
						player.setPlayAgainRequested(false);
						oppPlayer.setPlayAgainRequested(false);
					}
				}
				break;

			default:
				System.out.println("Unknown message type: " + type);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session) {
		sessions.remove(session);

		// Remove from opponents
		Session opponent = opponents.remove(session);
		if (opponent != null) {
			opponents.remove(opponent);
			try {
				if (opponent.isOpen()) {
					opponent.getBasicRemote().sendText("{\"type\":\"opponentLeft\"}");
				}
			} catch (IOException ignored) {
			}
		}

		// Remove from pendingGames
		pendingGames.values().removeIf(g -> g.player1 == session || g.player2 == session);
		
		PlayerInfo player = playersInfo.get(session);
		// Remove player info
		playersInfo.remove(session);

		System.out.println("Player disconnected: " + player.getUsername());
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
	    System.out.println("Error for session " + session.getId() + ": " + throwable);
	    // Optional: cleanup
	    playersInfo.remove(session);
	    opponents.remove(session);
	}

	// --------------------- Helper Methods ---------------------

	private void sendAssignSymbols(GameSession game) throws IOException {
		JsonObject assignP1 = Json.createObjectBuilder()
				.add("type", "assignSymbol")
				.add("symbol", "X")
				.add("myTurn", true)
				.build();

		JsonObject assignP2 = Json.createObjectBuilder()
				.add("type", "assignSymbol")
				.add("symbol", "O")
				.add("myTurn", false)
				.build();

		game.player1.getBasicRemote().sendText(assignP1.toString());
		game.player2.getBasicRemote().sendText(assignP2.toString());
	}

	private void notifyGameStart(GameSession game) throws IOException {
		JsonObject startMsg = Json.createObjectBuilder()
				.add("type", "gameStart")
				.build();

		game.player1.getBasicRemote().sendText(startMsg.toString());
		game.player2.getBasicRemote().sendText(startMsg.toString());
	}

	public void handlePlayAgain(Session session) throws IOException {
	    PlayerInfo player = playersInfo.get(session);
	    if (player == null) {
	        System.out.println("⚠️ Player not found in playersInfo for session: " + session.getId());
	        return;
	    }
	   // System.out.println("✅ Player requested play again: " + player.getUsername());
	    // Mark that this player requested to play again
	    player.setPlayAgainRequested(true);

	    // Send request to opponent
	    Session opponent = opponents.get(session);
	    if (opponent != null && opponent.isOpen()) {
	    	JsonObject json = Json.createObjectBuilder()
	    			.add("type", "playAgainRequest")
	    			.add("from", player.getUsername())
	    			.build();
	    	
	        opponent.getBasicRemote().sendText(json.toString());
	       // System.out.println("Request sent!");
	    }
	}


}
