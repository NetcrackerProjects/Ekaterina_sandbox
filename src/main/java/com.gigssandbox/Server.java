package com.gigssandbox;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static final int PORT = 1234;

    private static final String EMPTY_STRING = "";
    private static final String NAME_SEPARATOR = ":";
    private static final String SPACE_REGEX = "\\s+";

    private static UserInteraction userInteraction = new UserInteraction();
    private static Map<String, String> usersChatrooms = new ConcurrentHashMap<>();
    private static Map<String, Connection> usersConnections = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        userInteraction.writePreloaded("server_running");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!serverSocket.isClosed()) {
                new HandlerThread(serverSocket.accept()).start();
            }
        }
    }

    private static class HandlerThread extends Thread {
        private Socket socket;
        private String username;
        private boolean isChatMember;
        private String currentChatroom;

        HandlerThread(Socket socket) {
            this.socket = socket;
            this.isChatMember = false;
            setDaemon(true);
        }

        @Override
        public void run() {
            try (Connection connectionWithClient = new Connection(socket)) {

                addUser(connectionWithClient);
                processClientMessages(connectionWithClient);
                removeUser();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void addUser(Connection connection) throws IOException, ClassNotFoundException {
            username = verifyAndGetUsername(connection);
            userInteraction.unite(userInteraction.loadString("new_user_connected_to_server"), username);
        }

        private void processClientMessages(Connection connection) throws IOException, ClassNotFoundException {
            while (connection.isBound()) {
                connection.sendMessage(new Message(MessageType.CHOOSE_ACTION, userInteraction.loadString("choose_server_action")));

                Message message = connection.receiveMessage();
                switch (message.getType()) {
                    case PRINT_GIGS:
                        connection.sendMessage(new Message(MessageType.TEXT, userInteraction.loadString("fake_gigs_list")));
                        break;
                    case JOIN_CHAT:
                        addUserToChat(connection);
                        processChatMessages(connection);
                        break;
                    case PUBLIC_NOTIFICATION:
                        sendPublicMessageToCurrentChat(new Message(MessageType.TEXT, message.getContent()));
                        break;
                    default:
                        userInteraction.writePreloaded("unsupported");
                        break;
                }
            }
        }

        private void removeUser() {
            usersConnections.remove(username);
            userInteraction.write(userInteraction.unite("client_connection_closed", username));
        }

        private String verifyAndGetUsername(Connection connection) throws IOException, ClassNotFoundException {
            String username = null;

            while (username == null || username.equals(EMPTY_STRING)) {
                connection.sendMessage(Message.builder().type(MessageType.NAME_REQUEST).build());

                username = connection.receiveMessage().getContent();
                if (username != null && !username.equals(EMPTY_STRING) && !usersConnections.containsKey(username)) {
                    connection.sendMessage(Message.builder().type(MessageType.NAME_ACCEPTED).build());
                    usersConnections.put(username, connection);

                } else {
                    if (usersConnections.containsKey(username)) {
                        connection.sendMessage(Message.builder().type(MessageType.NAME_EXISTS).build());
                        username = EMPTY_STRING;

                    } else {
                        connection.sendMessage(Message.builder().type(MessageType.NAME_IS_NULL).build());
                    }
                }
            }
            return username;
        }

        void addUserToChat(Connection connection) throws IOException, ClassNotFoundException {
            connection.sendMessage(new Message(MessageType.CHATS, userInteraction.loadString("choose_chatroom")));

            currentChatroom = connection.receiveMessage().getContent();
            usersChatrooms.put(username, currentChatroom);
            isChatMember = true;
            sendPublicMessageToCurrentChat(new Message(MessageType.TEXT, userInteraction.unite(username, userInteraction.loadString("member_joined_chat"))));
        }

        void processChatMessages(Connection connection) throws IOException, ClassNotFoundException {
            while (isChatMember) {
                Message message = connection.receiveMessage();

                if (message.getType() == MessageType.EXIT_CHAT) {
                    sendPublicMessageToCurrentChat(new Message(MessageType.TEXT,
                            userInteraction.unite(username, userInteraction.loadString("member_exited_chat"), currentChatroom)));
                    usersChatrooms.remove(username);
                    isChatMember = false;
                    currentChatroom = EMPTY_STRING;

                } else if (!message.getContent().equals(SPACE_REGEX)) {
                    sendPublicMessageToCurrentChat(new Message(MessageType.TEXT, userInteraction.unite(username, NAME_SEPARATOR, message.getContent())));
                }
            }
        }

        private void sendPublicMessageToCurrentChat(Message message) {
            for (Map.Entry<String, Connection> entry : usersConnections.entrySet()) {
                try {
                    if (currentChatroom.equals(usersChatrooms.get(entry.getKey()))) {
                        entry.getValue().sendMessage(message);
                    }
                } catch (IOException e) {
                    userInteraction.writePreloaded("sending_message_error");
                    e.printStackTrace();
                }
            }
        }
    }
}
