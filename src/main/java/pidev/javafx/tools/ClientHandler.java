package pidev.javafx.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

            ChatServer.getClients().put( Integer.parseInt( reader.readLine() ), this );


            while ((clientMessage = reader.readLine()) != null) {
                if (clientMessage.startsWith( "@" )) {
                    String[] parts = clientMessage.split( "_", 2 );
                    String recipient = parts[0].substring( 1 );
                    String message = parts[1];
                    ChatServer.sendMessageToUser( Integer.parseInt( recipient ), message );
                } else {
                    String[] parts = clientMessage.split( "__", 2 );
                    if (parts[0].equals( "[o^^{[|{|>" ))
                        writer.println( ChatServer.isUserConnected( Integer.parseInt( parts[1] ) ) );
                    else if (parts[0].equals( "[|@><{__" ))
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
