import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;


public class TestMail {
    private static ChromeDriver driver;
    // Задаем адрес почтового сервера
    String url = "https://mail.ru/";
    // Задаем логин и пароль
    String emailLogin = "testcsi";
    String emailPass = "123456qW";
    // Задаем ожидаемые значения отправителя, темы письма и часть содержимого письма
    String expSubject = "Test mail";
    String expSender = "Тест Тестов <testcsi@mail.ru>";
    String expBody = "Lorem ipsum";

    @BeforeTest
    public void s00_LaunchBrowser() {
        // Запуск Chrome
        System.setProperty("webdriver.chrome.driver", "src/ChromeDriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        driver = new ChromeDriver(chromeOptions);
    }

    @Test
    public void s01_MailRuOpen() {
        // Открытие страницы Mail.ru
        driver.get(url);
        String currentUrl = driver.getCurrentUrl();

        if (currentUrl.equals(url)) {
            System.out.println("Step01 MailRuOpen PASSED");
        } else {
            System.out.println("Step01 MailRuOpen FAILED");

        }
    }

    @Test
    public void s02_Login() {
        // Авторизация почтового ящика
        WebElement login = driver.findElementById("mailbox:login");
        login.sendKeys(emailLogin);
        WebElement pass = driver.findElementById("mailbox:password");
        pass.sendKeys(emailPass);
        WebElement submit = driver.findElementById("mailbox:submit");
        submit.click();
        WebElement pageloaded = (new WebDriverWait(driver, 5)).until(ExpectedConditions.elementToBeClickable(By.className("b-datalist__item__wrapper")));

        // Проверка загрузки страницы Входящие

        if (driver.getTitle().contains("Входящие")) {
            System.out.println("Step02 Login PASSED");
        } else {
            System.out.println("Step02 Login FAILED");
        }
    }

    @Test
    public void s03_OpenMail() {
        // Открытие письма
        try {
            // Ожидание загрузки страницы
            WebElement pageloaded = (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.className("b-datalist__item__panel")));
        } catch (Exception e) {
        }

        // Здесь нужен более сложный сценарий выбора требуемого письма. Сейчас реализован поиск по contains темы письма.
        WebElement subjLink = driver.findElement(By.xpath("//div[@class='b-datalist__item__panel']"));
        WebElement mailLink = subjLink.findElement(By.xpath("//a[contains(.,'" + expSubject + "')]"));

        mailLink.click();

/*
        if (ExpectedConditions.visibilityOf(driver.findElement(By.className("b-letter__body"))) == null) {
            System.out.println("Step03 OpenMail FAILED");
        } else {
            System.out.println("Step03 OpenMail PASSED");
        }*/
    }
/*
    @Test
    public void s04_CheckMail() {
        String senderemail, subject, bodyemail;
        try {
            WebElement pageloaded = (new WebDriverWait(driver, 5)).until(ExpectedConditions.elementToBeClickable(By.className("b-letter__foot__boxz")));
        } catch (Exception e) {
        }
            subject = driver.findElement(By.className("b-letter__head__subj__text")).getText();
        senderemail = driver.findElement(By.className("b-letter__head__addrs__from")).getText();
        bodyemail = driver.findElement(By.className("b-letter__body")).getText();

        if (subject.equals(expSubject)) {
            if (bodyemail.contains(expBody)) {
                if (senderemail.equals(expSender)) {
                    System.out.println("Step04 CheckMail PASSED");
                }
                else {
                    System.out.println("Step04 CheckMail FAILED, senderemail is " + senderemail);
                }
            }
            else {
                System.out.println("Step04 CheckMail FAILED, bodyemail is " + bodyemail);
                }
        }
         else {
            System.out.println("Step04 CheckMail FAILED, subject is " + subject);
        }
    }

    @Test
    public void s05_LogOut() {
        WebElement submit = driver.findElementById("PH_logoutLink");
        submit.click();

        WebElement newlogin = driver.findElementById("mailbox:login");
        if (newlogin == null) {
            System.out.println("Step04 LogOut FAILED");
        } else {
            System.out.println("Step04 LogOut PASSED");
        }
    }

    @Test
    public void s06_closeBrowser() {
        new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(driver.findElementByClassName("games")));
        driver.close();
    }
*/}
