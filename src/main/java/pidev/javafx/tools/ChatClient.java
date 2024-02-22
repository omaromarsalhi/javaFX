package pidev.javafx.tools;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import pidev.javafx.controller.chat.ChatController;
import pidev.javafx.controller.user.UserController;

import java.io.*;
import java.net.*;


public class ChatClient {

    private static ChatClient instance;
    Socket socket;
    BufferedReader reader;
    PrintWriter  writer;


    private ChatClient() {}

    public static ChatClient getInstance() {
        if (instance == null)
            instance = new ChatClient();
        return instance;
    }


    public boolean establishConnection() {
            try {
                 socket = new Socket( "localhost", 8001 );
                 reader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
                 writer = new PrintWriter( socket.getOutputStream(), true );

                 String username = UserController.getInstance().getCurrentUser().getFirstname();
                 String pwd = UserController.getInstance().getCurrentUser().getPassword();

                 if (!ChatServer.authenticateClient( username, pwd )) {
                     System.out.println( "not connected" );
                     try {
                         socket.close();
                     } catch (IOException ex) {
                         throw new RuntimeException( ex );
                     }
                     return false;
                 }
                 writer.println(username);
            } catch (IOException ex) {
                throw new RuntimeException( ex );
            }
            return true;
    }



    public void reciveMessagesFromOtherUser(VBox chatContainer){
        new Thread(() -> {
            try {
                while (socket.isConnected()) {
                    String recivedMessage = reader.readLine();
                    Platform.runLater( () -> {
                        chatContainer.getChildren().add(ChatController.createTextChatBox(recivedMessage,true));
                    } );
                }
            } catch (IOException e) {
                closeConnection();
            }
        }).start();
    }

    public void sendMessages(String msg) {
            writer.println(msg);
    }

    public void closeConnection() {
        try {
            if(socket!=null)
                socket.close();
            if(reader!=null)
                reader.close();
            if(writer!=null)
                writer.close();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
    }
}