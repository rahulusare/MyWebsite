package com.rahul.model;

import jakarta.websocket.Session;

public class GameSession {
    public Session player1;
    public Session player2;
    public String gameId;

    // Optional: helper method to check if both players are connected
    public boolean isReady() {
        return player1 != null && player2 != null;
    }
}


