package in.coronainfo.server.services;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

import java.io.FileWriter;
import java.io.IOException;

@Log4j2
public class FileUtilService<T> {

    private Gson gson = new Gson();

    public void createJsonFile(Class<T> type, T t, String filePath) throws IOException {
        log.info("Going to create json file. type:{},  t:{}, filePath:{}", type, t, filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(t, type, writer);
            log.info("Successfully created json file. type:{},  t:{}, filePath:{}", type, t, filePath);
        }
    }

}
