package com.example.forum.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtil {
    public static String now() {
        return format(new Date(), "yyyy-MM-dd hh:mm:ss");
    }

    public static String now(String format) {
        return format(new Date(), format);
    }

    public static String format(long timestamp) {
        return format(new Date(timestamp), "yyyy-MM-dd hh:mm:ss");
    }

    public static String format(Date date) {
        return format(date, "yyyy-MM-dd hh:mm:ss");
    }

    public static String format(long timestamp, String format) {
        return format(new Date(timestamp), format);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date parse(String text, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static long parseToLong(String text, String format) {
        return parse(text, format).getTime();
    }
}
