package ma.enset.immatriculationservice.query.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Owner {
    @Id
    private String id;
    private String name;
    private Date dateOfBirth;
    private String email;
    @OneToMany(mappedBy = "owner")
    private List<Vehicule> vehicules;

}
