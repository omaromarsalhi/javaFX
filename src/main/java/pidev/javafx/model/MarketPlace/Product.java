package pidev.javafx.model.MarketPlace;

import javafx.scene.image.ImageView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private int idUser;
    private String name;
    private String descreption;
    private String imgSource;
    private Float price;
    private Float quantity;
    private String state;
    private Timestamp timestamp;
    private ImageView image;
    private String type;
    private List<String> allImagesSources;


    public Product() {}

    public Product(int id, int idUser, String name, String descreption, String imgSource, Float price, Float quantity, String state, Timestamp timestamp, String type) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.descreption = descreption;
        this.imgSource = imgSource;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
        this.timestamp = timestamp;
        this.type = type;
        this.allImagesSources = new ArrayList<>();
    }


    public List<String> getAllImagesSources() {
        return allImagesSources;
    }
    public String getImageSourceByIndex(int index) {
        return allImagesSources.get(index);
    }

    public void setAllImagesSources(List<String> allImagesSources) {
        this.allImagesSources = allImagesSources;
    }
    public void deleteFromImagesSources(int index) {
        this.allImagesSources.remove( index );
    }
    public void addFromImagesSources(String path) {
        this.allImagesSources.add( path );
    }

    public int getId() {
        return id;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", name='" + name + '\'' +
                ", descreption='" + descreption + '\'' +
                ", imgSource='" + imgSource + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", state='" + state + '\'' +
                ", timestamp=" + timestamp +
                ", image=" + image +
                ", type='" + type + '\'' +
                ", allImagesSources=" + allImagesSources +
                '}';
    }
}
