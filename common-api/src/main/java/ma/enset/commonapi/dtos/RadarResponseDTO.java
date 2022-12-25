package ma.enset.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RadarResponseDTO {
    private String radarId;
    private double radarSpeed;
    private double longitude;
    private double latitude;
}
