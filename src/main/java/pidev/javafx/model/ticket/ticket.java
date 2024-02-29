package pidev.javafx.model.ticket;

import java.util.Date;
import java.util.Objects;

public class ticket {
    private int idTicket;
    private String customName;
    private Date date;
    private String type;
    private int ticketNumber;

    public ticket(int idTicket, String customName, String type, int ticketNumber) {
        this.idTicket = idTicket;
        this.customName = customName;

        this.type = type;
        this.ticketNumber = ticketNumber;
    }

    public ticket() {
    }

    public ticket(String customName, String type, int ticketNumber) {
        this.customName = customName;
        this.type = type;
        this.ticketNumber = ticketNumber;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ticket ticket = (ticket) o;
        return idTicket == ticket.idTicket && ticketNumber == ticket.ticketNumber && Objects.equals(customName, ticket.customName) && Objects.equals(date, ticket.date) && type == ticket.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTicket, customName, date, type, ticketNumber);
    }

    @Override
    public String toString() {
        return "ticket{" +
                "idTicket=" + idTicket +
                ", customName='" + customName + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", ticketNumber=" + ticketNumber +
                '}';
    }

}
