package in.coronainfo.server.services;

import in.coronainfo.server.model.IndiaCases;
import in.coronainfo.server.model.IndiaCasesSummary;
import in.coronainfo.server.repository.IndiaCasesRepository;
import in.coronainfo.server.util.DateTimeUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class IndiaCasesService {

    @NonNull
    private IndiaCasesRepository indiaCasesRepository;

    public IndiaCases getByDate(String dateKey) {
        return indiaCasesRepository.getByDate(dateKey);
    }

    public Map<String, IndiaCases> getByDates(String... dateKeys) {
        return indiaCasesRepository.getByDates(dateKeys);
    }

    public boolean addIndiaCases(IndiaCases indiaCases) {
        indiaCasesRepository.addIndiaCases(indiaCases);
        return true;
    }

    public List<IndiaCases> getAllIndiaCase() {
        List<IndiaCases> indiaCasesList = indiaCasesRepository.getAllIndiaCases();
        return indiaCasesList;
    }

    // thread-safe
    public synchronized IndiaCasesSummary getIndiaCasesSummaryData() {
        IndiaCasesSummary icSummary = null;

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

        log.info("Date being considered for India Summary data - today:{}, yesterday:{}", today, yesterday);

        // get data from datastore
        Map<String, IndiaCases> indiaCases = getByDates(yesterday, today);

        IndiaCases icToday = indiaCases.get(today);
        IndiaCases icYesterday = indiaCases.get(yesterday);

        if (icToday == null) {
            log.error("No India Cases data available for today. Will return null. today:{}", today);
            return null;
        }

        // get Today's data
        confirmed = icToday.getConfirmed();
        cured = icToday.getCured();
        deaths = icToday.getDeaths();
        active = confirmed - cured - deaths;

        if (icYesterday == null) {
            log.error("No India Cases data available for yesterday. Will return 0 for delta fields. yesterday:{}",
                    yesterday);
        } else {
            // compute delta values
            confirmedDelta = confirmed - icYesterday.getConfirmed();
            curedDelta = cured - icYesterday.getCured();
            deathsDelta = deaths - icYesterday.getDeaths();
            activeDelta = confirmedDelta - curedDelta - deathsDelta;
        }

        // build summary object
        icSummary = IndiaCasesSummary.builder()
                .confirmed(confirmed).confirmedDelta(confirmedDelta)
                .cured(cured).curedDelta(curedDelta)
                .deaths(deaths).deathsDelta(deathsDelta)
                .active(active).activeDelta(activeDelta).build();

        return icSummary;
    }
}