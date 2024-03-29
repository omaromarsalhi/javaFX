package pidev.javafx.model.Ticket;

import java.util.Date;
import java.util.Objects;

public class Ticket {
    public static int idTicket;
    private static String customName;
    private static Date date;
    private static String type;
    private static int ticketNumber;

    public Ticket(int idTicket, String customName, String type, int ticketNumber) {
        this.idTicket = idTicket;
        this.customName = customName;

        this.type = type;
        this.ticketNumber = ticketNumber;
    }

    public Ticket() {
    }

    public Ticket(String customName, String type, int ticketNumber) {
        this.customName = customName;
        this.type = type;
        this.ticketNumber = ticketNumber;
    }

    public static int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public static String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public static Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return idTicket == ticket.idTicket && ticketNumber == ticket.ticketNumber && Objects.equals(customName, ticket.customName) && Objects.equals(date, ticket.date) && type == ticket.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTicket, customName, date, type, ticketNumber);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "idTicket=" + idTicket +
                ", customName='" + customName + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", ticketNumber=" + ticketNumber +
                '}';
    }

}
