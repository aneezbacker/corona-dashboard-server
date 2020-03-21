package in.coronainfo.server.spring;

import in.coronainfo.server.repository.GlobalCasesRepository;
import in.coronainfo.server.repository.IndiaCasesRepository;
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
    public GoogleCloudStorageService gcsService() {
        return new GoogleCloudStorageService();
    }

    @Bean
    public SummaryFileGeneratorService summaryFileGeneratorService(GoogleCloudStorageService gcsService,
                                                                   FileUtilService fileUtilService,
                                                                   GlobalCasesService globalCasesService,
                                                                   IndiaCasesService indiaCasesService) {
        return new SummaryFileGeneratorService(gcsService, fileUtilService, globalCasesService, indiaCasesService);
    }

    @Bean
    public JobRunnerService jobRunnerService(SummaryFileGeneratorService summaryFileGeneratorService) {
        return new JobRunnerService(summaryFileGeneratorService);
    }
}