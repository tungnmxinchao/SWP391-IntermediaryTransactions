/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author TNO
 */
@ServerEndpoint("/notification")
public class NotificationWebSocket {

    private static Set<Session> userSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());

    @OnOpen
    public void onOpen(Session curSession) {
        userSessions.add(curSession);
    }

    @OnClose
    public void onClose(Session curSession) {
        userSessions.remove(curSession);
    }

    @OnMessage
    public void onMessage(String message, Session userSession) throws IOException {
        for (Session ses : userSessions) {
            ses.getAsyncRemote().sendText(message);
        }
    }
}
