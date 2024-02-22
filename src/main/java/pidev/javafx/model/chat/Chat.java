package pidev.javafx.model.chat;

import java.sql.Timestamp;

public class Chat {
    private int idChat;
    private int idSender;
    private int idReciver;
    private String message;
    private boolean msgState;
    private Timestamp timestamp;


    public Chat() {
    }

    public Chat(int idChat, int idSender, int idReciver, String message, boolean msgState, Timestamp timestamp) {
        this.idChat = idChat;
        this.idSender = idSender;
        this.idReciver = idReciver;
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

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public int getIdReciver() {
        return idReciver;
    }

    public void setIdReciver(int idReciver) {
        this.idReciver = idReciver;
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
