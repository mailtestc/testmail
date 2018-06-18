import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class TestMail {
    private static ChromeDriver driver;
    String url = "https://mail.ru/";
    String emailLogin = "testcsi";
    String emailPass = "123456qW";
    String expSubject = "Test mail";
    String expSender = "Тест Тестов <testcsi@mail.ru>";
    String expBody = "Lorem ipsum";

    @BeforeTest
    public void s00_LaunchBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/ChromeDriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        driver = new ChromeDriver(chromeOptions);
    }

    @Test
    public void s01_MailRuOpen() {

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
//      Login
        WebElement login = driver.findElementById("mailbox:login");
        login.sendKeys(emailLogin);
        WebElement pass = driver.findElementById("mailbox:password");
        pass.sendKeys(emailPass);
        WebElement submit = driver.findElementById("mailbox:submit");
        submit.click();
        new WebDriverWait(driver, 20);

        if (ExpectedConditions.elementToBeClickable(By.className("b-datalist__item__subj")) == null) {
            System.out.println("Step02 Login FAILED");
        } else {
            System.out.println("Step02 Login PASSED");
        }
    }

    @Test
    public void s03_OpenMail() {
//      Login
        try {
            WebElement pageloaded = (new WebDriverWait(driver, 5)).until(ExpectedConditions.elementToBeClickable(By.className("b-datalist__item__wrapper")));
        } catch (Exception e) {
        }
        try {
            WebElement maillink = driver.findElementByClassName("b-datalist__item__info");
            maillink.click();
        } catch (Exception e) {
        }

        if (ExpectedConditions.elementToBeClickable(By.className("b-datalist__item__info")) == null) {
            System.out.println("Step03 OpenMail FAILED");
        } else {
            System.out.println("Step03 OpenMail PASSED");
        }
    }

    @Test
    public void s04_CheckMail() {
        String senderemail, sendername, subject, bodyemail;
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
            System.out.println("Step04 CheckMail FAILED");
        } else {
            System.out.println("Step04 CheckMail PASSED");
        }
    }

    @Test
    public void s06_closeBrowser() {
        new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(driver.findElementByClassName("games")));
        driver.close();
    }
}
