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

            ChatServer.getClients().put( Integer.parseInt( reader.readLine() ), this );


            while ((clientMessage = reader.readLine()) != null) {
                if (clientMessage.startsWith( "@" )) {
                    String[] parts = clientMessage.split( "_", 2 );
                    String recipient = parts[0].substring( 1 );
                    String message = parts[1];
                    if(message.equals("uploadImage")) {
                        byte[] imageBytes = readImageBytesFromClient();
//                        processReceivedImage(imageBytes);
                        ChatServer.sendImage(Integer.parseInt(recipient),imageBytes);
                    }
                    else
                        ChatServer.sendMessageToUser( Integer.parseInt( recipient ), message );
//                    processReceivedImage(imageBytes);
                } else {
                    String[] parts = clientMessage.split( "__", 2 );
                    if (parts[0].equals( "[o^^{[|{|>" )) {
                        writer.println( ChatServer.isUserConnected( Integer.parseInt( parts[1] ) ) );
                        System.out.println("chat server");
                        System.out.println(parts[1]);
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


    public void sendImageBytes(byte[] bytes) {
        writer.println("uploadImage");
        try {
            socket.getOutputStream().write(bytes);
            socket.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
    }


    private byte[] readImageBytesFromClient() throws IOException {
        // Read the image data from the client
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = socket.getInputStream().read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        return bos.toByteArray();
    }
    private void processReceivedImage(byte[] imageBytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage receivedImage = null;
        try {
            receivedImage = ImageIO.read(bis);
//            File outputFile = new File("userImg/"+ UUID.randomUUID() +".jpg");
            File outputFile = new File("src/main/resources/usersImg/"+ UUID.randomUUID() +".png");
            ImageIO.write(receivedImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
    }


}
