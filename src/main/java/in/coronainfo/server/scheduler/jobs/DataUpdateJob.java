package in.coronainfo.server.scheduler.jobs;

import in.coronainfo.server.htmlparser.WomParser;
import in.coronainfo.server.model.GlobalCases;
import in.coronainfo.server.services.GlobalCasesService;
import in.coronainfo.server.services.SummaryFileGeneratorService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalTime;

@Log4j2
@RequiredArgsConstructor
public class DataUpdateJob {
    @NonNull
    private WomParser womParser;

    @NonNull
    private GlobalCasesService globalCasesService;

    @NonNull
    private SummaryFileGeneratorService summaryFileGeneratorService;

    public boolean updateGlobalCases() {
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = null;

        log.info("Going to run updateGlobalCases job");

        // parse and get latest data
        log.info("Going to get data from WoM website");
        GlobalCases globalCases = womParser.getGlobalCasesData();

        if (globalCases == null) {
            log.error("Failed to get global cases data");
            return false;
        }
        log.info("Fetched data from WoM website. globalCases:{}", globalCases);


        log.info("Going to update data in database");
        // update data in database
        globalCasesService.addGlobalCases(globalCases);

        log.info("Going to generate global cases summary file");
        // generate summary global cases summary file
        summaryFileGeneratorService.generateGlobalCasesSummaryFile();

        endTime = LocalTime.now();

        log.info("Finished running updateGlobalCases job. startTime:{}, endTime:{}", startTime, endTime);

        return true;
    }

}
