package in.coronainfo.server.repository;

import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class DataStoreEmulatorManager {
    public static void initDataStoreEmulator() {
        ObjectifyService.init(new ObjectifyFactory(
                DatastoreOptions.newBuilder()
                        .setHost("http://localhost:8484")
                        .setProjectId("corona-dashboard-270916")
                        .build()
                        .getService()
        ));
    }
}