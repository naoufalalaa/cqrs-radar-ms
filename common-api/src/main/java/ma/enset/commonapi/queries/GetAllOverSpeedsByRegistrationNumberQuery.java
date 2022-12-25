package ma.enset.commonapi.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllOverSpeedsByRegistrationNumberQuery {
    public String registrationNumber;
}
