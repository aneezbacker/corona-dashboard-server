package in.coronainfo.server.services;

import com.google.gson.Gson;
import in.coronainfo.server.constants.StringConstants;
import in.coronainfo.server.model.GlobalCasesSummary;
import in.coronainfo.server.model.IndiaCasesSummary;
import in.coronainfo.server.model.SnackBarMessage;
import in.coronainfo.server.model.StateWiseCasesSummary;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.security.auth.login.FailedLoginException;

@Log4j2
@RequiredArgsConstructor
public class SummaryFileGeneratorService {

    @NonNull
    private GoogleCloudStorageService gcsService;

    @NonNull
    private FileUtilService fileUtilService;

    @NonNull
    private GlobalCasesService globalCasesService;

    @NonNull
    private IndiaCasesService indiaCasesService;

    @NonNull
    private StateWiseCasesService stateWiseCasesService;

    @NonNull
    private SnackBarMessageService snackBarMessageService;


    private Gson gson = new Gson();

    public boolean generateGlobalCasesSummaryFile() {
        String filePath = StringConstants.FILE_NAME.GLOBAL_CASES_SUMMARY;
        boolean result = false;

        try {
            // get data from GlobalCasesService
            log.info("Going to create Global Cases summary data.");
            GlobalCasesSummary gcSummary = globalCasesService.getGlobalCasesSummaryData();
            log.info("Created Global Cases summary data. gcSummary:{}", gcSummary);

            if (gcSummary == null) {
                log.error("Failed to fetch Global Cases summary");
                return false;
            }

            // create json file
            log.info("Going to create Global Cases summary json file.");
            fileUtilService.createJsonFile(GlobalCasesSummary.class, gcSummary, filePath);
            log.info("Finished creating Global Cases summary json file. filePath:{}", filePath);

            // upload file to Google Cloud Storage bucket
            log.info("Upload Global Cases summary json file to GCS bucket");
            gcsService.uploadFile(filePath, StringConstants.CDN_FILE_NAME.GLOBAL_CASES_SUMMARY);
            log.info("Successfully uploaded Global Cases summary json file to GCS bucket");

            log.info("Finished creating Global Cases summary file.");
            result = true;
        } catch (Exception e) {
            log.error("Exception occurred while creating Global Cases Summary file.", e);
        }
        return result;
    }

    public boolean generateIndiaCasesSummaryFile() {
        String filePath = StringConstants.FILE_NAME.INDIA_CASES_SUMMARY;
        boolean result = false;

        try {
            // get data from IndiaCasesService
            log.info("Going to create India Cases summary data.");
            IndiaCasesSummary icSummary = indiaCasesService.getIndiaCasesSummaryData();
            log.info("Created India Cases summary data. icSummary:{}", icSummary);

            if (icSummary == null) {
                log.error("Failed to fetch India Cases summary");
                return false;
            }

            // create json file
            log.info("Going to create India Cases summary json file.");
            fileUtilService.createJsonFile(IndiaCasesSummary.class, icSummary, filePath);
            log.info("Finished creating India Cases summary json file. filePath:{}", filePath);

            // upload file to Google Cloud Storage bucket
            log.info("Upload India Cases summary json file to GCS bucket");
            gcsService.uploadFile(filePath, StringConstants.CDN_FILE_NAME.INDIA_CASES_SUMMARY);
            log.info("Successfully uploaded India Cases summary json file to GCS bucket");

            log.info("Finished creating India Cases summary file.");

            result = true;
        } catch (Exception e) {
            log.error("Exception occurred while creating India Cases Summary file.", e);
        }
        return result;
    }

    public boolean generateStateWiseCasesSummaryFile() {
        String filePath = StringConstants.FILE_NAME.STATE_WISE_CASES_SUMMARY;
        boolean result = false;

        try {
            // get data from StateWiseCasesService
            log.info("Going to create State Wise Cases summary data.");
            StateWiseCasesSummary swcSummary = stateWiseCasesService.getStateWiseCasesSummaryData();
            log.info("Created State Wise Cases summary data. swcSummary:{}", swcSummary);

            if (swcSummary == null) {
                log.error("Failed to fetch State Wise Cases summary");
                return false;
            }

            // create json file
            log.info("Going to create State Wise Cases summary json file.");
            fileUtilService.createJsonFile(StateWiseCasesSummary.class, swcSummary, filePath);
            log.info("Finished creating State Wise Cases summary json file. filePath:{}", filePath);

            // upload file to Google Cloud Storage bucket
            log.info("Upload State Wise Cases summary json file to GCS bucket");
            gcsService.uploadFile(filePath, StringConstants.CDN_FILE_NAME.STATE_WISE_CASES_SUMMARY);
            log.info("Successfully uploaded State Wise Cases summary json file to GCS bucket");

            log.info("Finished creating State Wise Cases summary file.");
            result = true;
        } catch (Exception e) {
            log.error("Exception occurred while creating State Wise Cases Summary file.", e);
        }
        return result;
    }

    public boolean generateSnackBarMessageFile() {
        String filePath = StringConstants.FILE_NAME.SNACK_BAR_MESSAGE;
        boolean result = false;

        try {
            // get data from SnackBarMessageService
            log.info("Going to create Snack Bar Message data.");
            SnackBarMessage snackBarMessage = snackBarMessageService.getById("1");
            log.info("Created Snack Bar Message data. snackBarMessage:{}", snackBarMessage);

            if (snackBarMessage == null) {
                log.error("Failed to fetch Snack Bar Message data");
                return false;
            }

            // create json file
            log.info("Going to create Snack Bar Message json file.");
            fileUtilService.createJsonFile(SnackBarMessage.class, snackBarMessage, filePath);
            log.info("Finished creating Snack Bar Message json file. filePath:{}", filePath);

            // upload file to Google Cloud Storage bucket
            log.info("Upload Snack Bar Message json file to GCS bucket");
            gcsService.uploadFile(filePath, StringConstants.CDN_FILE_NAME.SNACK_BAR_MESSAGE);
            log.info("Successfully uploaded Snack Bar Message json file to GCS bucket");

            log.info("Finished creating Snack Bar Message file.");
            result = true;
        } catch (Exception e) {
            log.error("Exception occurred while creating Snack Bar Message file.", e);
        }
        return result;
    }

}
