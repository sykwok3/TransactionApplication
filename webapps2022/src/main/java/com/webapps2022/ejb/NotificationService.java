package com.webapps2022.ejb;
import com.webapps2022.entity.Notifications;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface NotificationService {
    public List<Notifications> getNotificationList(String currentUsername);

    public void insertNotification(String type, String currentUsername, String requestFromUsername, double paymentAmount, String senderCurrency, String recieverCurrency, Date timeStamp);
}