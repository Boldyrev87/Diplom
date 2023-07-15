package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {
    private SelenideElement title = $x("//*[@id=\"root\"]/div/h3");
    private List<SelenideElement> input = $$(".input__control");
    private SelenideElement cardNumber = input.get(0);
    private SelenideElement month = input.get(1);
    private SelenideElement year = input.get(2);
    private SelenideElement holder = input.get(3);
    private SelenideElement cvc = input.get(4);
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement cardNumberError = $(byText("Номер карты")).parent().$(".input__sub");
    private SelenideElement monthError = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement yearError = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement holderError = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement cvcError = $(byText("CVC/CVV")).parent().$(".input__sub");
    private SelenideElement approvedNotification = $(".notification_status_ok");
    private SelenideElement declinedNotification = $(".notification_status_error");

    public PaymentPage() {
        title.shouldBe(visible);
        title.shouldHave(text("Оплата по карте"));
    }

    public void formInput(DataHelper.CardInfo card) {
        cardNumber.setValue(card.getCardNumber());
        month.setValue(card.getMonth());
        year.setValue(card.getYear());
        holder.setValue(card.getHolder());
        cvc.setValue(card.getCardCVC());
        continueButton.click();
    }

    public void checkApprovedNotification() {
        approvedNotification.shouldBe(visible, Duration.ofMillis(15000));
        approvedNotification.shouldHave(text("Операция одобрена Банком"));
    }

    public void checkDeclinedNotification() {
        declinedNotification.shouldBe(visible, Duration.ofMillis(15000));
        approvedNotification.shouldHave(text("Ошибка! Банк отказал в проведении операции."));
    }

    public void checkNumberCardError() {
        cardNumberError.shouldBe(visible);
        cardNumberError.shouldHave(text("Неверный формат"));
        monthError.shouldNotBe(visible);
        yearError.shouldNotBe(visible);
        holderError.shouldNotBe(visible);
        cvcError.shouldNotBe(visible);
    }

    public void checkMonthError() {
        monthError.shouldBe(visible);
        monthError.shouldHave(text("Неверный формат"));
        cardNumberError.shouldNotBe(visible);
        yearError.shouldNotBe(visible);
        holderError.shouldNotBe(visible);
        cvcError.shouldNotBe(visible);
    }

    public void checkYearError() {
        yearError.shouldBe(visible);
        yearError.shouldHave(text("Неверный формат"));
        cardNumberError.shouldNotBe(visible);
        monthError.shouldNotBe(visible);
        holderError.shouldNotBe(visible);
        cvcError.shouldNotBe(visible);
    }

    public void checkHolderError() {
        holderError.shouldBe(visible);
        holderError.shouldHave(text("Поле обязательно для заполнения"));
        cardNumberError.shouldNotBe(visible);
        monthError.shouldNotBe(visible);
        yearError.shouldNotBe(visible);
        cvcError.shouldNotBe(visible);
    }

    public void checkHolderErrorPattern() {
        holderError.shouldBe(visible);
        holderError.shouldHave(text("Неверный формат"));
        cardNumberError.shouldNotBe(visible);
        monthError.shouldNotBe(visible);
        yearError.shouldNotBe(visible);
        cvcError.shouldNotBe(visible);
    }

    public void checkCVCError() {
        cvcError.shouldBe(visible);
        cvcError.shouldHave(text("Неверный формат"));
        cardNumberError.shouldNotBe(visible);
        monthError.shouldNotBe(visible);
        yearError.shouldNotBe(visible);
        holderError.shouldNotBe(visible);
    }
}
