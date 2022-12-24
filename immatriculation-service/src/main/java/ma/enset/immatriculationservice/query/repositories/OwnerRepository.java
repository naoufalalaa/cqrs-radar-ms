package ma.enset.immatriculationservice.query.repositories;

import ma.enset.immatriculationservice.query.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, String> {
}
