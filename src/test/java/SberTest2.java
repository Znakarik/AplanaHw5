
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@RunWith(value = Parameterized.class)
public class SberTest2 {
    private static WebDriver webDriver;
//  Игнор - TestIgnore / try{} (catch)
    //endPoint.publish(address, new Class);
//    SOAP UPI - сервис для тестирование и автотестирования
//    Работу нашего функционала проверяют только автотесты
//    Data provider - парметр. тесты

    private String a, b, c;

    public SberTest2(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Parameterized.Parameters(name = "Ф: {0} И: {1} О: {2}")
    public static Iterable<String[]> dataForTest() {
        return Arrays.asList(new String[][]{
                {"Знакарик", "Знакарик", "Знакарик"},
                {"Апланов", "Аплан", "Апланович"},
                {"Кукумберов", "Кукумбер", "Кукумберович"},
        });
    }

    private static void setBrowser(String browserName) {
        System.getenv(BrowserType.SAFARI);
        if (BrowserType.CHROME.equals(browserName)) {
            webDriver = new ChromeDriver();
            System.setProperty("webdriver.chrome.driver", "//hw5/src/main/resources/chromedriver");
        } else if (BrowserType.SAFARI.equals(browserName)) {
            webDriver = new SafariDriver();
            System.setProperty("webdriver.safari.driver", "/Users/o/IdeaProjects/Aplana/hw5/src/main/resources/safaridriver");
        } else if (BrowserType.FIREFOX.equals(browserName)) {
            webDriver = new FirefoxDriver();
            System.setProperty("webdriver.gecko.driver", "/Users/o/IdeaProjects/Aplana/hw5/src/main/resources/geckodriver");
        }
    }

    private static void click(WebElement element) {
        WebDriverWait wait = new WebDriverWait(webDriver, 30);
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }

    @BeforeClass
    public static void TestUp() {
        setBrowser(BrowserType.CHROME);
        webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
    }

    @AfterClass
    public static void CleanUp() {
        webDriver.close();
        webDriver.quit();
    }

    @Test
    public void Sber() {
        webDriver.get("http://www.sberbank.ru/ru/person");

//      2.Нажать на – Страхование
        click(webDriver.findElement(By.xpath("//span[contains(text(),'Страхование')]")));
//      3.Выбрать – Путешествие и покупки
        click(webDriver.findElement(By.xpath("//div[contains(@class,'lg-menu__col')]//a[contains(text(),'Страхование путешественников')]")));

//      4.Проверить наличие на странице заголовка – Страхование путешественников

//        webDriver.get("https://www.sberbank.ru/ru/person/bank_inshure/insuranceprogram/life/travel");
        Assert.assertEquals("Страхование путешественников", By.xpath("//div[contains(@class,'product-teaser-full-width__image product-teaser-full-width__image_md')]//h2[contains(text(),'Страхование путешественников')]").findElement(webDriver).getAttribute("innerHTML").trim());

//      5. Нажать на – Оформить Онлайн
        click(webDriver.findElement(By.xpath("//b[contains(text(),'Оформить онлайн')]")));

//      6. На вкладке – Выбор полиса  выбрать сумму страховой защиты – Минимальная
        click(webDriver.findElement(By.xpath("//h3[contains(text(),'Минимальная')]")));
//
//      7. Нажать Оформить
        click(webDriver.findElement(By.xpath("//button[contains(text(),'Оформить')]")));

//      8. На вкладке Оформить заполнить поля:
//          1) Фамилию и Имя, Дату рождения застрахованных
        click(webDriver.findElement(By.xpath("//input[contains(@placeholder,'Surname')]")));
        webDriver.findElement(By.xpath("//input[contains(@placeholder,'Surname')]")).sendKeys("Znakarik");
//
        webDriver.findElement(By.xpath("//input[contains(@placeholder,'Name')]")).sendKeys("Znakarik");

        webDriver.findElement(By.xpath("//input[contains(@id,'birthDate_vzr_ins_0')]")).sendKeys("31.10.1996");

//          2) Данные страхователя: Фамилия, Имя, Отчество, Дата рождения, Пол
        click(webDriver.findElement(By.xpath("//input[contains(@id,'person_lastName')]")));
        webDriver.findElement(By.xpath("//input[contains(@id,'person_lastName')]")).sendKeys(a);

        webDriver.findElement(By.xpath("//input[contains(@id,'person_firstName')]")).sendKeys(b);

        webDriver.findElement(By.xpath("//input[contains(@id,'person_middleName')]")).sendKeys(c);

        webDriver.findElement(By.xpath("//input[contains(@id,'person_birthDate')]")).sendKeys("30.10.1996");

        click(webDriver.findElement(By.xpath("//label[contains(text(),'Женский')]")));

//        //Паспортные данные
        webDriver.findElement(By.xpath("//input[contains(@id,'passportSeries')]")).sendKeys("3110");
        webDriver.findElement(By.xpath("//input[contains(@id,'passportNumber')]")).sendKeys("100000");
        webDriver.findElement(By.xpath("//input[contains(@id,'documentDate')]")).sendKeys("30102016");

        click(webDriver.findElement(By.xpath("//input[contains(@id,'documentIssue')]")));
        webDriver.findElement(By.xpath("//input[contains(@id,'documentIssue')]")).sendKeys("РГУ им Косыгина");
//       Контактные данные не заполняем

//      9. Проверить, что все поля заполнены правильно - визуально?

//      10.   Нажать продолжить
        click(webDriver.findElement(By.xpath("//button[contains(text(),'Продолжить')]")));
//      11. Проверить, что появилось сообщение - Заполнены не все обязательные поля
        Assert.assertEquals("При заполнении данных произошла ошибка", webDriver.findElement(By.xpath("//div[contains(@class,'alert-form alert-form-error')]")).getAttribute("innerHTML").trim());
    }
}
