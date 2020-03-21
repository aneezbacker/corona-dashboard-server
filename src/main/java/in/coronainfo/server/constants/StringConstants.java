package in.coronainfo.server.constants;

public interface StringConstants {
    public static final String TIMEZONE_KOLKATA = "Asia/Kolkata";

    public interface FILE_NAME {
        public static final String GLOBAL_CASES_SUMMARY = "globalCasesSummary.json";
        public static final String INDIA_CASES_SUMMARY = "indiaCasesSummary.json";
    }

    public interface CDN_FILE_NAME {
        public static final String GLOBAL_CASES_SUMMARY = "summaryData/globalCasesSummary.json";
        public static final String INDIA_CASES_SUMMARY = "summaryData/indiaCasesSummary.json";
    }

    public interface JOB_NAME {
        public static final String SUMMARY_GLOBAL_CASES = "summaryGlobalCases";
        public static final String SUMMARY_INDIA_CASES = "summaryIndiaCases";
    }


}
