package pidev.javafx.tools;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pidev.javafx.crud.marketplace.CrudFavorite;
import pidev.javafx.model.Contrat.Contract;
import pidev.javafx.model.MarketPlace.Bien;
import pidev.javafx.model.MarketPlace.Favorite;
import pidev.javafx.model.MarketPlace.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyTools {

    private static MyTools instance;

    private MyTools() {}

    public static MyTools getInstance() {
        if (instance == null)
            instance = new MyTools();
        return instance;
    }


    public void generatePDF(Contract contract) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(getFileOfSave()));
            document.open();
            document.add(new Paragraph("Title: Sale contrat" ));
            document.add(new Paragraph("Party A ID: " + 1));
            document.add(new Paragraph("Party B ID: " + 2));
            document.add(new Paragraph("Item Name: " ));
            document.add(new Paragraph("Effective Date: " + contract.getEffectiveDate()));
            document.add(new Paragraph("Termination Date: " + contract.getTerminationDate()));
            document.add(new Paragraph("Purpose: buyintg oéjrhgniuoh't" ));
            document.add(new Paragraph("Terms and Conditions: " + contract.getTermsAndConditions()));
            document.add(new Paragraph("Payment Method: " + contract.getPaymentMethod()));
            document.close();
            System.out.println("PDF generated successfully!");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getFileOfSave(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pdf Image");
        fileChooser.setInitialDirectory( new File( "src/main/resources/Cnotrat" ) );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        return fileChooser.showSaveDialog( Stage.getWindows().get(0) );
    }

    public void notifyUser4NewAddedProduct(Product product){
        ObservableList<Favorite> favoriteObservableList= CrudFavorite.getInstance().selectItems();
        boolean checkIfProductIsValid=true;
        if(product instanceof Bien prod){
            for(Favorite favorite:favoriteObservableList){
                String[] parts = favorite.getSpecifications().split("__");
                if(LocalDate.now().isAfter(LocalDate.parse(parts[0]))) {
                    if (parts[1].isEmpty() || LocalDate.now().isAfter( LocalDate.parse( parts[1] ) ))
                        checkIfProductIsValid = false;
                    else if (parts[2].equals( "-1" )||Integer.parseInt(parts[2])>prod.getPrice())
                        checkIfProductIsValid = false;
                    else if (parts[3].equals( "-1" )||Integer.parseInt(parts[3])<prod.getPrice())
                        checkIfProductIsValid = false;
                    else if (parts[4].equals( "-1" )||Integer.parseInt(parts[4])!=prod.getQuantity())
                        checkIfProductIsValid = false;
                    else if (!parts[5].equals(prod.getCategorie().toString())&&!parts[5].equals( "ALL"))
                        checkIfProductIsValid = false;
                }
                else
                        checkIfProductIsValid=false;
                if(checkIfProductIsValid) {
                    System.out.println("wa");
                    PhoneSMS.getInstance().sendSMS( "+21629624921","A New Product Was Added" );
                }
                checkIfProductIsValid=true;
            }
        }
    }
}
