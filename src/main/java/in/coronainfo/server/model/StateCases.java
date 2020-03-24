package in.coronainfo.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StateCases {
    int confirmedIndia;
    int confirmedForeign;
    int curedIndia;
    int curedForeign;
    int deathsIndia;
    int deathsForeign;
}