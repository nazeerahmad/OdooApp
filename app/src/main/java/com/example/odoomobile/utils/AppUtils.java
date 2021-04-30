package com.example.odoomobile.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {

    public static String getDate(String dateTime){
        String dateStr="";
        try {
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date d = f.parse(dateTime);

            DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
//            System.out.println("Date: " + date.format(d));
            dateStr = date.format(d);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }
}
