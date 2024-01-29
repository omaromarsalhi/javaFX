package pidev.javafx.Model.MarketPlace;

import java.sql.Timestamp;

public class Product {
    private int id;
    private int idUser;
    private String name;
    private String imgSource;
    private Float price;
    private Float quantity;
    private Boolean state;
    private Timestamp timestamp;

    public Product(int id, int idUser, String name, String imgSource, Float price, Float quantity, Boolean state, Timestamp timestamp) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.imgSource = imgSource;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }




}
