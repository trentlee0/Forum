package com.example.forum.util;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.equals("") || str.trim().equals("");
    }

    public static boolean isEmpty(String... strings) {
        for (String string : strings) {
            if (isEmpty(string)) return true;
        }
        return false;
    }

    public static String htmlToText(String input, int length) {
        if (input == null || input.trim().equals("")) {
            return "";
        }
        input = input.replaceAll("<img(.*)[(\\>)>]", "[图片]");
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "")
                .replaceAll("<[^>]*>", "");
        str = str.replaceAll("[(/>)<]", "");
        int len = str.length();
        if (len <= length) {
            return str;
        } else {
            str = str.substring(0, length);
            str += "......";
        }
        return str;
    }

    public static String htmlToText(String input) {
        return htmlToText(input, input.length());
    }
}
