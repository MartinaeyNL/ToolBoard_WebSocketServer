package streamerchat.websockets;

import streamerchat.messages.WSMessage;
import streamerchat.messages.WSMessageType;
import streamerchat.strategies.WSMessageTypeStrategy;
import streamerchat.models.Controller;
import streamerchat.models.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public abstract class WSContext {

    // Variables
    public Controller controller;
    public Map<WSMessageType, WSMessageTypeStrategy> strategies;

    // Main method for starting procedure
    public Collection<WSMessage> start(WSMessageType type, Object parameter, String sessionId) {
        WSMessageTypeStrategy strategy = this.strategies.get(type);
        Session session = this.controller.getConnectedUser(sessionId);
        Collection<WSMessage> toReturn = new ArrayList<>();

        // Start
        try { toReturn = strategy.start(parameter, session, controller); }
        catch (Exception e) {
            toReturn.add(new WSMessage(session.getSessionId(), WSMessageType.ERROR, e.getMessage()));
            System.out.println("There was a freaking error! Wow! This is him: [" + e.getMessage() + "]");
        }
        return toReturn;
    }

    /*----------------------------------------------------------------------*/

    // User Connections
    public void connectUser(String sessionId) {
        this.controller.addConnectedUser(sessionId);
    }

    public void disconnectUser(String sessionId) {
        this.controller.disconnectUser(sessionId);
    }
}
