package ru.netology;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliverySelenideTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void ShouldBeSent() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String currentDate = generateDate(5,"dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Ольга");
        $("[data-test-id='phone'] input").setValue("+79813254867");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }
}