package in.coronainfo.server.scheduler.jobs;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;

@Log4j2
@RequiredArgsConstructor
public class JobScheduler {

    @NonNull
    private DataUpdateJob dataUpdateJob;

    // run every 15 mins with initial delay of 30s
    //@Scheduled(fixedDelay = 120000, initialDelay = 5000)
    @Scheduled(fixedDelay = 900000, initialDelay = 30000)
    public void indiaDataSchJob() {
        log.info("Going to run India data job");

        ObjectifyService.run(new VoidWork() {
            @Override
            public void vrun() {
                boolean result = dataUpdateJob.updateIndiaData();
                log.info("Completed running India data job. result:{}", result);
            }
        });
    }

    // run every 15 mins with initial delay of 1 min
    //@Scheduled(fixedDelay = 120000, initialDelay = 30000)
    @Scheduled(fixedDelay = 900000, initialDelay = 60000)
    public void globalCasesSchJob() {
        log.info("Going to run global cases job");

        ObjectifyService.run(new VoidWork() {
            @Override
            public void vrun() {
                boolean result = dataUpdateJob.updateGlobalCases();
                log.info("Completed running global cases job. result:{}", result);
            }
        });
    }


}

