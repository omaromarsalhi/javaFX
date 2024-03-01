package pidev.javafx.controller.ticket;
import com.aspose.imaging.internal.Exceptions.IO.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.fxml.FXML;
import pidev.javafx.crud.ticket.ServiceTicket;
import pidev.javafx.model.ticket.Ticket;

import javax.imageio.ImageIO;


public class First_page {

    public static int counter;

    private int counter1 = 0;
    private int counter2 = 0;

    @FXML
    private ImageView imageView;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ListView lista;
    ServiceTicket si = new ServiceTicket();
    public void updateImage() {
        First_page.counter++;

        // Create a BufferedImage with RGB color components
        BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);

        // Create a Graphics2D object from the BufferedImage
        Graphics2D graphic = image.createGraphics();

        // Set the color and font for the text
        java.awt.Color customColor = new java.awt.Color(239, 16, 16); // RGB values range from 0 to 255
        java.awt.Font font = new java.awt.Font("Arial", java.awt.Font.BOLD, 14);
        graphic.setFont(font);

        // Draw the text on the image
        String text = "Ticket No: " + String.valueOf(First_page.counter);
        graphic.setColor(customColor);

        // Get the FontMetrics
        FontMetrics metrics = graphic.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = (image.getWidth() - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = ((image.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

        // Draw the string
        graphic.drawString(text, x, y);

        // Draw a rectangle around the Ticket
        graphic.setColor(java.awt.Color.BLACK);
        graphic.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);

        // Save the image to a file
        try {
            ImageIO.write(image, "png", new File("photo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

        // Convert the BufferedImage to a JavaFX Image and set it on the ImageView
        javafx.scene.image.Image fxImage = SwingFXUtils.toFXImage(image, null);
        imageView.setImage(fxImage);
        showPopup();

    }
    public void updateImage1() {
        counter1++;

        // Create a BufferedImage with RGB color components
        BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);

        // Create a Graphics2D object from the BufferedImage
        Graphics2D graphic = image.createGraphics();

        // Set the color and font for the text
        java.awt.Color customColor = new java.awt.Color(239, 16, 16); // RGB values range from 0 to 255
        java.awt.Font font = new java.awt.Font("Arial", java.awt.Font.BOLD, 14);
        graphic.setFont(font);

        // Draw the text on the image
        String text = "Ticket No: " + String.valueOf(counter1);
        graphic.setColor(customColor);

        // Get the FontMetrics
        FontMetrics metrics = graphic.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = (image.getWidth() - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = ((image.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

        // Draw the string
        graphic.drawString(text, x, y);

        // Draw a rectangle around the Ticket
        graphic.setColor(java.awt.Color.BLACK);
        graphic.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);

        // Save the image to a file
        try {
            ImageIO.write(image, "png", new File("photo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

        // Convert the BufferedImage to a JavaFX Image and set it on the ImageView
        javafx.scene.image.Image fxImage = SwingFXUtils.toFXImage(image, null);
        imageView1.setImage(fxImage);
    }
    public void updateImage2() {
        counter2++;

        // Create a BufferedImage with RGB color components
        BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);

        // Create a Graphics2D object from the BufferedImage
        Graphics2D graphic = image.createGraphics();

        // Set the color and font for the text
        java.awt.Color customColor = new java.awt.Color(239, 16, 16); // RGB values range from 0 to 255
        java.awt.Font font = new java.awt.Font("Arial", java.awt.Font.BOLD, 14);
        graphic.setFont(font);

        // Draw the text on the image
        String text = "Ticket No: " + String.valueOf(counter2);
        graphic.setColor(customColor);

        // Get the FontMetrics
        FontMetrics metrics = graphic.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = (image.getWidth() - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = ((image.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

        // Draw the string
        graphic.drawString(text, x, y);

        // Draw a rectangle around the Ticket
        graphic.setColor(java.awt.Color.BLACK);
        graphic.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);

        // Save the image to a file
        try {
            ImageIO.write(image, "png", new File("photo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

        // Convert the BufferedImage to a JavaFX Image and set it on the ImageView
        javafx.scene.image.Image fxImage = SwingFXUtils.toFXImage(image, null);
        imageView2.setImage(fxImage);
    }

    public void showPopup() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ticket/secondForm.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Popup Window");
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void initialize() {

        List<Ticket> Tickets = new ArrayList<>(si.getAll());

        lista.getItems().addAll(Tickets);
        lista.setCellFactory(reclamationListView -> new ListCell<Ticket>() {
            @Override
            protected void updateItem(Ticket ticket, boolean empty) {
                super.updateItem(ticket, empty);
                if (empty || ticket == null) {
                    setText(null);
                } else {
                    setText(" tickt number: "+ ticket.getTicketNumber() + " | date :  " + ticket.getDate() +" |  nam : "+ticket.getCustomName() + " | type :  " + ticket.getType());
                    setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
                }
            }
        });
    }
}
