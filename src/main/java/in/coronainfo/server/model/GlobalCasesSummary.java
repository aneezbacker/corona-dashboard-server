package in.coronainfo.server.model;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class GlobalCasesSummary {
    // today's data
    int confirmed;
    int deaths;
    int cured;
    int active;

    // change from previous day
    int confirmedDelta;
    int deathsDelta;
    int curedDelta;
    int activeDelta;
}
