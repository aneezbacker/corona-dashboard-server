package in.coronainfo.server.model;


import lombok.NonNull;
import lombok.Value;

@Value
public class ResponseData<R> {
    @NonNull
    private R result;

    @NonNull
    private ResponseStatus responseStatus;
}
