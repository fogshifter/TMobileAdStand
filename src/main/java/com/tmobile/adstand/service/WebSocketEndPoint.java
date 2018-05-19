package com.tmobile.adstand.service;


import javax.ejb.Singleton;
//import javax.ejb.Stateful;
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Named;
//import javax.inject.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Singleton
@ServerEndpoint("/websocket")
public class WebSocketEndPoint {

    private Set<Session> clients = new HashSet<>();

    public WebSocketEndPoint(){}

    @OnOpen
    public void onConnect(Session session) {
        System.out.println("--------------------------onConnect----------------------------------------");
        clients.add(session);
    }

    @OnClose
    public void onDisconnect(Session session) {
        System.out.println("--------------------------onDisconnect----------------------------------------");
        clients.remove(session);
    }

    public void updateView() {
        System.out.println("updateView: " + clients.size());
        for(Session client : clients) {
            try {
                client.getBasicRemote().sendText("update");
                System.out.println("updateView sending update message");
            }
            catch (IOException e) {
                // TODO: log exception
                try {
                    client.close();
                }
                catch (IOException ex) {
                    // TODO: log exception
                }
                finally {
                    clients.remove(client);
                }
            }
        }
    }
}
