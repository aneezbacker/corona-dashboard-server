package in.coronainfo.server.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class ResponseStatus {
    @NonNull
    private int responseCode;

    @NonNull
    private String responseMessage;
}
