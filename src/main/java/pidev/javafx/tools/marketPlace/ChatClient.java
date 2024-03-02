package pidev.javafx.tools.marketPlace;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import pidev.javafx.controller.chat.ChatController;
import pidev.javafx.tools.UserController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
public class ChatClient {

    private static ChatClient instance;
    Socket socket;
    BufferedReader reader;
    PrintWriter  writer;
    byte[] imageBytes;
    int bytesRead;
    ByteArrayOutputStream bos;


    private ChatClient() {}

    public static ChatClient getInstance() {
        if (instance == null)
            instance = new ChatClient();
        return instance;
    }

    public void isUserConnected(int userID){
        writer.println("[o^^{[|{|>__"+userID);
    }


    public boolean establishConnection() {
            try {
                 socket = new Socket( "localhost", 8001 );
                 reader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
                 writer = new PrintWriter( socket.getOutputStream(), true );
                imageBytes = new byte[1024];
                bos = new ByteArrayOutputStream();

                 int userID = UserController.getInstance().getCurrentUser().getId();
                 String pwd = UserController.getInstance().getCurrentUser().getPassword();

                 if (!ChatServer.authenticateClient( userID, pwd )) {
                     System.out.println( "not connected" );
                     try {
                         socket.close();
                     } catch (IOException ex) {
                         throw new RuntimeException( ex );
                     }
                     return false;
                 }
                 writer.println(userID);

            } catch (IOException ex) {
                throw new RuntimeException( ex );
            }
            return true;
    }



    public void reciveMessagesFromOtherUser(VBox chatContainer,ResultHolder resultHolder){
        new Thread(() -> {
            try {
                while (socket.isConnected()) {
                    String recivedMessage = reader.readLine();
                    if(recivedMessage.contains( "[o^^{[|{|>__" )){
                        String[] parts = recivedMessage.split( "__", 2 );
                        System.out.println("chat server result "+parts[1]);
                        resultHolder.setResult(parts[1]);
                    }
                    else if(recivedMessage.equals( "uploadImage" )){
                        while ((bytesRead = socket.getInputStream().read(imageBytes)) != -1) {
                            bos.write(imageBytes, 0, bytesRead);
                        }
//                        !:;
                        chatContainer.getChildren().add( ChatController.createImageChatBoxFromBytes(bos.toByteArray(), true ) );
                    }
                    else {
                        Platform.runLater( () -> {
                            chatContainer.getChildren().add( ChatController.createTextChatBox( recivedMessage, true ) );
                        } );
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    public void sendMessages(String msg) {
            writer.println(msg);
    }
//
//    public void sendImageBytes(Byte[] bytes) {
//        writer.println(bytes);
//    }


    public void sendImage(int userId,File imageFile) {
        try {
            // Read the image file and convert it to a byte array
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", bos);
            byte[] imageBytes = bos.toByteArray();

            // Send the image byte array over the socket
            writer.println("@"+userId+"_uploadImage");
            socket.getOutputStream().write(imageBytes);
            socket.getOutputStream().flush();
            System.out.println("flushed successfuly");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(int userID) {
        writer.println("[|@><{__"+userID);
        System.out.println("disconnected");
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
