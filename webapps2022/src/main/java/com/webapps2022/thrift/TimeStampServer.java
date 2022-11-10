/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.thrift;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import timestamp.TimeStampService;

/**
 *
 * @author user
 */
@Singleton
@Startup
public class TimeStampServer {

    public static TimeStampHandler handler;
    public static TimeStampService.Processor processor;
    public static TServerTransport serverTransport;
    public static TServer server;

    @PostConstruct
    public void init() {
        startThriftServer();
    }


    public void startThriftServer() {
        try {
            handler = new TimeStampHandler();
            processor = new TimeStampService.Processor(handler);

            Runnable simple = new Runnable() {
                @Override
                public void run() {
                    simple(processor);
                }
            };

            new Thread(simple).start();
            System.out.println("timestamp thrift server started");

        } catch (Exception x) {
            System.err.println(x);
            System.out.println("server unable to start");
        }
    }

    @PreDestroy
    public void stopServer() {
        server.stop();

    }

    public static void simple(TimeStampService.Processor processor) {
        try {
            serverTransport = new TServerSocket(9090);
            server = new TSimpleServer(new Args(serverTransport).processor(processor));

            System.out.println("Starting the server in Thread " + Thread.currentThread().getId());
            server.serve();
        } catch (TTransportException e) {
            System.err.println(e);
        }
    }

}
