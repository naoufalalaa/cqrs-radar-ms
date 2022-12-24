package ma.enset.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassingVehiculeDTO {
    public String matricule;
    public double vehicleSpeed;
    public String radarId;
    public double radarSpeed;
}
