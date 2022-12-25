package ma.enset.infractionservice.commands.aggregates;

import ma.enset.commonapi.commands.CreateInfractionCommand;
import ma.enset.commonapi.events.InfractionCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
public class InfractionAggregate {
    @AggregateIdentifier
    private String id;
    private String matricule;
    private double vehicleSpeed;
    private Date date;
    private String radarId;
    private double maxSpeedAllowed;
    private double amende;

    public InfractionAggregate() {
    }

    @CommandHandler
    public InfractionAggregate(CreateInfractionCommand command) {
        if (command.getMatricule() == null || command.getMatricule().isEmpty()) {
            throw new IllegalArgumentException("Matricule cannot be empty");
        }
        AggregateLifecycle.apply(new InfractionCreatedEvent(
                command.getId(),
                command.getMatricule(),
                command.getVehicleSpeed(),
                command.getDate(),
                command.getRadarId(),
                command.getMaxSpeedAllowed(),
                command.getAmende()));
    }
    @EventSourcingHandler
    public void on(InfractionCreatedEvent event) {
        this.id = event.getId();
        this.matricule = event.getMatricule();
        this.vehicleSpeed = event.getVehicleSpeed();
        this.date = event.getDate();
        this.radarId = event.getRadarId();
        this.maxSpeedAllowed = event.getMaxSpeedAllowed();
        this.amende = event.getAmende();
    }

}