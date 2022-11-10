/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import timestamp.TimeStampService;

/**
 *
 * @author user
 */
public class TimeStampClient {

//    public static void main(String[] args) {
//
//    }

    public long startClient() {
        long timeStamp = 0;
        try {
            TTransport transport;

            transport = new TSocket("localhost", 9090);//9090
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            TimeStampService.Client client = new TimeStampService.Client(protocol);

            timeStamp = client.getTimeStamp();
            System.out.println("timeStamp: " + timeStamp);

            //close transport
            transport.close();

        } catch (TException x) {
            System.err.println(x);
        }

        return timeStamp;
    }
}
