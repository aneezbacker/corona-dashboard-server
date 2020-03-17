package in.coronainfo.server.services;

import in.coronainfo.server.model.GlobalCases;
import in.coronainfo.server.repository.GlobalCasesRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GlobalCasesService {

    @NonNull
    private GlobalCasesRepository globalCasesRepository;

    public GlobalCases getByDate(String date) {
        return globalCasesRepository.getByDate(date);
    }

    public void addGlobalCases(GlobalCases globalCases) {
        globalCasesRepository.addGlobalCases(globalCases);
    }

    public List<GlobalCases> getAllGlobalCase() {
        List<GlobalCases> globalCasesList = globalCasesRepository.getAllGlobalCases();
        return globalCasesList;
    }
}