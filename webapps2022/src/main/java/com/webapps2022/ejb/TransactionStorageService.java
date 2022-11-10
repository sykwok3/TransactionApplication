package com.webapps2022.ejb;

import com.webapps2022.entity.TransactionRecord;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface TransactionStorageService {

    public List<TransactionRecord> getRecordList(String currentUsername);

    public List<TransactionRecord> getRecordListAdmin();

    public void insertRecord(String recipientUsername, double paymentAmount, String currentUsername, String senderCurrency, String receiverCurrency, Date timeStamp);
}
