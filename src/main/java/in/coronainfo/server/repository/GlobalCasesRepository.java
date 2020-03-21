package in.coronainfo.server.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.KeyFactory;
import in.coronainfo.server.constants.EntityKind;
import in.coronainfo.server.model.GlobalCases;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

public class GlobalCasesRepository {

    // get default instance of datastore using Application Default Credentials.
    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    private final KeyFactory keyFactory = datastore.newKeyFactory().setKind(EntityKind.GLOBAL_CASES.name());

    public void addGlobalCases(GlobalCases globalCases) {
        ObjectifyRegistry.ofy().save().entity(globalCases).now();
    }

    public GlobalCases getByDate(String date) {
        return ObjectifyRegistry.ofy().load().type(GlobalCases.class).id(date).now();
    }

    public Map<String, GlobalCases> getByDates(String... dates) {
        return ObjectifyRegistry.ofy().load().type(GlobalCases.class).ids(dates);
    }

    public List<GlobalCases> getAllGlobalCases() {
        return ObjectifyRegistry.ofy().load().type(GlobalCases.class).list();
    }
}