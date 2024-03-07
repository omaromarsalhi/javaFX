package pidev.javafx.crud.marketplace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pidev.javafx.crud.ConnectionDB;
import pidev.javafx.crud.CrudInterface;
import pidev.javafx.model.MarketPlace.Categorie;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.tools.UserController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrudBien implements CrudInterface<Bien> {

    private Connection connect;
    private PreparedStatement prepare;
    private  ResultSet result;
    private Connection connect4Images;
    private PreparedStatement prepare4Images;
    private  ResultSet result4Images;
    private static CrudBien instance;

    private CrudBien() {}

    public static CrudBien getInstance() {
        if (instance == null)
            instance = new CrudBien();
        return instance;
    }



    public ObservableList<Bien> searchItems(String culumn,String value) {

        Bien bien = null;
        String sql = "SELECT * FROM products where "+culumn+" like ? and isDeleted=false ";

        connect = ConnectionDB.getInstance().getCnx();
        ObservableList<Bien> BienList = FXCollections.observableArrayList();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(  1, "%"+value+"%");
            result = prepare.executeQuery();
            while (result.next()) {
                bien=new Bien(result.getInt("idProd"),
                        result.getInt("idUser"),
                        result.getString("name"),
                        result.getString("descreption"),
                        "",
                        result.getFloat("price"),
                        result.getFloat("quantity"),
                        result.getString("state"),
                        result.getTimestamp("timestamp"),
                        Categorie.valueOf(result.getString("category")));
                bien.setAllImagesSources( selectImagesById(bien.getId()) );
                bien.setImgSource( bien.getImageSourceByIndex( 0 ) );
                bien.setImage( new ImageView( new Image( "file:src/main/resources" + bien.getImgSource(), 35, 35, false, false ) ) );
                BienList.add(bien);
            }
        } catch (SQLException e) {
            System.out.println("Error selecting items: " + e.getMessage());
        }

        return BienList;
    }


    public void addItem(Bien bien) {
        String sql = "INSERT INTO products "
                + "(idUser, name, descreption,isDeleted, price, quantity, state, type, category)"
                + " VALUES (?, ?, ?, ?, ?, ? ,? , ?, ?)";

        connect = ConnectionDB.getInstance().getCnx();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, 1);
            prepare.setString(2, bien.getName());
            prepare.setString(3, bien.getDescreption());
