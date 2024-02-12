package api.facture;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
    public static void main(String[] args) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try{
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("admin@example.com", "Example.com Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                            new InternetAddress("earlbps@gmail.com", "Mr. User"));
            msg.setSubject("Your Example.com account has been activated");
            msg.setText("This is a test");
            Transport.send(msg);
        }catch(Exception e){}
    }
}
