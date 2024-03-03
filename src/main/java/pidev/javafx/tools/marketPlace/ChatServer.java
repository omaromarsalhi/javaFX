package pidev.javafx.tools.marketPlace;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.util.Duration;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(3);
    private static Map<Integer, ClientHandler> users = new HashMap<>();


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


    public static Map<Integer, ClientHandler> getClients() {
        return users;
    }

    public static String isUserConnected(int userID) {
        System.out.println("connection test "+users.containsKey(userID));
        return "[o^^{[|{|>__" + users.containsKey(userID);
    }

    public static void closeConnection(int userID) {
        users.remove(userID);
        System.out.println("size after close: "+users.size());
    }
}

