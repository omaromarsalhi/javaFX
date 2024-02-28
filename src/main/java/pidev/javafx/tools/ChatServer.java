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
    private static ExecutorService threadPool = Executors.newFixedThreadPool(3);
    private static Map<Integer, ClientHandler> users = new HashMap<>();
    private static Map<Integer, String> userCredentials = new HashMap<>();

    static {
        userCredentials.put(1, "salhi");
        userCredentials.put(2, "benzaied");
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
        threadPool.shutdown();
    }


    public static void sendMessageToUser(Integer userId,String message) {
        ClientHandler client = users.get(userId);
        if (client != null) {
            System.out.println(userId);
            client.sendMessage(message);
        }
    }

    public static boolean authenticateClient(int userID,String pwd) {
        return userCredentials.containsKey(userID) && userCredentials.get(userID).equals(pwd);
    }

    public static Map<Integer, ClientHandler> getClients() {
        return users;
    }
    public static String isUserConnected(int userID) {
        return "[o^^{[|{|>__" + users.containsKey(userID);
    }

    public static void closeConnection(int userID) {
        users.remove(userID);
    }
}

