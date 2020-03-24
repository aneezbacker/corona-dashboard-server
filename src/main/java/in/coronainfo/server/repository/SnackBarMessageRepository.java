package in.coronainfo.server.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.KeyFactory;
import in.coronainfo.server.constants.EntityKind;
import in.coronainfo.server.model.SnackBarMessage;

import java.util.List;
import java.util.Map;

public class SnackBarMessageRepository {

    // get default instance of datastore using Application Default Credentials.
    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    private final KeyFactory keyFactory = datastore.newKeyFactory().setKind(EntityKind.SNACK_BAR_MESSAGES.name());

    public void addSnackBarMessage(SnackBarMessage snackBarMessage) {
        ObjectifyRegistry.ofy().save().entity(snackBarMessage).now();
    }

    public SnackBarMessage getById(String id) {
        return ObjectifyRegistry.ofy().load().type(SnackBarMessage.class).id(id).now();
    }

    public Map<String, SnackBarMessage> getByIds(String... ids) {
        return ObjectifyRegistry.ofy().load().type(SnackBarMessage.class).ids(ids);
    }

    public List<SnackBarMessage> getAllSnackBarMessages() {
        return ObjectifyRegistry.ofy().load().type(SnackBarMessage.class).list();
    }
}