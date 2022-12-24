package ma.enset.infractionservice.query.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Infraction {
    @Id
    private String id;
    private String matricule;
    private double vehicleSpeed;
    private Date dateInfraction;
    private String radarId;
    private double maxSpeedAllowed;
    private double amande;
}
