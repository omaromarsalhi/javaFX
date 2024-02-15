package pidev.javafx.model.MarketPlace;

import java.sql.Timestamp;

public class Transaction {
    private int idTransaction;
    private int idProd;
    private int idContract;
    private int idSeller;
    private int idBuyer;
    private float pricePerUnit;
    private int quantity;
    private TransactionMode transactionMode;
    private Timestamp effectiveDate;

    public Transaction() {
    }

    public Transaction(int idTransaction, int idProd,int idContract, int idSeller, int idBuyer, float pricePerUnit, int quantity, TransactionMode transactionMode, Timestamp effectiveDate) {
        this.idTransaction = idTransaction;
        this.idProd = idProd;
        this.idContract = idContract;
        this.idSeller = idSeller;
        this.idBuyer = idBuyer;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.transactionMode = transactionMode;
        this.effectiveDate = effectiveDate;
    }


    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public int getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(int idSeller) {
        this.idSeller = idSeller;
    }

    public int getIdBuyer() {
        return idBuyer;
    }

    public void setIdBuyer(int idBuyer) {
        this.idBuyer = idBuyer;
    }

    public float getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(float pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public TransactionMode getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(TransactionMode transactionMode) {
        this.transactionMode = transactionMode;
    }

    public Timestamp getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Timestamp effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
