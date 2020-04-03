package in.coronainfo.server.spring;

import in.coronainfo.server.htmlparser.WomParser;
import in.coronainfo.server.repository.GlobalCasesRepository;
import in.coronainfo.server.repository.IndiaCasesRepository;
import in.coronainfo.server.repository.SnackBarMessageRepository;
import in.coronainfo.server.repository.StateWiseCasesRepository;
import in.coronainfo.server.scheduler.jobs.DataUpdateJob;
import in.coronainfo.server.scheduler.jobs.JobScheduler;
import in.coronainfo.server.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FileUtilService fileUtilService() {
        return new FileUtilService();
    }

    @Bean
    public GlobalCasesRepository globalCasesRepository() {
        return new GlobalCasesRepository();
    }

    @Bean
    public GlobalCasesService globalCasesService(GlobalCasesRepository globalCasesRepository) {
        return new GlobalCasesService(globalCasesRepository);
    }

    @Bean
    public IndiaCasesRepository indiaCasesRepository() {
        return new IndiaCasesRepository();
    }

    @Bean
    public IndiaCasesService indiaCasesService(IndiaCasesRepository indiaCasesRepository) {
        return new IndiaCasesService(indiaCasesRepository);
    }

    @Bean
    public StateWiseCasesRepository stateWiseCasesRepository() {
        return new StateWiseCasesRepository();
    }

    @Bean
    public StateWiseCasesService stateWiseCasesService(StateWiseCasesRepository stateWiseCasesRepository) {
        return new StateWiseCasesService(stateWiseCasesRepository);
    }

    @Bean
    public SnackBarMessageRepository snackBarRepository() {
        return new SnackBarMessageRepository();
    }

    @Bean
    public SnackBarMessageService snackBarService(SnackBarMessageRepository snackBarMessageRepository) {
        return new SnackBarMessageService(snackBarMessageRepository);
    }

    @Bean
    public GoogleCloudStorageService gcsService() {
        return new GoogleCloudStorageService();
    }

    @Bean
    public SummaryFileGeneratorService summaryFileGeneratorService(GoogleCloudStorageService gcsService,
                                                                   FileUtilService fileUtilService,
                                                                   GlobalCasesService globalCasesService,
                                                                   IndiaCasesService indiaCasesService,
                                                                   StateWiseCasesService stateWiseCasesService,
                                                                   SnackBarMessageService snackBarService) {
        return new SummaryFileGeneratorService(gcsService, fileUtilService, globalCasesService, indiaCasesService,
                stateWiseCasesService, snackBarService);
    }

    @Bean
    public JobRunnerService jobRunnerService(SummaryFileGeneratorService summaryFileGeneratorService) {
        return new JobRunnerService(summaryFileGeneratorService);
    }

    @Bean
    public WomParser womParser() {
        return new WomParser();
    }

    @Bean
    public DataUpdateJob dataUpdateJob(WomParser womParser, GlobalCasesService globalCasesService,
                                       SummaryFileGeneratorService summaryFileGeneratorService) {
        return new DataUpdateJob(womParser, globalCasesService, summaryFileGeneratorService);
    }

    @Bean
    public JobScheduler jobScheduler(DataUpdateJob dataUpdateJob) {
        return new JobScheduler(dataUpdateJob);
    }

}