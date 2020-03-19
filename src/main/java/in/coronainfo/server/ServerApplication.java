package in.coronainfo.server;

import com.googlecode.objectify.ObjectifyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    @Value("${TARGET:World}")
    String target;

    public static void main(String[] args) {
        // init Objectify
        ObjectifyService.init();

        // start the spring boot app
        SpringApplication.run(ServerApplication.class, args);
    }
}
