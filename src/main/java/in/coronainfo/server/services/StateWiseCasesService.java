package in.coronainfo.server.services;

import in.coronainfo.server.model.*;
import in.coronainfo.server.repository.StateWiseCasesRepository;
import in.coronainfo.server.util.DateTimeUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class StateWiseCasesService {

    @NonNull
    private StateWiseCasesRepository stateWiseRepository;

    public StateWiseCases getByDate(String dateKey) {
        return stateWiseRepository.getByDate(dateKey);
    }

    public Map<String, StateWiseCases> getByDates(String... dateKeys) {
        return stateWiseRepository.getByDates(dateKeys);
    }

    public boolean addStateWiseCases(StateWiseCases stateWiseCases) {
        stateWiseRepository.addStateWiseCases(stateWiseCases);
        return true;
    }

    public List<StateWiseCases> getAllStateWiseCases() {
        List<StateWiseCases> stateWiseCasesList = stateWiseRepository.getAllStateWiseCases();
        return stateWiseCasesList;
    }

    // thread-safe
    public synchronized StateWiseCasesSummary getStateWiseCasesSummaryData() {
        StateWiseCasesSummary swcSummary = new StateWiseCasesSummary();

        // get date strings as per IST
        String today = DateTimeUtil.getTodayDateStr();
        String yesterday = DateTimeUtil.getYesterdayDateStr();

        log.info("Date being considered for StateWise Summary data - today:{}, yesterday:{}", today, yesterday);

        // get data from datastore
        Map<String, StateWiseCases> swCases = getByDates(yesterday, today);

        StateWiseCases swcToday = swCases.get(today);
        StateWiseCases swcYest = swCases.get(yesterday);

        if (swcToday == null) {
            log.error("No StateWise Cases data available for today. Will return null. today:{}", today);
            return null;
        }

        if (swcYest == null) {
            log.error("No StateWise Cases data available for yesterday. Will return 0 for delta fields. yesterday:{}",
                    yesterday);
        }

        Map<String, StateCasesSummary> scSummaryMap = new HashMap<>();
        Map<String, StateCases> scMapToday = swcToday.getStateCasesMap();
        Map<String, StateCases> scMapYest = swcYest != null ? swcYest.getStateCasesMap() : null;

        // set Today's data
        for (String state : scMapToday.keySet()) {
            StateCases scToday = scMapToday.get(state);

            StateCasesSummary scSummary = new StateCasesSummary();

            scSummary.setState(state);

            scSummary.setConfirmed(scToday.getConfirmed());
            scSummary.setDeaths(scToday.getDeaths());
            scSummary.setCured(scToday.getCured());
            scSummary.setActive(scToday.getConfirmed() - scToday.getCured() - scToday.getDeaths());

            StateCases scYesterday = scMapYest != null ? scMapYest.get(state) : null;
            if (scYesterday == null) {
                log.error("Missing States data for yesterday.  yesterday:{}. state:{}", yesterday, state);
                scSummary.setConfirmedDelta(0);
                scSummary.setDeathsDelta(0);
                scSummary.setCuredDelta(0);
                scSummary.setActiveDelta(0);
            } else {
                scSummary.setConfirmedDelta(scToday.getConfirmed() - scYesterday.getConfirmed());
                scSummary.setDeathsDelta(scToday.getDeaths() - scYesterday.getDeaths());
                scSummary.setCuredDelta(scToday.getCured() - scYesterday.getCured());
                scSummary.setActiveDelta(scSummary.getConfirmedDelta() - scSummary.getDeathsDelta() - scSummary.getCuredDelta());
            }
            scSummaryMap.put(state, scSummary);
        }

        swcSummary.setScSummaryMap(scSummaryMap);

        return swcSummary;
    }

}