package pidev.javafx.controller.reclamation;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.net.PasswordAuthentication;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailController {

    public static void sendEmail(String recipientEmail, String subject, String body) {
        // Configurez les paramètres de connexion SMTP
        String host = "smtp.gmail.com";
        int port = 587;
        String username = "latifabenzaied23@gmail.com";
        String password = "zipe psyi qacj vmkl";

        // Créez et configurez une session JavaMail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("omarmarrakchi22@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setContent(body, "text/html");
            Transport.send(message);

        } catch (MessagingException e) {
            // Gérez les erreurs de messagerie
            e.printStackTrace();
        }
    }

    public static void sendEmailWithHtmlTemplate(String recipientEmail, String subject, String htmlFilePath) {
        try {
            String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)));
            sendEmail(recipientEmail, subject, htmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String addDynamicText(String htmlContent, String dynamicText) {
        return htmlContent.replace("[[dynamicText]]", dynamicText);
    }
}
