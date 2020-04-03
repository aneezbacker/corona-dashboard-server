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

    // run every 15 mins with initial delay of 2 mins
    @Scheduled(fixedDelay = 900000, initialDelay = 120000)
    public void fixedDelaySch() {
        log.info("Going to run global cases job");

        ObjectifyService.run(new VoidWork() {
            @Override
            public void vrun() {
                boolean result = dataUpdateJob.updateGlobalCases();
                log.info("Completed running global cases job. result:{}");
            }
        });
    }

}
