package pidev.javafx.model.Contrat;

public class Contract {

    private int idContract;
    private String title;
    private String effectiveDate;
    private String terminationDate;
    private String purpose;
    private String termsAndConditions;
    private PaymentMethod paymentMethod;
    private String recingLocation;

    public Contract(int idContract, String title, String effectiveDate, String terminationDate, String purpose, String termsAndConditions, PaymentMethod paymentMethod,String recingLocation) {
        this.idContract = idContract;
        this.title = title;
        this.effectiveDate = effectiveDate;
        this.terminationDate = terminationDate;
        this.purpose = purpose;
        this.termsAndConditions = termsAndConditions;
        this.paymentMethod = paymentMethod;
        this.recingLocation = recingLocation;
    }


    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContrat) {
        this.idContract = idContrat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(String terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRecingLocation() {
        return recingLocation;
    }

    public void setRecingLocation(String recingLocation) {
        this.recingLocation = recingLocation;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "idContract=" + idContract +
                ", title='" + title + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", terminationDate='" + terminationDate + '\'' +
                ", purpose='" + purpose + '\'' +
                ", termsAndConditions='" + termsAndConditions + '\'' +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
