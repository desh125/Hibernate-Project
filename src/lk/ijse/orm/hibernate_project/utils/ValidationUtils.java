package lk.ijse.orm.hibernate_project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final String KEY_MONEY_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";
    private static final String QTY_PATTERN = "^[1-9][0-9]*$"; // Positive integer
    private static final String STRING_PATTERN = "^[a-zA-Z]+$";


    public static boolean isValidKeyMoney(String keyMoney) {
        Pattern pattern = Pattern.compile(KEY_MONEY_PATTERN);
        Matcher matcher = pattern.matcher(keyMoney);
        return matcher.matches();
    }

    public static boolean isValidQuantity(String qty) {
        Pattern pattern = Pattern.compile(QTY_PATTERN);
        Matcher matcher = pattern.matcher(qty);
        return matcher.matches();
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        String regex = "0\\d{9}";
        return phoneNumber.matches(regex);
    }


    public static boolean isValidString(String input) {
        Pattern pattern = Pattern.compile(STRING_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}

