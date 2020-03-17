package in.coronainfo.server.web.controller;

import in.coronainfo.server.model.GlobalCases;
import in.coronainfo.server.services.GlobalCasesService;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;


@Log4j2
@Data
@RestController
public class AppController {

    @NonNull
    private GlobalCasesService globalCasesService;

    @GetMapping("/global-cases/get")
    public GlobalCases getGlobalCases(@RequestParam String date) {
        return globalCasesService.getByDate(date);
    }

    @PostMapping("/global-cases/add")
    public void addGlobalCases(@RequestBody GlobalCases globalCases) {
        log.info("Adding global cases. globalCases:{}", globalCases);
        globalCasesService.addGlobalCases(globalCases);
        log.info("Successfully added global cases. globalCases:{}", globalCases);
    }
}