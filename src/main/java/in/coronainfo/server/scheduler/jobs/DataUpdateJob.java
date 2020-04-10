package in.coronainfo.server.scheduler.jobs;

import in.coronainfo.server.htmlparser.MohfwParser;
import in.coronainfo.server.htmlparser.WomParser;
import in.coronainfo.server.model.GlobalCases;
import in.coronainfo.server.model.IndiaCases;
import in.coronainfo.server.model.IndiaData;
import in.coronainfo.server.model.StateWiseCases;
import in.coronainfo.server.services.GlobalCasesService;
import in.coronainfo.server.services.IndiaCasesService;
import in.coronainfo.server.services.StateWiseCasesService;
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
    private MohfwParser mohfwParser;

    @NonNull
    private GlobalCasesService globalCasesService;

    @NonNull
    private IndiaCasesService indiaCasesService;

    @NonNull
    private StateWiseCasesService stateWiseCasesService;

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

    public boolean updateIndiaData() {
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = null;

        IndiaCases indiaCases = null;
        StateWiseCases stateWiseCases = null;

        try {
            log.info("Going to run updateIndiaData job");

            // parse and get latest data
            log.info("Going to get data from MohFW website");
            IndiaData indiaData = mohfwParser.getIndiaData();

            if (indiaData == null) {
                log.error("Failed to get india data");
                return false;
            }

            log.info("Fetched data from MohFW website. indiaData:{}", indiaData);

            indiaCases = indiaData.getIndiaCases();
            stateWiseCases = indiaData.getStateWiseCases();

            if (indiaCases != null) {
                log.info("Going to update India cases in database");
                indiaCasesService.addIndiaCases(indiaCases);

                log.info("Going to generate india cases summary file");
                summaryFileGeneratorService.generateIndiaCasesSummaryFile();
            } else {
                log.error("Failed to fetch India cases data. indiaCases:{}", indiaCases);
            }

            if (stateWiseCases != null) {
                log.info("Going to update state-wise cases in database");
                stateWiseCasesService.addStateWiseCases(stateWiseCases);

                log.info("Going to generate state cases summary file");
                summaryFileGeneratorService.generateStateWiseCasesSummaryFile();
            } else {
                log.error("Failed to fetch state-wise cases data. stateWiseCases:{}", stateWiseCases);
            }

            endTime = LocalTime.now();

            log.info("Finished running updateIndiaData job. startTime:{}, endTime:{}", startTime, endTime);

            return true;
        } catch (Exception e) {
            log.error("Exception occurred while auto-updating India data", e);
            e.printStackTrace();
        }
        return false;
    }

}