//            prepare.setString(4,(bien.getImgSource().equals( "DO_NOT_UPDATE_OR_ADD_IMAGE" ))?"":getPathAndSaveIMG(bien.getImgSource()));
            prepare.setBoolean(4,false);
            prepare.setFloat(5, bien.getPrice());
            prepare.setFloat(6, bien.getQuantity());
            prepare.setString(7, bien.getState());
            prepare.setString(8, "BIEN");
            prepare.setString(9, bien.getCategorie().toString());
            prepare.executeUpdate();
            addItemImages(bien.getAllImagesSources(),selectLastItem().getId());
        } catch (SQLException e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }

    public void addItemImages(List<String> imageList,int id) {
        String sql = "INSERT INTO product_images "
                + "(idProduct,path)"
                + " VALUES (?, ?)";

        connect4Images = ConnectionDB.getInstance().getCnx();

        try {
            for(String file :imageList) {
                prepare4Images = connect4Images.prepareStatement( sql );
                prepare4Images.setInt( 1,id );
                prepare4Images.setString( 2, file );
                prepare4Images.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }



    public void deleteItem(int id) {

        String sql = "UPDATE products SET isDeleted = true WHERE idProd = ?";
        connect = ConnectionDB.getInstance().getCnx();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt( 1,id );
            prepare.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting item: " + e.getMessage());
        }
    }

    public void deleteImages(int id) {

        String sql = "DELETE FROM product_images WHERE idProduct = ?";

        connect4Images = ConnectionDB.getInstance().getCnx();

        try {
            prepare4Images = connect4Images.prepareStatement(sql);
            prepare4Images.setInt(1, id);
            prepare4Images.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting item: " + e.getMessage());
        }
    }

    public void updateItem(Bien bien) {

        String sql = "UPDATE products SET name = ?," +
                " descreption = ?,"+
//                ((bien.getImgSource().equals( "DO_NOT_UPDATE_OR_ADD_IMAGE" ))?"":" imgSource = ?,")+
                "price = ?,"+
                " quantity = ?,"+
                " state = ?,"+
                " category = ?"+
                " WHERE idProd = ?";

        connect = ConnectionDB.getInstance().getCnx();

        try {
            int i=2;
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, bien.getName());
            prepare.setString(2, bien.getDescreption());
            prepare.setFloat(++i, bien.getPrice());
            prepare.setFloat(++i, bien.getQuantity());
            prepare.setString(++i, bien.getState());
            prepare.setString(++i, bien.getCategorie().toString());
            prepare.setInt(++i, bien.getId());
            prepare.executeUpdate();
            if(!bien.getImgSource().equals( "DO_NOT_UPDATE_OR_ADD_IMAGE" )) {
                deleteImages(bien.getId());
                addItemImages(bien.getAllImagesSources(),bien.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error updating item: " + e.getMessage());
        }
    }


    public ObservableList<Bien> selectItemsById() {
        Bien bien = null;
        String sql = "SELECT * FROM products  where isDeleted=false and idUser= ? order by idProd desc"; // Retrieve all items

        connect = ConnectionDB.getInstance().getCnx();
        ObservableList<Bien> BienList = FXCollections.observableArrayList();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt( 1, UserController.getInstance().getCurrentUser().getId() );
            result = prepare.executeQuery();
            while (result.next()) {
                bien=new Bien(result.getInt("idProd"),
                        result.getInt("idUser"),
                        result.getString("name"),
                        result.getString("descreption"),
                        "",
                        result.getFloat("price"),
                        result.getFloat("quantity"),
                        result.getString("state"),
                        result.getTimestamp("timestamp"),
                        Categorie.valueOf(result.getString("category")));
                bien.setAllImagesSources( selectImagesById(bien.getId()) );
                if(bien.getAllImagesSources().size()>0) {
                    bien.setImgSource( bien.getImageSourceByIndex( 0 ) );
                    bien.setImage( new ImageView( new Image( "file:src/main/resources" + bien.getImgSource(), 40, 40, false, false ) ) );
                }
                BienList.add(bien);
            }
        } catch (SQLException e) {
            System.out.println("Error selecting items: " + e.getMessage());
        }

        return BienList;
    }

    @Override
    public ObservableList<Bien> selectItems() {
        Bien bien = null;
        String sql = "SELECT * FROM products  where isDeleted=false order by idProd desc"; // Retrieve all items

        connect = ConnectionDB.getInstance().getCnx();
        ObservableList<Bien> BienList = FXCollections.observableArrayList();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                bien=new Bien(result.getInt("idProd"),
                        result.getInt("idUser"),
                        result.getString("name"),
                        result.getString("descreption"),
                        "",
                        result.getFloat("price"),
                        result.getFloat("quantity"),
                        result.getString("state"),
                        result.getTimestamp("timestamp"),
                        Categorie.valueOf(result.getString("category")));
                bien.setAllImagesSources( selectImagesById(bien.getId()) );
                if(bien.getAllImagesSources().size()>0) {
                    bien.setImgSource( bien.getImageSourceByIndex( 0 ) );
                    bien.setImage( new ImageView( new Image( "file:src/main/resources" + bien.getImgSource(), 40, 40, false, false ) ) );
                }
                BienList.add(bien);
            }
        } catch (SQLException e) {
            System.out.println("Error selecting items: " + e.getMessage());
        }

        return BienList;
    }


    public ObservableList<Bien> filterItems(String fromDate,String todate,int minPrice,int maxPrice,int quantity,String categoryChoice) {
        Bien bien = null;
        String sql = "SELECT * FROM products where isDeleted=false  " ;
        sql+=(categoryChoice.isEmpty()||categoryChoice.equals( "ALL" ))?"":"and category = ?";
        sql+=(fromDate.isEmpty())?"":" and timestamp >= ?";
        sql+=(todate.isEmpty())?"":" and timestamp <= ?";
        sql+=(minPrice==-1)?"":" and price >= ?";
        sql+=(maxPrice==-1)?"":" and price <= ?";
        sql+=(quantity==-1)?"":" and quantity = ?";

        connect = ConnectionDB.getInstance().getCnx();
        ObservableList<Bien> BienList = FXCollections.observableArrayList();
        try {
            int i=1;
            prepare = connect.prepareStatement(sql);
            if(!categoryChoice.isEmpty()&&!categoryChoice.equals( "ALL" ))
                prepare.setString(  i++, categoryChoice);
            if(!fromDate.isEmpty())
                prepare.setString( i++, fromDate);
            if(!todate.isEmpty())
                prepare.setString( i++, todate);
            if(minPrice!=-1)
                prepare.setInt(  i++, minPrice);
            if(maxPrice!=-1)
                prepare.setInt(  i++, maxPrice);
            if(quantity!=-1)
                prepare.setInt(  i++, quantity);


            System.out.println(prepare.toString());
            result = prepare.executeQuery();
            while (result.next()) {
                bien=new Bien(result.getInt("idProd"),
                        result.getInt("idUser"),
                        result.getString("name"),
                        result.getString("descreption"),
                        "",
                        result.getFloat("price"),
                        result.getFloat("quantity"),
                        result.getString("state"),
                        result.getTimestamp("timestamp"),
                        Categorie.valueOf(result.getString("category")));
                bien.setAllImagesSources( selectImagesById(bien.getId()) );
                bien.setImgSource( bien.getImageSourceByIndex( 0 ) );
                bien.setImage( new ImageView( new Image( "file:src/main/resources" + bien.getImgSource(), 35, 35, false, false ) ) );
                BienList.add(bien);
            }
        } catch (SQLException e) {
            System.out.println("Error selecting items: " + e.getMessage());
        }

        return BienList;
    }


    public String selectMaxValues4Filter() {
        Bien bien = null;
        String sql = "SELECT  max(price) as mPrice , max(quantity) as mQuantity FROM products  where isDeleted=0; "; // Retrieve all items
        String res="";
        connect = ConnectionDB.getInstance().getCnx();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                res+=result.getString( "mPrice" );
                res+="_";
                res+=result.getString( "mQuantity" );
            }
        } catch (SQLException e) {
            System.out.println("Error selecting items: " + e.getMessage());
        }
        return res;
    }



    public List<String> selectImagesById(int id) {
        String sql = "SELECT * FROM product_images where idProduct= ?";
        List<String> list=new ArrayList<>();
        connect4Images = ConnectionDB.getInstance().getCnx();
        try {

                prepare4Images = connect4Images.prepareStatement( sql );
                prepare4Images.setInt( 1,id );
                result4Images=prepare4Images.executeQuery();
                while(result4Images.next()){
                    list.add( result4Images.getString("path" ) );
                }
        } catch (SQLException e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
        return list;
    }

    @Override
    public Bien findById(int id) {
        return null;
    }

    @Override
    public Bien selectFirstItem() {
        return  null;
    }


    public Bien selectLastItem() {
        String Sql = "SELECT * FROM products ORDER BY idProd DESC LIMIT 1";
        connect = ConnectionDB.getInstance().getCnx();
        try {
            prepare = connect.prepareStatement(Sql);
            result = prepare.executeQuery();

            if (result.next()) {
                Bien bien= new Bien(result.getInt("idProd"),
                        result.getInt("idUser"),
                        result.getString("name"),
                        result.getString("descreption"),
                        "",
                        result.getFloat("price"),
                        result.getFloat("quantity"),
                        result.getString("state"),
                        result.getTimestamp("timestamp"),
                        Categorie.valueOf(result.getString("category")));
                bien.setAllImagesSources( selectImagesById(bien.getId()) );
                if(bien.getAllImagesSources().size()!=0) {
                    bien.setImgSource( bien.getImageSourceByIndex( 0 ) );
                    bien.setImage( new ImageView( new Image( "file:src/main/resources" + bien.getImgSource(), 35, 35, false, false ) ) );
                }
                return bien;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Bien selectItemById(int id) {
        String Sql = "SELECT * FROM products where idProd= ?";
        connect = ConnectionDB.getInstance().getCnx();
        try {
            prepare = connect.prepareStatement(Sql);
            prepare.setInt( 1,id );
            result = prepare.executeQuery();

            if (result.next()) {
                Bien bien= new Bien(result.getInt("idProd"),
                        result.getInt("idUser"),
                        result.getString("name"),
                        result.getString("descreption"),
                        "",
                        result.getFloat("price"),
                        result.getFloat("quantity"),
                        result.getString("state"),
                        result.getTimestamp("timestamp"),
                        Categorie.valueOf(result.getString("category")));
                bien.setAllImagesSources( selectImagesById(bien.getId()) );
                bien.setImgSource( bien.getImageSourceByIndex( 0 ) );
                bien.setImage( new ImageView( new Image( "file:src/main/resources" + bien.getImgSource(), 35, 35, false, false ) ) );
                return bien;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}




























//package pidev.javafx.Crud;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import pidev.javafx.Model.MarketPlace.Bien;
//import pidev.javafx.Model.MarketPlace.Categorie;
//import pidev.javafx.Model.MarketPlace.Bien;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.sql.*;
//import java.util.Random;
//
//public class CrudBien implements CrudInterface<Bien> {
//
//
//    private  Connection connect;
//    private  Statement statement;
//    private  PreparedStatement prepare;
//    private  ResultSet result;
//
//
//
//    public void addItem(Bien bien) {
//
//        String sql = "INSERT INTO Bien "
//                + "(idUser,name,descreption,imgSource,price,quantity,state,type,category)"
//                + "VALUES(?,?,?,?,?,?,?,?)";
//
//        connect = ConnectionDB.connectDb();
//
//        try {
//            prepare = connect.prepareStatement(sql);
//            prepare.setInt(1, 1);
//            prepare.setString(2, bien.getName());
//            prepare.setString(3, bien.getDescreption());
//            prepare.setString(4, getPathAndSaveIMG(bien.getImgSource()));
//            prepare.setFloat(5, bien.getPrice());
//            prepare.setFloat(6, bien.getQuantity());
//            prepare.setString(7, (bien.getState())?"1":"0");
//            prepare.setString(8, "BIEN");
//            prepare.setString(8, bien.getCategorie().toString());
//            prepare.executeUpdate();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public  void deletItem(Bien bien) {
//
//    }
//
//
//    public  void updateItem(Bien bien) {
//
//    }
//
//    @Override
//    public void deleteItem(Bien bien) {
//
//    }
//
//    @Override
//    public void selectItems(Bien bien) {
//
//    }
//
//
//    public  Bien selectFirstItems() {
//        return null;
//    }
//
//
//    private  String getPathAndSaveIMG(String chosenFilePath){
//        Random randomVal=new Random();
//
//        File chosenFile= new File( chosenFilePath );
//
//        int nameLenght=chosenFile.getName().length();
//
//        String fileName=Long.toString(chosenFile.getPath().length()* randomVal.nextInt(chosenFile.getPath().length())*nameLenght)+chosenFile.getName().substring(0,nameLenght-4);
//
//        String path ="/usersImg/"+fileName+".png";
//
//        try {
//            BufferedImage bi = ImageIO.read(chosenFile);
//            ImageIO.write(bi, "png", new File( "src/main/resources"+path ));
//        } catch (IOException e) {
//            throw new RuntimeException( e );
//        }
//        return path;
//    }
//}
