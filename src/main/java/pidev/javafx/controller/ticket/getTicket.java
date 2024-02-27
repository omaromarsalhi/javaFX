package pidev.javafx.controller.ticket;
import com.aspose.imaging.internal.Exceptions.IO.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class getTicket {



//    public void showTicket() {
//        int counter = first_page.counter;
//        // Create a BufferedImage with RGB color components
//        BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
//
//        // Create a Graphics2D object from the BufferedImage
//        Graphics2D graphic = image.createGraphics();
//
//        // Set the color and font for the text
//        java.awt.Color customColor = new java.awt.Color(239, 16, 16); // RGB values range from 0 to 255
//        java.awt.Font font = new java.awt.Font("Arial", java.awt.Font.BOLD, 14);
//        graphic.setFont(font);
//
//        // Draw the text on the image
//        String text = "Ticket No: " + String.valueOf(counter);
//        graphic.setColor(customColor);
//
//        // Get the FontMetrics
//        FontMetrics metrics = graphic.getFontMetrics(font);
//        // Determine the X coordinate for the text
//        int x = (image.getWidth() - metrics.stringWidth(text)) / 2;
//        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
//        int y = ((image.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
//
//        // Draw the string
//        graphic.drawString(text, x, y);
//
//        // Draw a rectangle around the ticket
//        graphic.setColor(java.awt.Color.BLACK);
//        graphic.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);
//
//        // Save the image to a file
//        try {
//            ImageIO.write(image, "png", new File("photo.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (java.io.IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Convert the BufferedImage to a JavaFX Image and set it on the ImageView
//        javafx.scene.image.Image fxImage = SwingFXUtils.toFXImage(image, null);
//        imageView.setImage(fxImage);


 //   }
}
