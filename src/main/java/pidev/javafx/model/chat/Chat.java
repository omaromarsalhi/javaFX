package pidev.javafx.model.chat;

import pidev.javafx.model.user.User;

import java.sql.Timestamp;

public class Chat {
    private int idChat;
    private User userSender;
    private User userReciver;
    private String message;
    private boolean msgState;
    private Timestamp timestamp;


    public Chat() {
    }

    public Chat(int idChat, User userSender, User userReciver, String message, boolean msgState, Timestamp timestamp) {
        this.idChat = idChat;
        this.userSender = userSender;
        this.userReciver = userReciver;
        this.message = message;
        this.msgState = msgState;
        this.timestamp = timestamp;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public User getUserSender() {
        return userSender;
    }

    public void setUserSender(User userSender) {
        this.userSender = userSender;
    }

    public User getUserReciver() {
        return userReciver;
    }

    public void setUserReciver(User userReciver) {
        this.userReciver = userReciver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMsgState() {
        return msgState;
    }

    public void setMsgState(boolean msgState) {
        this.msgState = msgState;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
