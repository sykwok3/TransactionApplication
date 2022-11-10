/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webapps2022.thrift;

import timestamp.TimeStampService;

/**
 *
 * @author user
 */
public class TimeStampHandler implements TimeStampService.Iface {

    @Override
    public long getTimeStamp() {
            long dateTime = System.currentTimeMillis();
        return dateTime;
    }

}
