package in.coronainfo.server;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.ObjectifyService;
import in.coronainfo.server.repository.DataStoreEmulatorManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Log4j2
@EnableScheduling
@SpringBootApplication
public class CoronaDashboardApplication {

    public static void main(String[] args) {
        if (args.length != 0 && "dev".equals(args[0])) {
            log.info("This is DEV environment. Will use datastore simulator");
            DataStoreEmulatorManager.initDataStoreEmulator();
        } else {
            log.info("This is PROD environment. Will use GCS datastore");
            ObjectifyService.init(); // init Objectify
        }

        log.info("Starting springboot app");
        // start the spring boot app
        SpringApplication.run(CoronaDashboardApplication.class, args);

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        log.info("Database init");
        log.info("datastore.getOptions().getHost():{}", datastore.getOptions().getHost());
        log.info("datastore.getOptions().getProjectId():{}", datastore.getOptions().getProjectId());
        log.info("datastore.getOptions().getNamespace():{}", datastore.getOptions().getNamespace());
        log.info("datastore.getOptions().getApplicationName():{}", datastore.getOptions().getApplicationName());
    }
}
