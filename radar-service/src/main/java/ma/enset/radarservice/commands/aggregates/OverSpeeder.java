package ma.enset.radarservice.commands.aggregates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.modelling.command.EntityId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class OverSpeeder {
    @EntityId
    private String id;
    private String matricule;
    private double vehicleSpeed;
}
