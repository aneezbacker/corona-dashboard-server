package in.coronainfo.server.spring;

import in.coronainfo.server.repository.GlobalCasesRepository;
import in.coronainfo.server.repository.IndiaCasesRepository;
import in.coronainfo.server.repository.StateWiseCasesRepository;
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
    public GoogleCloudStorageService gcsService() {
        return new GoogleCloudStorageService();
    }

    @Bean
    public SummaryFileGeneratorService summaryFileGeneratorService(GoogleCloudStorageService gcsService,
                                                                   FileUtilService fileUtilService,
                                                                   GlobalCasesService globalCasesService,
                                                                   IndiaCasesService indiaCasesService,
                                                                   StateWiseCasesService stateWiseCasesService) {
        return new SummaryFileGeneratorService(gcsService, fileUtilService, globalCasesService, indiaCasesService,
                stateWiseCasesService);
    }

    @Bean
    public JobRunnerService jobRunnerService(SummaryFileGeneratorService summaryFileGeneratorService) {
        return new JobRunnerService(summaryFileGeneratorService);
    }
}