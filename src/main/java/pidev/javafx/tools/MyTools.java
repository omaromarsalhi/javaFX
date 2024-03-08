package pidev.javafx.tools;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pidev.javafx.model.Contrat.Contract;

import java.io.File;
import java.io.FileOutputStream;

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
}
