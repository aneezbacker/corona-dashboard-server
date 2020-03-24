package in.coronainfo.server.constants;

public interface StringConstants {
    public static final String TIMEZONE_KOLKATA = "Asia/Kolkata";

    public interface FILE_NAME {
        public static final String GLOBAL_CASES_SUMMARY = "globalCasesSummary.json";
        public static final String INDIA_CASES_SUMMARY = "indiaCasesSummary.json";
        public static final String STATE_WISE_CASES_SUMMARY = "stateWiseCasesSummary.json";

        public static final String SNACK_BAR_MESSAGE = "snackBarMessage.json";
    }

    public interface CDN_FILE_NAME {
        public static final String GLOBAL_CASES_SUMMARY = "summaryData/globalCasesSummary.json";
        public static final String INDIA_CASES_SUMMARY = "summaryData/indiaCasesSummary.json";
        public static final String STATE_WISE_CASES_SUMMARY = "summaryData/stateWiseCasesSummary.json";

        public static final String SNACK_BAR_MESSAGE = "summaryData/snackBarMessage.json";
    }

    public interface JOB_NAME {
        public static final String SUMMARY_GLOBAL_CASES = "summaryGlobalCases";
        public static final String SUMMARY_INDIA_CASES = "summaryIndiaCases";
        public static final String SUMMARY_STATE_WISE_CASES = "summaryStateWiseCases";

        public static final String SNACK_BAR_MESSAGE = "snackBarMessage";
    }


}
