import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverExample {
    public static void main(String[] args) {
        // создаем новый экземпляр html unit driver
        // Обратите внимание, что последующий код не закладывается на
        // конкретную, имплементацию, а только на интерфейс WebDriver.
        System.setProperty("webdriver.chrome.driver", "resources\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.get("http://eda.ru/recepty/vypechka-deserty/jogurtovoe-morozhenoe-s-avokado-ogurcom-28448?from=recipescatalog");
        // По-другому это можно сделать так:
        // driver.navigate().to("http://www.google.com");

/*        WebElement select = (driver.findElement(By.cssSelector(".selectBox-label")));
        select.click();
        select.findElement(By.name("value=4"));
        select.click();*/

   /*     WebElement selects = driver.findElement(By.id("NormalizedPortionsCount"));
        List<WebElement> allOptions = selects.findElements(By.tagName("option"));
        for (WebElement option : allOptions) {
            System.out.println(option.getText());
         //   System.out.println(String.format("Значение: %s", allOptions));
           // option.click();
        }*/
        WebElement selector=(driver.findElement(By.cssSelector(".scroll-content")));
        System.out.println("Scroll-content");
        System.out.println(selector.getText());
        selector.findElements(By.tagName("5 порций"));
        selector.submit();
       // selector.deselectByIndex(5);


       /* WebElement mySelectElm = driver.findElement(By.id("NormalizedPortionsCount"));
//        WebElement select = (driver.findElement(By.cssSelector(".selectBox-label")));
//        select.click();
        List<WebElement> allOptions = mySelectElm.findElements(By.tagName("option"));
        Select mySelect= new Select(mySelectElm);



        for (WebElement option : allOptions) {
            if (option.equals("4 порции")) {
                System.out.println(option.getText());
                mySelect.selectByValue("4 порции");
            }
            //   System.out.println(String.format("Значение: %s", allOptions));
            // option.click();
        }
        WebElement option = mySelect.getFirstSelectedOption();
        System.out.println(option.getText());*/

        /*Select dropdown =new Select(driver.findElement(By.xpath("//select")));
        System.out.println(dropdown.getAllSelectedOptions());
        dropdown.selectByIndex(3);*/

        // Находим элемент по атрибуту name
//        Select select = new Select(driver.findElement(By.xpath("//select")));
        //WebElement element = driver.findElement(By.xpath("//div[@class='main']"));
      /*  WebElement select = driver.findElement(By.xpath("//select"));
        WebElement options = select.findElement(By.tagName("option"));
        System.out.println(options.findElement(By.tagName("value=4")));
        options.findElement(By.tagName("value=4")).click();*/
        /*for (int i=0; i< allOptions.size();i++) {
            findElements(By.tagName("option"));
           // System.out.println(String.format("Значение: %s", allOptions));
            if (allOptions.get(i).isSelected()){
                System.out.println(i);
            }
           // option.click();
        }*/

    /*    WebElement select = driver.findElement(By.xpath("//select"));
        List<WebElement> allOptions = select.findElements(By.tagName("option"));

        for (WebElement option : allOptions) {

            System.out.println(String.format("Значение: %s", allOptions));
            option.click();
        }*/
        //element.click();

        // Вводим текст
  /*      element.sendKeys("Selenium");

        // Отправляем форму, при этом дравер сам определит как отправить форму по элементу
        element.submit();

        // Проверяем тайтл страницы
        System.out.println("Page title is: " + driver.getTitle());

        // Страницы гугл динамически отрисовывается с помощью javascript
        // Ждем загрузки страницы с таймаутом в 10 секунд
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("selenium");
            }
        });

        // Ожидаем увидеть: "Selenium - Google Search"
        System.out.println("Page title is: " + driver.getTitle());*/

        // Закрываем браузер
        //driver.quit();
    }
}

