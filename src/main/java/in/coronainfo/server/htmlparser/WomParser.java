package in.coronainfo.server.htmlparser;

import in.coronainfo.server.model.GlobalCases;
import in.coronainfo.server.util.DateTimeUtil;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@Log4j2
public class WomParser {
    private static final String GLOBAL_DATA_URL = "https://www.worldometers.info/coronavirus";

    public GlobalCases getGlobalCasesData() {
        GlobalCases globalCases = null;
        String date = DateTimeUtil.getTodayDateStr();
        int confirmed = 0;
        int deaths = 0;
        int cured = 0;

        try {
            Document doc = Jsoup.connect(GLOBAL_DATA_URL).get();
            Elements divElements = doc.getElementsByClass("maincounter-number");

            // confirmed cases
            Element confirmedDivElem = divElements.get(0);  // confirmed cases div
            Element confirmedSpanElem = confirmedDivElem.getElementsByTag("span").get(0);
            String confirmedStr = confirmedSpanElem.text();
            confirmed = Integer.parseInt(confirmedStr.replace(",", ""));

            // deaths cases
            Element deathsDivElem = divElements.get(1);  // deaths cases div
            Element deathsSpanElement = deathsDivElem.getElementsByTag("span").get(0);
            String deathsStr = deathsSpanElement.text();
            deaths = Integer.parseInt(deathsStr.replace(",", ""));

            // cured cases
            Element curedDivElem = divElements.get(2);  // cured cases div
            Element curedSpanElement = curedDivElem.getElementsByTag("span").get(0);
            String curedStr = curedSpanElement.text();
            cured = Integer.parseInt(curedStr.replace(",", ""));

            globalCases =
                    GlobalCases.builder().date(date).confirmed(confirmed).deaths(deaths).cured(cured).build();

        } catch (Exception e) {
            log.error("Exception occurred while getting Global Cases data. GLOBAL_DATA_URL:{}", GLOBAL_DATA_URL, e);
        }

        return globalCases;
    }


    public static void main(String[] args) {
        WomParser womParser = new WomParser();
        System.out.println(womParser.getGlobalCasesData());
    }
}
