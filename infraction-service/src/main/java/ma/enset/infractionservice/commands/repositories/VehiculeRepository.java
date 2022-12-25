package ma.enset.infractionservice.commands.repositories;

import ma.enset.infractionservice.commands.models.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, String> {
    List<Vehicule> findByProprietaireIdEquals(String id);
}
