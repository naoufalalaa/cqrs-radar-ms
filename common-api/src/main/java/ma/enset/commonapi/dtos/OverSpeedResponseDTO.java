package ma.enset.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@NoArgsConstructor @AllArgsConstructor
public class OverSpeedResponseDTO {
    private String overSpeedId;
    private Date date;
    private String matricule;
    private double vehicleSpeed;
    private RadarResponseDTO radar;
}
