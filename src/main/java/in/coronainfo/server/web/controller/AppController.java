package in.coronainfo.server.web.controller;

import in.coronainfo.server.model.GlobalCases;
import in.coronainfo.server.model.IndiaCases;
import in.coronainfo.server.model.StateWiseCases;
import in.coronainfo.server.services.GlobalCasesService;
import in.coronainfo.server.services.IndiaCasesService;
import in.coronainfo.server.services.JobRunnerService;
import in.coronainfo.server.services.StateWiseCasesService;
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

    @NonNull
    private IndiaCasesService indiaCasesService;

    @NonNull
    private StateWiseCasesService stateWiseCasesService;

    @NonNull
    private JobRunnerService jobRunnerService;


    // ---------- Global Cases STARTS ----------
    @GetMapping("/global-cases/get")
    public GlobalCases getGlobalCases(@RequestParam String date) {
        log.info("Fetching global cases. date:{}", date);
        return globalCasesService.getByDate(date);
    }

    @PostMapping("/global-cases/add")
    public boolean addGlobalCases(@RequestBody GlobalCases globalCases) {
        log.info("Adding global cases. globalCases:{}", globalCases);
        return globalCasesService.addGlobalCases(globalCases);
    }
    // ---------- Global Cases ENDS ----------

    // ---------- India Cases STARTS ----------
    @GetMapping("/india-cases/get")
    public IndiaCases getIndiaCases(@RequestParam String date) {
        log.info("Fetching india cases. date:{}", date);
        return indiaCasesService.getByDate(date);
    }

    @PostMapping("/india-cases/add")
    public boolean addIndiaCases(@RequestBody IndiaCases indiaCases) {
        log.info("Adding india cases. indiaCases:{}", indiaCases);
        return indiaCasesService.addIndiaCases(indiaCases);
    }
    // ---------- India Cases ENDS ----------


    // ---------- StateWise Cases STARTS ----------
    @GetMapping("/statewise-cases/get")
    public StateWiseCases getStateWiseCases(@RequestParam String date) {
        log.info("Fetching stateWise cases. date:{}", date);
        return stateWiseCasesService.getByDate(date);
    }

    @PostMapping("/statewise-cases/add")
    public boolean addStatewiseCases(@RequestBody StateWiseCases statewiseCases) {
        log.info("Adding statewise cases. statewiseCases:{}", statewiseCases);
        return stateWiseCasesService.addStateWiseCases(statewiseCases);
    }
    // ---------- StateWise Cases ENDS ----------

    // ---------- Jobs STARTS ----------
    @GetMapping("/jobs/run")
    public boolean runJobs(@RequestParam String jobName) {
        log.info("Going to run job. jobName:{}", jobName);
        return jobRunnerService.run(jobName);
    }
    // ---------- Jobs ENDS ----------

}