package ma.enset.immatriculationservice.query.repositories;

import ma.enset.immatriculationservice.query.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, String> {
    List<Vehicule> findByProprietaireIdEquals(String id);
}
