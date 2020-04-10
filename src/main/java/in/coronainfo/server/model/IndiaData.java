package in.coronainfo.server.model;

import com.googlecode.objectify.annotation.Entity;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class IndiaData {
    @NonNull
    private IndiaCases indiaCases;

    @NonNull
    private StateWiseCases stateWiseCases;
}
