package ma.enset.infractionservice.query.repositories;

import ma.enset.infractionservice.query.entities.Infraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfractionRepository extends JpaRepository<Infraction,String> {
    public List<Infraction> findByMatriculeEquals(String matricule);
}
