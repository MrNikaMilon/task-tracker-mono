package com.nion.tasktrackerpostman.utils;

import java.util.regex.Pattern;

public class CheckDataUtils {
    public static boolean checkEmail(String email) {
        return Pattern.compile("^\\S+@\\S+\\.\\S+$").matcher(email).matches();
    }
}
