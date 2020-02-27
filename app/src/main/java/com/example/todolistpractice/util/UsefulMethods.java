package com.example.todolistpractice.util;

import java.text.DateFormat;
import java.util.Date;

public class UsefulMethods {
    public static String convertDate(Long dateLong){
        DateFormat dateFormat = DateFormat.getDateInstance();

        // Creating date from milliseconds
        Date date = new Date(dateLong);

        return dateFormat.format(date.getTime());
    }
}
