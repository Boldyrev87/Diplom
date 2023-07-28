package ru.netology.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelper {
    private DataHelper() {

    }

    public static String generateDate(long addYear, String pattern) {
        return LocalDate.now().plusYears(addYear).format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String validYear() {
        return generateDate(5, "YY");
    }

    public static String validMonth() {
        return generateDate(5, "MM");
    }

    public static String cardNumberApproved() {

        String number = "4444 4444 4444 4441";
        return number;
    }

    public static String cardNumberDeclined() {

        return "4444 4444 4444 4442";
    }

    public static String invalidCardNumber() {
        String number = "4444 4444 4444 4450";
        return number;
    }


    public static String cardNumberEmpty() {
        String number = "";
        return number;
    }

    public static String holder() {
        String name = "BOLDYREV ANATOLY";
        return name;
    }


    public static Card getValidCardInfo() {

        return new Card(cardNumberApproved(), validMonth(), validYear(), holder(), "999");
    }
}
