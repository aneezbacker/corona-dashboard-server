package in.coronainfo.server.htmlparser;

import in.coronainfo.server.model.IndiaCases;
import in.coronainfo.server.model.IndiaData;
import in.coronainfo.server.model.StateCases;
import in.coronainfo.server.model.StateWiseCases;
import in.coronainfo.server.util.DateTimeUtil;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class MohfwParser {
    private static final String INDIA_DATA_URL = "https://www.mohfw.gov.in";

    public IndiaData getIndiaData() {
        IndiaData indiaData = null;
        IndiaCases indiaCases = null;
        StateWiseCases stateWiseCases = null;

        String date = DateTimeUtil.getTodayDateStr();

        WebDriver driver = null;
        try {
            driver = new HtmlUnitDriver();

            log.info("Going to load INDIA_DATA_URL:{}", INDIA_DATA_URL);

            driver.navigate().to(INDIA_DATA_URL);

            log.info("Loaded INDIA_DATA_URL:{}. Going to parse html", INDIA_DATA_URL);


            indiaCases = getIndiaCases(driver, date);

            stateWiseCases = getStateWiseCases(driver, date);

            indiaData = new IndiaData(indiaCases, stateWiseCases);

            log.info("Parsed html successfully. indiaData:{}", indiaData);
        } catch (Exception e) {
            log.error("Exception occurred while getting India data. INDIA_DATA_URL:{}, {}", INDIA_DATA_URL, e);
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }

        return indiaData;
    }

    private IndiaCases getIndiaCases(WebDriver driver, String date) {
        log.info("Going to parse India Cases");
        IndiaCases indiaCases = null;
        int confirmed = 0;
        int deaths = 0;
        int cured = 0;
        int active = 0;

        try {
            // active cases
            WebElement activeElem = driver.findElement(By.xpath("//div[@class='site-stats-count']/ul/li[1]/strong"));
            String activeStr = activeElem.getText();
            active = Integer.parseInt(activeStr.replace(",", ""));

            // cured cases
            WebElement curedElem = driver.findElement(By.xpath("//div[@class='site-stats-count']/ul/li[2]/strong"));
            String curedStr = curedElem.getText();
            cured = Integer.parseInt(curedStr.replace(",", ""));

            // deaths cases
            WebElement deathsElem = driver.findElement(By.xpath("//div[@class='site-stats-count']/ul/li[3]/strong"));
            String deathsStr = deathsElem.getText();
            deaths = Integer.parseInt(deathsStr.replace(",", ""));

            confirmed = active + cured + deaths;

            indiaCases =
                    IndiaCases.builder().date(date).confirmed(confirmed).deaths(deaths).cured(cured).build();

            log.info("Finished parsing India Cases. indiaCases:{}", indiaCases);

        } catch (Exception e) {
            log.error("Exception occurred while getting India cases data.", e);
            e.printStackTrace();
        }

        return indiaCases;
    }

    private StateWiseCases getStateWiseCases(WebDriver driver, String date) {
        log.info("Going to parse state-wise Cases");
        StateWiseCases stateWiseCases = null;

        Map<String, StateCases> stateCasesMap = new HashMap<>();

        try {
            // rows
            WebElement stateElem = driver.findElement(By.xpath("//section[@id='state-data']//tbody"));
            List<WebElement> rowElemList = stateElem.findElements(By.tagName("tr"));

            for (int i = 0; i < rowElemList.size() - 2; i++) {
                WebElement trElem = rowElemList.get(i);
                List<WebElement> columnElemList = trElem.findElements(By.tagName("td"));

                String state = columnElemList.get(1).getText().trim();
                String activeStr = columnElemList.get(2).getText();
                String curedStr = columnElemList.get(3).getText();
                String deathsStr = columnElemList.get(4).getText();

                int active = Integer.parseInt(activeStr.replace(",", ""));
                int cured = Integer.parseInt(curedStr.replace(",", ""));
                int deaths = Integer.parseInt(deathsStr.replace(",", ""));

                int confirmed = active + cured + deaths;

                StateCases stateCases = StateCases.builder().confirmed(confirmed).deaths(deaths).cured(cured).build();
                stateCasesMap.put(state, stateCases);
            }

            stateWiseCases = new StateWiseCases(date, stateCasesMap);

            log.info("Finished statewise cases. stateWiseCases:{}", stateWiseCases);
        } catch (Exception e) {
            log.error("Exception occurred while getting India cases data.", e);
            e.printStackTrace();
        }
        return stateWiseCases;
    }

    public static void main(String[] args) {
        MohfwParser mohfwParser = new MohfwParser();
        System.out.println(mohfwParser.getIndiaData());
    }
}
