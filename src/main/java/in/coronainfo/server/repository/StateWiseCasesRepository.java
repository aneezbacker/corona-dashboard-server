package in.coronainfo.server.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.KeyFactory;
import in.coronainfo.server.constants.EntityKind;
import in.coronainfo.server.model.StateWiseCases;

import java.util.List;
import java.util.Map;

public class StateWiseCasesRepository {

    // get default instance of datastore using Application Default Credentials.
    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    private final KeyFactory keyFactory = datastore.newKeyFactory().setKind(EntityKind.STATE_WISE_CASES.name());

    public void addStateWiseCases(StateWiseCases stateWiseCases) {
        ObjectifyRegistry.ofy().save().entity(stateWiseCases).now();
    }

    public StateWiseCases getByDate(String date) {
        return ObjectifyRegistry.ofy().load().type(StateWiseCases.class).id(date).now();
    }

    public Map<String, StateWiseCases> getByDates(String... dates) {
        return ObjectifyRegistry.ofy().load().type(StateWiseCases.class).ids(dates);
    }

    public List<StateWiseCases> getAllStateWiseCases() {
        return ObjectifyRegistry.ofy().load().type(StateWiseCases.class).list();
    }
}