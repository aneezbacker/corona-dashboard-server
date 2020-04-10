package in.coronainfo.server.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import javax.swing.text.Element;

public class SeleniumTest {
    public static void main(String[] args) {
//       test();

        // Creating a new instance of the HTML unit driver
        WebDriver driver = new HtmlUnitDriver();
        try {


            // Navigate to Google
            driver.get("https://www.mohfw.gov.in");


            // Locate the searchbox using its name
            WebElement element = driver.findElement(By.className("site-stats-count"));
            WebElement ul = element.findElement(By.tagName("ul"));
            WebElement li = ul.findElement(By.tagName("li"));
            WebElement strong = li.findElement(By.tagName("span"));


            System.out.println(strong.getText());

            /*
            WebElement we1 = driver.findElement(By.xpath("//div[@class='site-stats-count']/ul/li[1]/strong"));
            String tx = we1.getText();
            System.out.println(tx);
            */
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }


    }


}