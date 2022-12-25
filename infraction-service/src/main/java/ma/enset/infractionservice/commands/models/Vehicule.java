package ma.enset.infractionservice.commands.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Vehicule {
    @Id
    private String id;
    private String matricule;
    private String marque;
    private String modele;
    private int puissance;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Owner owner;
    private String proprietaireId;
}
