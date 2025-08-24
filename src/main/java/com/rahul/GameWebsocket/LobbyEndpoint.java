package com.rahul.GameWebsocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.json.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.rahul.model.*;

@ServerEndpoint("/lobby")
public class LobbyEndpoint {

    private static final Set<Session> sessions = ConcurrentHashMap.newKeySet();
    private static final Map<Session, PlayerInfo> playersInfo = new ConcurrentHashMap<>();
    private static final Map<String, GameSession> pendingGames = new ConcurrentHashMap<>();
    private static final AtomicInteger gameCounter = new AtomicInteger(1);

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        JsonObject json = Json.createReader(new StringReader(message)).readObject();
        String type = json.getString("type", "");

        switch (type) {
            case "join":
                handleJoin(session, json);
                break;

            case "invite":
                handleInvite(session, json);
                break;

            case "accept":
                handleAccept(session, json);
                break;

            default:
                System.out.println("Unknown message type: " + type);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        sessions.remove(session);
        playersInfo.remove(session);
       // System.out.println("Lobby Connection closed: " + session.getId());
        broadcastPlayers();
    }
    
    private void handleJoin(Session session, JsonObject json) {
        String username = json.getString("username", "Unknown");
        String userId = json.getString("userId", UUID.randomUUID().toString());
        System.out.println(username + " " + userId);

        PlayerInfo player = new PlayerInfo();
        player.setUsername(username);
        player.setUserId(userId);
        player.setSession(session);
        playersInfo.put(session, player);
        broadcastPlayers();
    }
    
    private void handleInvite(Session session, JsonObject json) {
        String toId = json.getString("to");
        String mode = json.getString("mode", "ttt");
        PlayerInfo fromPlayer = playersInfo.get(session);
        if (fromPlayer == null) return;

        String fromName = fromPlayer.getUsername();
        String fromId = fromPlayer.getUserId();

        for (Map.Entry<Session, PlayerInfo> entry : playersInfo.entrySet()) {
            if (entry.getValue().getUserId().equals(toId)) {
                try {
                    JsonObject inviteMsg = Json.createObjectBuilder()
                            .add("type", "invite")
                            .add("fromName", fromName)
                            .add("fromId", fromId)
                            .add("mode", mode)
                            .build();
                    entry.getKey().getBasicRemote().sendText(inviteMsg.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    
    private void handleAccept(Session session, JsonObject json) {
    	String accepterId = json.getString("from", "");
        String inviterId = json.getString("to", "");
        String mode = json.getString("mode", "ttt");
        System.out.println(inviterId + " Send Invite to " + accepterId);

        Session inviterSession = null, accepterSession = null;

        for (Map.Entry<Session, PlayerInfo> entry : playersInfo.entrySet()) {
            if (entry.getValue().getUserId().equals(inviterId)) inviterSession = entry.getKey();
            if (entry.getValue().getUserId().equals(accepterId)) accepterSession = entry.getKey();
        }

        if (inviterSession != null && accepterSession != null) {
            String gameId = "GAME" + gameCounter.getAndIncrement();
            GameSession game = new GameSession();
            game.player1 = inviterSession;
            game.player2 = accepterSession;
            game.gameId = gameId;
            pendingGames.put(gameId, game);

            // notify both players
            JsonObject startMsgInviter = Json.createObjectBuilder()
                    .add("type", "startGame")
                    .add("gameId", gameId)
                    .add("mode", mode)
                    .build();
            JsonObject startMsgAccepter = Json.createObjectBuilder()
                    .add("type", "startGame")
                    .add("gameId", gameId)
                    .add("mode", mode)
                    .build();

            try {
				inviterSession.getBasicRemote().sendText(startMsgInviter.toString());
				accepterSession.getBasicRemote().sendText(startMsgAccepter.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}

            System.out.println("Game created: " + gameId);
        }
    }

    private void broadcastPlayers() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (PlayerInfo p : playersInfo.values()) {
            arrayBuilder.add(Json.createObjectBuilder()
                    .add("username", p.getUsername())
                    .add("userId", p.getUserId()));
        }

        String json = Json.createObjectBuilder()
                .add("type", "users")
                .add("list", arrayBuilder)
                .build().toString();

        sessions.forEach(s -> {
            try {
                s.getBasicRemote().sendText(json);
            } catch (IOException ignored) {
            }
        });

        System.out.println("Players online: " + playersInfo.values());
    }
}
