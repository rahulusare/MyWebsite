package com.rahul.GameWebsocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.json.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/iludo")
public class LudoEndpoint {

    private static Map<String, Set<Session>> games = new ConcurrentHashMap<>();
    private static Map<Session, String> players = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session){
        System.out.println("Connected: "+session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session){
        JsonObject json = Json.createReader(new StringReader(message)).readObject();
        String type = json.getString("type", "");
        if("join".equals(type)){
            String username = json.getString("username", "Unknown");
            String gameId = json.getString("gameId", "");
            players.put(session, username);
            games.putIfAbsent(gameId, ConcurrentHashMap.newKeySet());
            games.get(gameId).add(session);
            broadcastPlayers(gameId);
        }
    }

    @OnClose
    public void onClose(Session session){
        players.remove(session);
        games.values().forEach(s -> s.remove(session));
    }

    private void broadcastPlayers(String gameId){
        if(!games.containsKey(gameId)) return;
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for(Session s: games.get(gameId)){
            arr.add(Json.createObjectBuilder().add("username", players.get(s)));
        }
        JsonObject json = Json.createObjectBuilder()
                .add("type","players")
                .add("list", arr)
                .build();
        for(Session s: games.get(gameId)){
            try { s.getBasicRemote().sendText(json.toString()); }
            catch(IOException e){ e.printStackTrace();}
        }
    }
}
