package pidev.javafx.crud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pidev.javafx.model.Contrat.Contract;
import pidev.javafx.model.Contrat.PaymentMethod;
import pidev.javafx.model.MarketPlace.*;
import pidev.javafx.model.Wrapper.LocalWrapper;

import java.sql.*;

public class CrudLocalWrapper{

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private static CrudLocalWrapper instance;

    public static CrudLocalWrapper getInstance() {
        if (instance == null) {
            instance = new CrudLocalWrapper();
        }
        return instance;
    }

    public ObservableList<LocalWrapper> selectItemsByIdSeller(int idUser,String trasactionMode) {
        String id=(trasactionMode.equals("PURCHSED"))?"t.idBuyer":"t.idSeller";
        String sql = "SELECT * FROM transactions t JOIN products p ON t.idProd=p.idProd" +
                " JOIN contracts c on t.idContract=c.idContract" +
                " where "+id+" = ? ";

        connect = ConnectionDB.connectDb();
        ObservableList<LocalWrapper> wrapperList = FXCollections.observableArrayList();
        Product product=null;
        Transaction transaction=null;
        Contract contract=null;


        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt( 1,idUser );
            result = prepare.executeQuery();

            while (result.next()) {
                if(result.getString( "type").equals( "BIEN" )) {
                    product =new Bien(result.getInt("idProd"),
                            result.getInt("idUser"),
                            result.getString("name"),
                            result.getString("descreption"),
                            "",
                            result.getFloat("price"),
                            result.getFloat("quantity"),
                            result.getBoolean("state"),
                            result.getTimestamp("timestamp"),
                            Categorie.valueOf(result.getString("category")));
                    product.setAllImagesSources(CrudBien.getInstance().selectImagesById( product.getId() ) );
                    product.setImgSource( product.getImageSourceByIndex( 0 ) );
                }
                contract=new Contract( result.getInt( "idContract" ),
                        result.getString( "title" ),
                        result.getString( "effectiveDate" ),
                        result.getString( "terminationDate" ),
                        result.getString( "purpose" ),
                        result.getString( "termsAndConditions" ),
                        PaymentMethod.valueOf( result.getString( "paymentMethod" ) ),
                        result.getString( "recivingLocation" ));

                transaction=new Transaction(result.getInt("idTransaction"),
                        result.getInt("idProd"),
                        result.getInt("idContract"),
                        result.getInt("idSeller"),
                        result.getInt("idBuyer"),
                        result.getFloat("pricePerUnit"),
                        result.getInt("quantity"),
                        TransactionMode.valueOf(result.getString("transactionMode")),
                        result.getTimestamp("timeStamp") );
                wrapperList.add( new LocalWrapper(product, transaction, contract ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wrapperList;
    }
}
