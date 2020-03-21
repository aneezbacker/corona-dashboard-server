package in.coronainfo.server.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.KeyFactory;
import in.coronainfo.server.constants.EntityKind;
import in.coronainfo.server.model.IndiaCases;

import java.util.List;
import java.util.Map;

public class IndiaCasesRepository {

    // get default instance of datastore using Application Default Credentials.
    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    private final KeyFactory keyFactory = datastore.newKeyFactory().setKind(EntityKind.INDIA_CASES.name());

    public void addIndiaCases(IndiaCases indiaCases) {
        ObjectifyRegistry.ofy().save().entity(indiaCases).now();
    }

    public IndiaCases getByDate(String date) {
        return ObjectifyRegistry.ofy().load().type(IndiaCases.class).id(date).now();
    }

    public Map<String, IndiaCases> getByDates(String... dates) {
        return ObjectifyRegistry.ofy().load().type(IndiaCases.class).ids(dates);
    }

    public List<IndiaCases> getAllIndiaCases() {
        return ObjectifyRegistry.ofy().load().type(IndiaCases.class).list();
    }
}