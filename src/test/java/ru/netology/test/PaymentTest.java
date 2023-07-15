package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DBUtils;
import ru.netology.data.DataHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.netology.data.DataHelper.*;

public class PaymentTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080/");
        DBUtils.clearTables();
    }

    @Test
    @DisplayName("Успешная оплата по активной карте")
    void shouldBuy() {
        CardInfo card = new DataHelper.CardInfo(getValidActiveCard(), getCurrentMonth(), getCurrentYear(), getValidHolder(), getValidCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.checkPayment();
        paymentPage.formInput(card);
        paymentPage.checkApprovedNotification();
    }

    @Test
    @DisplayName("Отклонение оплаты по заблокированной карте")
    void shouldNotBuy() {
        CardInfo card = new CardInfo(getValidBlockedCard(), getCurrentMonth(), getCurrentYear(), getValidHolder(), getValidCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.checkPayment();
        paymentPage.formInput(card);
        paymentPage.checkDeclinedNotification();
    }

    @Test
    @DisplayName("Ввод некорректного номера карты")
    void shouldNotBuyInvalidPatternNumberCard() {
        CardInfo card = new CardInfo(getInvalidPatternNumberCard(), getCurrentMonth(), getCurrentYear(), getValidHolder(), getValidCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.checkPayment();
        paymentPage.formInput(card);
        paymentPage.checkNumberCardError();
        assertNull(DBUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Пустое поле Номер карты")
    void shouldNotBuyEmptyNumberCard() {
        CardInfo card = new CardInfo(getEmptyNumberCard(), getCurrentMonth(), getCurrentYear(), getValidHolder(), getValidCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.checkPayment();
        paymentPage.formInput(card);
        paymentPage.checkNumberCardError();
        assertNull(DBUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Ввод некорректных данных в поле Номер карты")
    void shouldNotBuyLetterNumberCard() {
        CardInfo card = new CardInfo(getLetterNumberCard(), getCurrentMonth(), getCurrentYear(), getValidHolder(), getValidCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.checkPayment();
        paymentPage.formInput(card);
        paymentPage.checkNumberCardError();
        assertNull(DBUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Ввод некорректных данных в поле Месяц")
    void shouldNotBuyLetterMonth() {
        CardInfo card = new CardInfo(getValidActiveCard(), getLetterMonth(), getCurrentYear(), getValidHolder(), getValidCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.checkPayment();
        paymentPage.formInput(card);
        paymentPage.checkMonthError();
        assertNull(DBUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Пустое поле Месяц")
    void shouldNotBuyEmptyMonth() {
        CardInfo card = new CardInfo(getValidActiveCard(), getEmptyMonth(), getCurrentYear(), getValidHolder(), getValidCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.checkPayment();
        paymentPage.formInput(card);
        paymentPage.checkMonthError();
        assertNull(DBUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Ввод некорректных данных в поле Год")
    void shouldNotBuyLetterYear() {
        CardInfo card = new CardInfo(getValidActiveCard(), getCurrentMonth(), getLetterYear(), getValidHolder(), getValidCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.checkPayment();
        paymentPage.formInput(card);
        paymentPage.checkYearError();
        assertNull(DBUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Пустое поле Год")
    void shouldNotBuyEmptyYear() {
        CardInfo card = new CardInfo(getValidActiveCard(), getCurrentMonth(), getEmptyYear(), getValidHolder(), getValidCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.checkPayment();
        paymentPage.formInput(card);
        paymentPage.checkYearError();
        assertNull(DBUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Ввод некорректных данных в поле Владелец")
    void shouldNotBuyInvalidLocaleHolder() {
        CardInfo card = new CardInfo(getValidActiveCard(), getCurrentMonth(), getCurrentYear(), getInvalidLocaleHolder(), getValidCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.checkPayment();
        paymentPage.formInput(card);
        paymentPage.checkHolderErrorPattern();
        assertNull(DBUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Пустое поле Владелец")
    void shouldNotBuyEmptyHolder() {
        CardInfo card = new CardInfo(getValidActiveCard(), getCurrentMonth(), getCurrentYear(), getEmptyHolder(), getValidCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.checkPayment();
        paymentPage.formInput(card);
        paymentPage.checkHolderError();
        assertNull(DBUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Ввод некорректных данных в поле CVC/CVV")
    void shouldNotBuyLetterCVC() {
        CardInfo card = new CardInfo(getValidActiveCard(), getCurrentMonth(), getCurrentYear(), getValidHolder(), getLetterCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.checkPayment();
        paymentPage.formInput(card);
        paymentPage.checkCVCError();
        assertNull(DBUtils.getPaymentStatus());
    }

    @Test
    @DisplayName("Пустое поле CVC/CVV")
    void shouldNotBuyEmptyCVC() {
        CardInfo card = new CardInfo(getValidActiveCard(), getCurrentMonth(), getCurrentYear(), getValidHolder(), getEmptyCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.checkPayment();
        paymentPage.formInput(card);
        paymentPage.checkCVCError();
        assertNull(DBUtils.getPaymentStatus());
    }

}


