package in.coronainfo.server.spring;

import in.coronainfo.server.repository.GlobalCasesRepository;
import in.coronainfo.server.services.GlobalCasesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public GlobalCasesRepository globalCasesRepository() {
        return new GlobalCasesRepository();
    }

    @Bean
    public GlobalCasesService globalCasesService(GlobalCasesRepository globalCasesRepository) {
        return new GlobalCasesService(globalCasesRepository);
    }

}