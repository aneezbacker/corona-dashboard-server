package in.coronainfo.server.htmlparser;

import in.coronainfo.server.model.GlobalCases;
import in.coronainfo.server.util.DateTimeUtil;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Log4j2
public class WomParser {
    private static final String GLOBAL_DATA_URL = "https://www.worldometers.info/coronavirus";

    public GlobalCases getGlobalCasesData() {
        GlobalCases globalCases = null;
        String date = DateTimeUtil.getTodayDateStr();
        int confirmed = 0;
        int deaths = 0;
        int cured = 0;

        WebDriver driver = null;
        try {
            driver = new HtmlUnitDriver();
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.MINUTES);

            log.info("Going to get data from WoM. GLOBAL_DATA_URL:{}", GLOBAL_DATA_URL);
            driver.get(GLOBAL_DATA_URL);
            log.info("Loaded GLOBAL_DATA_URL:{}. Going to parse html", GLOBAL_DATA_URL);

            List<WebElement> elementList = driver.findElements(By.className("maincounter-number"));

            // confirmed cases
            String confirmedStr = elementList.get(0).findElement(By.tagName("span")).getText();
            confirmed = Integer.parseInt(confirmedStr.replace(",", ""));

            // deaths cases
            String deathsStr = elementList.get(1).findElement(By.tagName("span")).getText();
            deaths = Integer.parseInt(deathsStr.replace(",", ""));

            // cured cases
            String curedStr = elementList.get(2).findElement(By.tagName("span")).getText();
            cured = Integer.parseInt(curedStr.replace(",", ""));

            globalCases =
                    GlobalCases.builder().date(date).confirmed(confirmed).deaths(deaths).cured(cured).build();

            log.info("Parsed html successfully. globalCases:{}", globalCases);

        } catch (Exception e) {
            log.error("Exception occurred while getting Global Cases data. GLOBAL_DATA_URL:{}, {}", GLOBAL_DATA_URL, e);
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }

        return globalCases;
    }


    public static void main(String[] args) {
        WomParser womParser = new WomParser();
        System.out.println(womParser.getGlobalCasesData());
    }
}
