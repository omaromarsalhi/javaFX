package pidev.javafx.crud.marketplace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.crud.CrudInterface;
import pidev.javafx.model.chat.Chat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CrudChat implements CrudInterface<Chat> {

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private static CrudChat instance;

    private CrudChat() {}

    public static CrudChat getInstance() {
        if (instance == null)
            instance = new CrudChat();
        return instance;
    }

    public void addItem(Chat chat) {
        String sql = "INSERT INTO chat "
                + "(idSender,idReciver, message,msgState)"
                + " VALUES (?, ?, ?, ?)";

        connect = ConnectionDB.getInstance().getCnx();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, chat.getIdSender());
            prepare.setInt(2,chat.getIdReciver() );
            prepare.setString(3, chat.getMessage() );
            prepare.setBoolean(4,false);
            prepare.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }

    @Override
    public void updateItem(Chat variable) {

    }


    public void deleteItem(int id) {

        String sql = "DELETE FROM chat WHERE idChat = ?";

        connect = ConnectionDB.getInstance().getCnx();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, id);
            prepare.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting item: " + e.getMessage());
        }
    }


    @Override
    public ObservableList<Chat> selectItems() {
        Chat chat = null;
        String sql = "SELECT * FROM products  where isDeleted=false order by idProd desc";

        connect = ConnectionDB.getInstance().getCnx();
        ObservableList<Chat> chatList = FXCollections.observableArrayList();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                chat=new Chat(result.getInt("idChat"),
                        result.getInt("idSender"),
                        result.getInt("idReciver"),
                        result.getString("messge"),
                        result.getBoolean("msgState"),
                        result.getTimestamp( "timestamp"));
                chatList.add(chat);
            }
        } catch (SQLException e) {
            System.out.println("Error selecting items: " + e.getMessage());
        }
        return chatList;
    }



    @Override
    public Chat findById(int id) {
        return null;
    }

    @Override
    public Chat selectFirstItem() {
        return  null;
    }


    private  String getPathAndSaveIMG(String chosenFilePath){

        String path ="/usersImg/"+ UUID.randomUUID()+".png";

        Path src = Paths.get(chosenFilePath);
        Path dest = Paths.get( "src/main/resources"+path);

        try {
            Files.copy(src,dest);
        } catch (IOException e) {
            throw new RuntimeException( e );
        }

        return path;
    }
}
