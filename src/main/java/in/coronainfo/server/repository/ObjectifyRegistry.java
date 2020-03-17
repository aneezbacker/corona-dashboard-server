package in.coronainfo.server.repository;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import in.coronainfo.server.model.GlobalCases;

public class ObjectifyRegistry {

    // register all entities here
    static {
        ObjectifyService.register(GlobalCases.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }
}