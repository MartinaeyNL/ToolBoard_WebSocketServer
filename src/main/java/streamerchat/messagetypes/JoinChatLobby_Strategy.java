package streamerchat.messagetypes;

import streamerchat.models.ChatLobby;
import streamerchat.models.Controller;
import streamerchat.models.User;

public class JoinChatLobby_Strategy implements WSMessageTypeStrategy {

    @Override
    public Object start(Object parameter, User user, Controller controller) {

        // Parameter checking
        if(parameter instanceof String) {
            String lobbyName = (String) parameter;

            // All other checks
            ChatLobby lobby = controller.getChatLobby(lobbyName);
            if(lobby != null) {
                if(lobby.users.size() > 0)
                    for(User loopUser : lobby.users) {
                        if(!loopUser.getSessionId().equals(user.getSessionId())) {
                            controller.addUserToLobby(lobby, user);
                            System.out.println("Joining the chatlobby " + parameter);
                        }
                        else {
                            System.out.println("You've already joined this lobby!");
                            return new Exception("You've already joined this lobby!");
                        }
                    }
                else {
                    controller.addUserToLobby(lobby, user);
                    System.out.println("Joining the chatlobby " + parameter);
                }
            }
            else {
                System.out.println("The lobby you wanted to join doesn't exist!");
                return new IllegalArgumentException("The lobby you wanted to join doesn't exist!");
            }
        }

        //System.out.println("I've arrived at the right strategy stuff meuk ding hahahahahahaahahahahah pop");
        return null;
    }
}
