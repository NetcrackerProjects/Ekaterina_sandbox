package com.gigssandbox;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Client {
    private static final String EMPTY_STRING = "";
    private static final String COMMA = ",";

    private Connection connectionWithServer;
    private UserInteraction userInteraction;
    private boolean isChatMember;
    private String username;

    private Client() {
        this.userInteraction = new UserInteraction();
        this.isChatMember = false;
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.work();
    }

    private void work() {
        try {
            connectToServer();
            addNewClient();
            processServerMessages();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void connectToServer() throws IOException {
        String host = getServerHost();
        int port = getServerPort();
        connectionWithServer = new Connection(new Socket(host, port));
    }

    private void addNewClient() throws IOException, ClassNotFoundException {
        while (true) {
            Message message = connectionWithServer.receiveMessage();

            switch (message.getType()) {
                case NAME_REQUEST:
                    userInteraction.writePreloaded("log_in_or_sign_up");
                    username = userInteraction.readString();
                    sendMessage(new Message(MessageType.USERNAME, username));
                    break;
                case NAME_ACCEPTED:
                    userInteraction.writePreloaded("name_accepted");
                    return;
                case NAME_EXISTS:
                    userInteraction.writePreloaded("name_exists");
                    username = EMPTY_STRING;
                    break;
                case NAME_IS_NULL:
                    userInteraction.writePreloaded("name_is_null");
                    break;
                default:
                    userInteraction.writePreloaded("unsupported");
                    break;
            }
        }
    }

    private void processServerMessages() throws IOException, ClassNotFoundException {
        while (connectionWithServer.isBound()) {
            Message message = connectionWithServer.receiveMessage();

            switch (message.getType()) {
                case TEXT:
                    userInteraction.write(message.getContent());
                    break;
                case CHOOSE_ACTION:
                    userInteraction.write(message.getContent());
                    chooseAction();
                    break;
                case CHATS:
                    userInteraction.write(message.getContent());
                    addUserToSpecificChatAndNotifyOthers();
                    processChatMessages();
                    break;
                default:
                    userInteraction.writePreloaded("unsupported");
                    break;
            }
        }
    }

    private void addUserToSpecificChatAndNotifyOthers() throws IOException, ClassNotFoundException {
        String chatroom;
        Collection<String> availableChatrooms = new ArrayList<>(Arrays.asList(userInteraction.loadString("chatrooms").split(COMMA)));

        while (!availableChatrooms.contains(chatroom = userInteraction.readString())) {
            userInteraction.writePreloaded("chat_does_not_exist");
        }

        sendMessage(Message.builder().type(MessageType.CHOSEN_CHAT).content(chatroom).build());

        isChatMember = true;
        userInteraction.write(connectionWithServer.receiveMessage().getContent());
    }

    private void processChatMessages() {
        new Thread(() -> {
            while (isChatMember) {
                Message message = receiveMessage();
                if (message.getType() == MessageType.TEXT) {
                    userInteraction.write(message.getContent());
                }
            }
        }).start();

        while (isChatMember) {
            String text = userInteraction.readString();

            if (text.equalsIgnoreCase("exit")) {
                isChatMember = false;
                sendMessage(new Message(MessageType.EXIT_CHAT, userInteraction.unite(username, userInteraction.loadString("member_exited_chat"))));

            } else if (!text.equals(EMPTY_STRING)) {
                sendMessage(new Message(MessageType.TEXT, text));
            }
        }
    }

    private void chooseAction() {
        switch (getNextCommand(userInteraction)) {
            case PRINT_GIGS:
                sendMessage(Message.builder().type(MessageType.PRINT_GIGS).build());
                break;
            case JOIN_CHAT:
                sendMessage(Message.builder().type(MessageType.JOIN_CHAT).build());
                break;
            default:
                userInteraction.writePreloaded("unsupported");
                break;
        }
    }

    private void sendMessage(Message message) {
        try {
            connectionWithServer.sendMessage(message);
        } catch (IOException e) {
            userInteraction.writePreloaded("sending_message_error");
        }
    }

    private Message receiveMessage() {
        Message message = null;
        try {
            message = connectionWithServer.receiveMessage();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    private Command getNextCommand(UserInteraction userInteraction) {
        int actionCode = userInteraction.readInt();
        Command currentCommand;

        try {
            currentCommand = Command.values()[actionCode - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            currentCommand = Command.UNSUPPORTED;
        }
        return currentCommand;
    }

    private String getServerHost() {
        userInteraction.writePreloaded("enter_server_host");
        return userInteraction.readString();
    }

    private int getServerPort() {
        userInteraction.writePreloaded("enter_server_port");
        return userInteraction.readInt();
    }

}