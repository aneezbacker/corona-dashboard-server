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
    int confirmedIndia;
    int confirmedForeign;
    int confirmedTotal;

    int curedIndia;
    int curedForeign;
    int curedTotal;

    int deathsIndia;
    int deathsForeign;
    int deathsTotal;

    int activeIndia;
    int activeForeign;
    int activeTotal;

    // change from previous day
    int confirmedIndiaDelta;
    int confirmedForeignDelta;
    int confirmedTotalDelta;

    int curedIndiaDelta;
    int curedForeignDelta;
    int curedTotalDelta;

    int deathsIndiaDelta;
    int deathsForeignDelta;
    int deathsTotalDelta;

    int activeIndiaDelta;
    int activeForeignDelta;
    int activeTotalDelta;
}