package in.coronainfo.server.repository;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import in.coronainfo.server.model.GlobalCases;
import in.coronainfo.server.model.IndiaCases;
import in.coronainfo.server.model.SnackBarMessage;
import in.coronainfo.server.model.StateWiseCases;

public class ObjectifyRegistry {

    // register all entities here
    static {
        ObjectifyService.register(GlobalCases.class);
        ObjectifyService.register(IndiaCases.class);
        ObjectifyService.register(StateWiseCases.class);
        ObjectifyService.register(SnackBarMessage.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }
}