package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Card {
    private String number;
    private String month;
    private String year;
    private String owner;
    private String cvv;

    public Card withNumber(String number) {
        this.number = number;
        return this;
    }

    public Card withMonth(String month) {
        this.month = month;
        return this;
    }

    public Card withYear(String year) {
        this.year = year;
        return this;
    }

    public Card withName(String owner) {
        this.owner = owner;
        return this;
    }

    public Card withCVV(String cvv) {
        this.cvv = cvv;
        return this;
    }
}
