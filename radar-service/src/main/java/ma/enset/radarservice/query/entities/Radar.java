package ma.enset.radarservice.query.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Radar {
    @Id
    private String id;
    private double maxSpeed;
    private double latitude;
    private double longitude;
    @OneToMany(mappedBy = "radar")
    private List<OverSpeedDetection> overSpeedDetections;
}
