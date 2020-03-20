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

    public void run(String jobName) {
        log.info("Going to execute job. jobName:{}", jobName);
        switch (jobName) {
            case StringConstants.JOB_NAME.SUMMARY_GLOBAL_CASES:
                summaryFileGeneratorService.generateGlobalCasesSummaryFile();
                break;
            default:
                log.error("Unknown job name:{}", jobName);
        }
        log.info("Finished executing job. jobName:{}", jobName);
    }


}