package in.coronainfo.server.services;

import in.coronainfo.server.constants.StringConstants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class JobRunnerService {

    @NonNull
    private SummaryFileGeneratorService summaryFileGeneratorService;

    public boolean run(String jobName) {
        boolean result = false;
        log.info("Going to execute job. jobName:{}", jobName);
        switch (jobName) {
            case StringConstants.JOB_NAME.SUMMARY_GLOBAL_CASES:
                result = summaryFileGeneratorService.generateGlobalCasesSummaryFile();
                break;
            case StringConstants.JOB_NAME.SUMMARY_INDIA_CASES:
                result = summaryFileGeneratorService.generateIndiaCasesSummaryFile();
                break;
            case StringConstants.JOB_NAME.SUMMARY_STATE_WISE_CASES:
                result = summaryFileGeneratorService.generateStateWiseCasesSummaryFile();
                break;
            default:
                log.error("Unknown job name:{}", jobName);
        }
        log.info("Finished executing job. jobName:{}. result:{}", jobName, result);
        return result;
    }


}