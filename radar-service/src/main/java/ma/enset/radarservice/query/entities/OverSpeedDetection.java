package ma.enset.radarservice.query.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Entity
@NoArgsConstructor @AllArgsConstructor @Data
public class OverSpeedDetection {
    @Id
    private String overSpeedId;
    private Date date;
    private String matricule;
    private double vehicleSpeed;
    @ManyToOne
    private Radar radar;


}
