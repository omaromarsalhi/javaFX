package pidev.javafx.tools.marketPlace;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.UUID;

class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String clientMessage;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {

            reader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
            writer = new PrintWriter( socket.getOutputStream(), true );

            String userID=reader.readLine();
            ChatServer.getClients().put( Integer.parseInt( userID ), this );
            System.out.println(ChatServer.getClients().keySet());
            System.out.println(userID);

            while ((clientMessage = reader.readLine()) != null) {
                if (clientMessage.startsWith( "@" )) {
                    String[] parts = clientMessage.split( "_", 2 );
                    String recipient = parts[0].substring( 1 );
                    String message = parts[1];
                    ChatServer.sendMessageToUser( Integer.parseInt( recipient ), message );
                } else {
                    String[] parts = clientMessage.split( "__", 2 );
                    if (parts[0].equals( "[o^^{[|{|>" )) {
                        writer.println( ChatServer.isUserConnected( Integer.parseInt( parts[1] ) ) );
                    }
                    else if (parts[0].equals( "[|@><{" ))
                        ChatServer.closeConnection(Integer.parseInt(parts[1]));
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println( message );
    }
}
