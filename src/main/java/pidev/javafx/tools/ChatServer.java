package pidev.javafx.tools;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private static Map<String, ClientHandler> clients = new HashMap<>();
    private static Map<String, String> userCredentials = new HashMap<>();

    static {
        userCredentials.put("omar", "salhi");
        userCredentials.put("latifa", "benzaied");
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8001);
            System.out.println("Server started. Listening for connections...");

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                threadPool.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void sendMessageToUser(String username,String message) {
        ClientHandler client = clients.get(username);
        if (client != null) {
            System.out.println(username);
            client.sendMessage(message);
        }
    }

    public static boolean authenticateClient(String username,String pwd) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(pwd);
    }

    public static int getClientsSize() {
        System.out.println(clients.size());
        return clients.size();
    }
    public static Map<String, ClientHandler> getClients() {
        return clients;
    }
}

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

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer = new PrintWriter(socket.getOutputStream(), true);

            ChatServer.getClients().put(reader.readLine(), this);


            while ((clientMessage = reader.readLine()) != null) {
                if (clientMessage.startsWith("@")) {
                    String[] parts = clientMessage.split( "_", 2 );
                    String recipient = parts[0].substring( 1 );
                    String message = parts[1];
                    ChatServer.sendMessageToUser(recipient,message);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }


}
