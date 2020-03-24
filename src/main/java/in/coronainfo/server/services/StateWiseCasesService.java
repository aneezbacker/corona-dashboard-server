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
        Map<String, StateCases> scMapYest = swcYest.getStateCasesMap();

        // set Today's data
        for (String state : scMapToday.keySet()) {
            StateCases scToday = scMapToday.get(state);

            StateCasesSummary scSummary = new StateCasesSummary();

            scSummary.setState(state);

            scSummary.setConfirmedIndia(scToday.getConfirmedIndia());
            scSummary.setConfirmedForeign(scToday.getConfirmedForeign());
            scSummary.setConfirmedTotal(scToday.getConfirmedIndia() + scToday.getConfirmedForeign());

            scSummary.setDeathsIndia(scToday.getDeathsIndia());
            scSummary.setDeathsForeign(scToday.getDeathsForeign());
            scSummary.setDeathsTotal(scToday.getDeathsIndia() + scToday.getDeathsForeign());

            scSummary.setCuredIndia(scToday.getCuredIndia());
            scSummary.setCuredForeign(scToday.getCuredForeign());
            scSummary.setCuredTotal(scToday.getCuredIndia() + scToday.getCuredForeign());

            scSummary.setActiveIndia(scToday.getConfirmedIndia() - scToday.getCuredIndia() - scToday.getDeathsIndia());
            scSummary.setActiveForeign(scToday.getConfirmedForeign() - scToday.getCuredForeign() - scToday.getDeathsForeign());
            scSummary.setActiveTotal(scSummary.getActiveIndia() + scSummary.getActiveForeign());

            StateCases scYesterday = scMapYest.get(state);
            if (scYesterday == null) {
                log.error("Missing States data for yesterday.  yesterday:{}. state:{}", yesterday, state);
                continue;
            }

            scSummary.setConfirmedIndiaDelta(scToday.getConfirmedIndia() - scYesterday.getConfirmedIndia());
            scSummary.setConfirmedForeignDelta(scToday.getConfirmedForeign() - scYesterday.getConfirmedForeign());
            scSummary.setConfirmedTotalDelta(scSummary.getConfirmedIndiaDelta() + scSummary.getConfirmedForeignDelta());

            scSummary.setDeathsIndiaDelta(scToday.getDeathsIndia() - scYesterday.getDeathsIndia());
            scSummary.setDeathsForeignDelta(scToday.getDeathsForeign() - scYesterday.getDeathsForeign());
            scSummary.setDeathsTotalDelta(scSummary.getDeathsIndiaDelta() + scSummary.getDeathsForeignDelta());

            scSummary.setCuredIndiaDelta(scToday.getCuredIndia() - scYesterday.getCuredIndia());
            scSummary.setCuredForeignDelta(scToday.getCuredForeign() - scYesterday.getCuredForeign());
            scSummary.setCuredTotalDelta(scSummary.getCuredIndiaDelta() + scSummary.getCuredForeignDelta());

            scSummary.setActiveIndiaDelta(scSummary.getConfirmedIndiaDelta() - scSummary.getDeathsIndiaDelta() - scSummary.getCuredIndiaDelta());
            scSummary.setActiveForeignDelta(scSummary.getConfirmedForeignDelta() - scSummary.getDeathsForeignDelta() - scSummary.getCuredForeignDelta());
            scSummary.setActiveTotalDelta(scSummary.getActiveIndiaDelta() + scSummary.getActiveForeignDelta());

            scSummaryMap.put(state, scSummary);
        }

        swcSummary.setScSummaryMap(scSummaryMap);

        return swcSummary;
    }

}