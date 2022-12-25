package ma.enset.infractionservice.commands.repositories;

import ma.enset.infractionservice.commands.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, String> {
}
