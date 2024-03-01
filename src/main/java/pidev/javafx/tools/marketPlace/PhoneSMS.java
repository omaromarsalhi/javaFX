package pidev.javafx.tools.marketPlace;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class PhoneSMS{

    public static final String ACCOUNT_SID = "ACa1ee1543220372f81af3f07782b6f09a";
    public static final String AUTH_TOKEN = "96a7360ecf33b0dd79bf72a3d03f7264";

    private static PhoneSMS instance;

    private PhoneSMS() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static PhoneSMS getInstance() {
        if (instance == null)
            instance = new PhoneSMS();
        return instance;
    }


    public void sendSMS(String reciver,String body) {
        Message message = Message
                .creator(
                        new PhoneNumber(reciver),
                        new PhoneNumber("+16097704463"),
                        body
                ).create();
    }
}
