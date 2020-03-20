package in.coronainfo.server.services;

import com.google.gson.Gson;
import in.coronainfo.server.constants.StringConstants;
import in.coronainfo.server.model.GlobalCasesSummary;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class SummaryFileGeneratorService {

    @NonNull
    private FileUtilService fileUtilService;

    @NonNull
    private GlobalCasesService globalCasesService;

    @NonNull
    private GoogleCloudStorageService gcsService;

    private Gson gson = new Gson();

    public void generateGlobalCasesSummaryFile() {
        log.info("Going to create Global Cases summary file.");

        String filePath = StringConstants.FILE_NAME.GLOBAL_CASES_SUMMARY;

        log.info("Going to create Global Cases summary file.");

        try {
            // get data from GlobalCasesService
            log.info("Going to create Global Cases summary data.");
            GlobalCasesSummary gcSummary = globalCasesService.getGlobalCasesSummaryData();
            log.info("Created Global Cases summary data. gcSummary:{}", gcSummary);

            // create json file
            log.info("Going to create Global Cases summary json file.");
            fileUtilService.createJsonFile(GlobalCasesSummary.class, gcSummary, filePath);
            log.info("Finished creating Global Cases summary json file. filePath:{}", filePath);

            // upload file to Google Cloud Storage bucket
            log.info("Upload file to Global Cases summary json file to GCS bucket");
            gcsService.uploadFile(filePath, StringConstants.CDN_FILE_NAME.GLOBAL_CASES_SUMMARY);
            log.info("Successfully uploaded Global Cases summary json file to GCS bucket");

            log.info("Finished creating Global Cases summary file.");
        } catch (Exception e) {
            log.error("Exception occurred while creating Global Cases Summary file.", e);
        }
    }

}
