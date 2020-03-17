package in.coronainfo.server.constants;

import in.coronainfo.server.model.ResponseStatus;

public enum ResponseStatusValues {

    REQUEST_SUCCESSFUL {
        @Override
        public ResponseStatus getResponseStatus() {
            return new ResponseStatus(201, "Request successful");
        }
    },

    REQUEST_FAILED {
        @Override
        public ResponseStatus getResponseStatus() {
            return new ResponseStatus(401, "Request failed");
        }
    },

    STATE_ANALYTICS_NOT_FOUND {
        @Override
        public ResponseStatus getResponseStatus() {
            return new ResponseStatus(402, "State Analytics not found");
        }
    };;

    public abstract ResponseStatus getResponseStatus();
}