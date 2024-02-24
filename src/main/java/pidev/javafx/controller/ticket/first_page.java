package pidev.javafx.controller.ticket;
import com.aspose.imaging.*;
import com.aspose.imaging.brushes.SolidBrush;
import com.aspose.imaging.imageoptions.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javafx.fxml.FXML;


public class first_page {

    private int counter = 0;
    @FXML
    private ImageView imageView;

    public void initialize() {
        // Initialize your interface here

        // Initialize the ImageView
        imageView = new ImageView();

        // Call the updateImage function
        updateImage();
    }
    public void updateImage() {
        counter++;

        // Create an instance of BmpCreateOptions and set its properties
        BmpOptions createOptions = new BmpOptions();
        createOptions.setBitsPerPixel(24);

        // Create an instance of Image
        try (Image image = Image.create(createOptions, 100, 100)) {
            // Create and initialize an instance of Graphics class
            Graphics graphic = new Graphics(image);
            graphic.drawString(Integer.toString(counter),
                    new Font("Arial", 14, FontStyle.Regular),
                    new SolidBrush(Color.getBlack()),
                    new Point(10, 50));

            // Save all changes to a ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            image.save(outputStream, createOptions);

            // Convert the ByteArrayOutputStream to a ByteArrayInputStream
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            // Create a new JavaFX Image and update the ImageView
            javafx.scene.image.Image fxImage = new javafx.scene.image.Image(inputStream);
            imageView.setImage(fxImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
