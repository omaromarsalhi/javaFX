package pidev.javafx.crud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pidev.javafx.model.Contrat.Contract;
import pidev.javafx.model.Contrat.PaymentMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudContract implements CrudInterface<Contract> {

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private static CrudContract instance;

    private CrudContract() {
    }

    public static CrudContract getInstance() {
        if (instance == null)
            instance = new CrudContract();
        return instance;
    }

    @Override
    public void addItem(Contract contract) {
        String sql = "INSERT INTO contracts (title, terminationDate, purpose, termsAndConditions, paymentMethod,recivingLocation) " +
                "VALUES (?, ?, ?, ?, ? ,?)";

        connect = ConnectionDB.connectDb();

        try {
            prepare = connect.prepareStatement( sql );
            prepare.setString( 1, contract.getTitle() );
            prepare.setString( 2, contract.getTerminationDate() );
            prepare.setString( 3, contract.getPurpose() );
            prepare.setString( 4, contract.getTermsAndConditions() );
            prepare.setString( 5, contract.getPaymentMethod().toString() );
            prepare.setString( 6, contract.getRecingLocation() );
            prepare.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateItem(Contract contract) {
        try {

            String updateQuery = "UPDATE contracts SET title = ?," +
                    " terminationDate = ?, purpose = ?, termsAndConditions = ?, paymentMethod = ? ,recingLocation= ?" +
                    "WHERE idContrat = ?";

            connect = ConnectionDB.connectDb();

            prepare = connect.prepareStatement( updateQuery );
            prepare.setString( 1, contract.getTitle() );
            prepare.setString( 2, contract.getTerminationDate() );
            prepare.setString( 3, contract.getPurpose() );
            prepare.setString( 4, contract.getTermsAndConditions() );
            prepare.setString( 5, contract.getPaymentMethod().toString() );
            prepare.setString( 6, contract.getRecingLocation() );
            prepare.executeUpdate();
            // Handle any exceptions related to database operations
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (log, show error message, etc.)
        }
    }

    @Override
    public ObservableList<Contract> selectItems() {
        ObservableList<Contract> contractList = FXCollections.observableArrayList();
        try {
            // Assuming you have a database table named "contrats"
            String selectQuery = "SELECT * FROM contracts";

            connect = ConnectionDB.connectDb();


            prepare = connect.prepareStatement( selectQuery );
            result = prepare.executeQuery();
            while (result.next()) {
                contractList.add( new Contract( result.getInt( "idContract" ),
                        result.getString( "title" ),
                        result.getString( "effectiveDate" ),
                        result.getString( "terminationDate" ),
                        result.getString( "purpose" ),
                        result.getString( "termsAndConditions" ),
                        PaymentMethod.valueOf( result.getString( "paymentMethod" ) ),
                        result.getString( "recingLocation" )) );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contractList;
    }

    @Override
    public Contract findById(int id) {
        try {
            // Assuming you have a database table named "contrats"
            String selectQuery = "SELECT * FROM contracts where idContract= ?";

            connect = ConnectionDB.connectDb();


            prepare = connect.prepareStatement( selectQuery );
            prepare.setInt( 1, id );
            result = prepare.executeQuery();
            if (result.next()) {
                return new Contract( result.getInt( "idContract" ),
                        result.getString( "title" ),
                        result.getString( "effectiveDate" ),
                        result.getString( "terminationDate" ),
                        result.getString( "purpose" ),
                        result.getString( "termsAndConditions" ),
                        PaymentMethod.valueOf( result.getString( "paymentMethod" ) ),
                        result.getString( "recingLocation" ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Contract selectFirstItem() {
        return null;
    }


    public Contract selectLastItem() {
        String sql = "SELECT * FROM contracts ORDER BY idContract DESC LIMIT 1";
        connect = ConnectionDB.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                return new Contract(result.getInt( "idContract" ),
                        result.getString( "title" ),
                        result.getString( "effectiveDate" ),
                        result.getString( "terminationDate" ),
                        result.getString( "purpose" ),
                        result.getString( "termsAndConditions" ),
                        PaymentMethod.valueOf( result.getString( "paymentMethod" ) ),
                        result.getString( "recivingLocation" ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteItem(int id) {
        try {
            String deleteQuery = "DELETE FROM contracts WHERE idContrat = ?";

            connect = ConnectionDB.connectDb();

            prepare = connect.prepareStatement( deleteQuery );
            prepare.setInt( 1, id );
            prepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
