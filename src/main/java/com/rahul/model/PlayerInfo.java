package com.rahul.model;

import jakarta.websocket.Session;

public class PlayerInfo {
    private String username;
    private String userId;
    private String gameId;
    private Session opponent;
    private Session session;   // add this
    private boolean ready;
    private boolean inGame;
    private boolean playAgainRequested;
    
    public PlayerInfo(String username, String userId) {
    	this.username = username;
    	this.userId = userId;
    }
    public boolean isPlayAgainRequested() { return playAgainRequested; }
    public void setPlayAgainRequested(boolean playAgainRequested) { this.playAgainRequested = playAgainRequested; }

    public Session getOpponent() {
		return opponent;
	}

	public void setOpponent(Session opponent) {
		this.opponent = opponent;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}


    public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public PlayerInfo(String userId, String username, String gameId, Session session) {
		this.userId = userId;
		this.username = username;
		this.session = session;
		this.gameId = gameId;
	}

    public PlayerInfo() {
		// TODO Auto-generated constructor stub
	}
	// Getters & setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Session getSession() { return session; }
    public void setSession(Session session) { this.session = session; }

    @Override
    public String toString() {
        return username;  // nice for printing lobby
    }

}
