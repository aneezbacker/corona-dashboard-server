package in.coronainfo.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StateCasesSummary {
    String state;

    // today's data
    int confirmed;
    int cured;
    int deaths;
    int active;

    // change from previous day
    int confirmedDelta;
    int curedDelta;
    int deathsDelta;
    int activeDelta;
}