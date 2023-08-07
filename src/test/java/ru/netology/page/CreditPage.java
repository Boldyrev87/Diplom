package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Card;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class CreditPage {
    SelenideElement titleCardPayment = $x("//h3[text()[contains(., 'Кредит по данным карты')]]");
    SelenideElement cardNumber = $x("//*[contains(text(), 'Номер карты')]/../span/input");
    SelenideElement month = $x("//*[contains(text(), 'Месяц')]/../*/input");
    SelenideElement year = $x("//*[contains(text(), 'Год')]/../*/input");
    SelenideElement owner = $x("//*[contains(text(), 'Владелец')]/../*/input");
    SelenideElement cvc = $x("//*[contains(text(), 'CVC/CVV')]/../*/input");
    SelenideElement continueButton = $x("//*[text()[contains(., 'Продолжить')]]");

    public CreditPage() {
        titleCardPayment.shouldBe(Condition.visible);
    }

    public void pay(Card data) {
        setElementValue(cardNumber, data.getNumber());
        setElementValue(month, data.getMonth());
        setElementValue(year, data.getYear());
        setElementValue(owner, data.getOwner());
        setElementValue(cvc, data.getCvv());
        continueButton.click();
    }

    private void setElementValue(SelenideElement element, String value) {
        element.click();
        element.setValue(value);
    }

    public void checkConfirmedNotification() {
        SelenideElement successfulNotification = $(".notification_status_ok .notification__content").shouldHave(Condition.text("Операция одобрена Банком."), Duration.ofMillis(20000));
        successfulNotification.shouldBe(Condition.visible);
    }

    public void checkRejectedNotification() {
        SelenideElement declineNotification = $(".notification_status_error .notification__content").shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."), Duration.ofMillis(15000));
        declineNotification.shouldBe(Condition.visible);
    }

    public void checkWrongFormatFieldNotification() {
        SelenideElement wrongFormat = $(".input__sub").shouldHave(Condition.text("Неверный формат"));
        wrongFormat.shouldBe(Condition.visible);
    }

    public void requiredFieldNotification() {
        SelenideElement empty = $(".input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
        empty.shouldBe(Condition.visible);
    }

}


