package in.coronainfo.server.model;


import com.googlecode.objectify.annotation.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class StateWiseCasesSummary {

    Map<String, StateCasesSummary> scSummaryMap;
}
