package in.coronainfo.server.services;

import com.google.gson.Gson;
import in.coronainfo.server.model.GlobalCases;
import in.coronainfo.server.model.GlobalCasesSummary;
import in.coronainfo.server.repository.GlobalCasesRepository;
import in.coronainfo.server.util.DateTimeUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class GlobalCasesService {

    @NonNull
    private GlobalCasesRepository globalCasesRepository;

    public GlobalCases getByDate(String dateKey) {
        return globalCasesRepository.getByDate(dateKey);
    }

    public Map<String, GlobalCases> getByDates(String... dateKeys) {
        return globalCasesRepository.getByDates(dateKeys);
    }

    public void addGlobalCases(GlobalCases globalCases) {
        globalCasesRepository.addGlobalCases(globalCases);
    }

    public List<GlobalCases> getAllGlobalCase() {
        List<GlobalCases> globalCasesList = globalCasesRepository.getAllGlobalCases();
        return globalCasesList;
    }

    // thread-safe
    public synchronized GlobalCasesSummary getGlobalCasesSummaryData() {
        GlobalCasesSummary gcSummary = null;

        int confirmed = 0;
        int deaths = 0;
        int cured = 0;
        int active = 0;

        // change from previous day
        int confirmedDelta = 0;
        int deathsDelta = 0;
        int curedDelta = 0;
        int activeDelta = 0;

        // get date strings as per IST
        String today = DateTimeUtil.getTodayDateStr();
        String yesterday = DateTimeUtil.getYesterdayDateStr();

        // get data from datastore
        Map<String, GlobalCases> globalCases = getByDates(yesterday, today);

        GlobalCases gcToday = globalCases.get(today);
        GlobalCases gcYesterday = globalCases.get(yesterday);

        if (gcToday == null) {
            log.error("No Global Cases data available for today. Will return null.");
            return null;
        }

        // get Today's data
        confirmed = gcToday.getConfirmed();
        cured = gcToday.getCured();
        deaths = gcToday.getDeaths();
        active = confirmed - cured - deaths;

        if (gcYesterday == null) {
            log.error("No Global Cases data available for yesterday. Will return 0 for delta fields.");
        } else {
            // compute delta values
            confirmedDelta = confirmed - gcYesterday.getConfirmed();
            curedDelta = cured - gcYesterday.getCured();
            deathsDelta = deaths - gcYesterday.getDeaths();
            activeDelta = confirmedDelta - curedDelta - deathsDelta;
        }

        // build summary object
        gcSummary = GlobalCasesSummary.builder()
                .confirmed(confirmed).confirmedDelta(confirmedDelta)
                .cured(cured).curedDelta(curedDelta)
                .deaths(deaths).deathsDelta(deathsDelta)
                .active(active).activeDelta(activeDelta).build();

        return gcSummary;
    }
}