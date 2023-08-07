package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.Card;
import ru.netology.data.DBUtils;
import ru.netology.data.DataHelper;
import ru.netology.data.PaymentEntity;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

public class PaymentTest {
    MainPage tour;
    PaymentPage card;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        DBUtils.prepareDb();
        open(System.getProperty("sut.url"));
        tour = new MainPage();
        card = tour.chooseCardPayment();
    }

    @Test
    @DisplayName("Успешная оплата по активной карте")
    void shouldBeSuccessfulCard() {
        Card cardInfo = DataHelper.getValidCardInfo();
        card.pay(cardInfo);
        card.checkApprovedNotification();
        PaymentEntity entity = DBUtils.paymentEntity();
        Assertions.assertEquals("APPROVED", entity.getStatus());
    }

    @Test
    @DisplayName("Отклонение оплаты по заблокированной карте")
    void shouldBeDeclinedCard() {
        card.pay(DataHelper.getValidCardInfo().withNumber(DataHelper.cardNumberDeclined()));
        card.checkDeclinedNotification();
        PaymentEntity entity = DBUtils.paymentEntity();
        Assertions.assertEquals("DECLINED", entity.getStatus());
    }

    @Test
    @DisplayName("Пустое поле Номер карты")
    void shouldWithEmptyNumberCard() {
        card.pay(DataHelper.getValidCardInfo().withNumber(DataHelper.cardNumberEmpty()));
        card.checkWrongFormatFieldMessage();
        DBUtils.assertDbEmpty();
    }

    @Test
    @DisplayName("Ввод некорректных данных в поле Номер карты")
    void shouldIncorrectNumberCard() {
        card.pay(DataHelper.getValidCardInfo().withNumber("johnwick"));
        card.checkWrongFormatFieldMessage();
        DBUtils.assertDbEmpty();
    }

    @Test
    @DisplayName("Ввод некорректного номера карты")
    void shouldNotBuyInvalidPatternNumberCard() {
        card.pay(DataHelper.getValidCardInfo().withNumber(DataHelper.invalidCardNumber()));
        card.checkDeclinedNotification();
        PaymentEntity entity = DBUtils.paymentEntity();
        Assertions.assertNotNull(entity);
    }

    @Test
    @DisplayName("Пустое поле Месяц")
    void shouldWithEmptyMonth() {
        card.pay(DataHelper.getValidCardInfo().withMonth(""));
        card.checkWrongFormatFieldMessage();
        DBUtils.assertDbEmpty();
    }

    @Test
    @DisplayName("Ввод некорректных данных в поле Месяц")
    void shouldNotBuyLetterMonth() {
        card.pay(DataHelper.getValidCardInfo().withMonth("johnwick"));
        card.checkWrongFormatFieldMessage();
        DBUtils.assertDbEmpty();
    }

    @Test
    @DisplayName("Пустое поле Год")
    void shouldWithEmptyYear() {
        card.pay(DataHelper.getValidCardInfo().withYear(""));
        card.checkWrongFormatFieldMessage();
        DBUtils.assertDbEmpty();
    }

    @Test
    @DisplayName("Ввод некорректных данных в поле Год")
    void shouldNotBuyLetterYear() {
        card.pay(DataHelper.getValidCardInfo().withYear("johnwick"));
        card.checkWrongFormatFieldMessage();
        DBUtils.assertDbEmpty();
    }

    @Test
    @DisplayName("Пустое поле Владелец")
    void shouldWithEmptyHolder() {
        card.pay(DataHelper.getValidCardInfo().withName(""));
        card.requiredFieldNotification();
        DBUtils.assertDbEmpty();
    }

    @Test
    @DisplayName("Ввод некорректных данных в поле Владелец")
    void shouldNotBuyInvalidLocaleHolder() {
        card.pay(DataHelper.getValidCardInfo().withName("98765"));
        card.checkWrongFormatFieldMessage();
        DBUtils.assertDbEmpty();
    }


    @Test
    @DisplayName("Пустое поле CVC/CVV")
    void shouldWithEmptyCVC() {
        card.pay(DataHelper.getValidCardInfo().withCVV(""));
        card.requiredFieldNotification();
        DBUtils.assertDbEmpty();
    }

    @Test
    @DisplayName("Ввод некорректных данных в поле CVC/CVV")
    void shouldNotBuyLetterCVC() {
        card.pay(DataHelper.getValidCardInfo().withCVV("johnwick"));
        card.checkWrongFormatFieldMessage();
        DBUtils.assertDbEmpty();
    }
}


