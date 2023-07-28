package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.SneakyThrows;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    SelenideElement title = $x("//*[contains(text(), 'Путешествие дня')]");
    SelenideElement buttonBuy = $x("//*[contains(text(), 'Купить')]");
    SelenideElement buttonBuyInCredit = $x("//*[(text() = 'Купить в кредит')]");

    public MainPage() {
        title.shouldBe(Condition.visible);
        buttonBuy.shouldBe(Condition.visible);
        buttonBuyInCredit.shouldBe(Condition.visible);
    }

    @SneakyThrows
    public PaymentPage chooseCardPayment() {
        buttonBuy.click();
        return new PaymentPage();
    }

    public CreditPage chooseCreditPayment() {
        buttonBuyInCredit.click();
        return new CreditPage();
    }
}

