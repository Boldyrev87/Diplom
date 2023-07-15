package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    @Value
    public static class CardInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String holder;
        private String cardCVC;
    }

    // "Номер Карты"
    public static String getValidActiveCard() {
        return "4444 4444 4444 4441";
    }

    public static String getValidBlockedCard() {
        return "4444 4444 4444 4442";
    }

    public static String getInvalidPatternNumberCard() {
        Faker faker = new Faker(new Locale("en"));
        return faker.numerify("#### #### #### ###");
    }


    public static String getEmptyNumberCard() {
        return "";
    }

    public static String getLetterNumberCard() {
        return "abcd abcd abcd abcd";
    }


    // "Месяц"
    public static String getCurrentMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }


    public static String getLetterMonth() {
        return "ab";
    }


    public static String getEmptyMonth() {
        return "";
    }


    public static String getCurrentYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }


    public static String getLetterYear() {
        return "ab";
    }


    public static String getEmptyYear() {
        return "";
    }


    public static String getValidHolder() {
        Faker faker = new Faker((new Locale("en")));
        return faker.name().firstName() + " " + faker.name().lastName();
    }


    public static String getInvalidLocaleHolder() {
        Faker faker = new Faker((new Locale("ru")));
        return faker.name().firstName() + " " + faker.name().lastName();
    }


    public static String getEmptyHolder() {
        return "";
    }


    public static String getValidCVC() {
        int min = 1;
        int max = 999;
        double rnd = (Math.random() * (max - min)) + min;
        return String.format("%,03.0f", rnd);
    }


    public static String getLetterCVC() {
        return "abc";
    }

    public static String getSpecialLetterCVC() {
        return "###";
    }

    public static String getEmptyCVC() {
        return "";
    }

}




