package ma.enset.radarservice.query.repositories;

import ma.enset.radarservice.query.entities.OverSpeedDetection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OverSpeedDetectionRepository extends JpaRepository<OverSpeedDetection, String> {
    List<OverSpeedDetection> findByMatricule(String matricule);
    List<OverSpeedDetection> findByRadarId(String radarId);
}
