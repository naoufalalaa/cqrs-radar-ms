package ma.enset.immatriculationservice.commands.aggregates;

import ma.enset.commonapi.commands.CreateVehiculeCommand;
import ma.enset.commonapi.events.VehiculeCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class VehiculeAggregate {
    @AggregateIdentifier
    private String id;
    private String matricule;
    private String marque;
    private String modele;
    private int puissance;
    private String proprietaire;

    public VehiculeAggregate() {
        // Required by Axon
    }

    @CommandHandler
    public VehiculeAggregate(CreateVehiculeCommand command) {
        if (command.getMatricule() == null || command.getMatricule().isEmpty() || command.getMarque() == null || command.getMarque().isEmpty() || command.getModele() == null || command.getModele().isEmpty() || command.getPuissance() == 0 || command.getProprietaire() == null || command.getProprietaire().isEmpty()) {
            throw new IllegalArgumentException("No field can be empty");
        }
        AggregateLifecycle.apply(new VehiculeCreatedEvent(
                UUID.randomUUID().toString(),
                command.getMatricule(),
                command.getMarque(),
                command.getModele(),
                command.getPuissance(),
                command.getProprietaire()));
    }
    @EventSourcingHandler
    public void on(VehiculeCreatedEvent event) {
        this.id = event.getId();
        this.matricule = event.getMatricule();
        this.marque = event.getMarque();
        this.modele = event.getModele();
        this.puissance = event.getPuissance();
        this.proprietaire = event.getProprietaire();
    }
}
