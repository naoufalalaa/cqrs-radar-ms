package ma.enset.immatriculationservice.commands.aggregates;

import ma.enset.commonapi.commands.CreateOwnerCommand;
import ma.enset.commonapi.events.OwnerCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
public class OwnerAggregate {
    @AggregateIdentifier
    private String id;
    private String name;
    private Date dateOfBirth;
    private String email;

    public OwnerAggregate() {
        // Required by Axon
    }

    @CommandHandler
    public OwnerAggregate(CreateOwnerCommand command) {
        if (command.getName() == null || command.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        AggregateLifecycle.apply(new OwnerCreatedEvent(
                command.getId(),
                command.getName(),
                command.getDateOfBirth(),
                command.getEmail()));
    }

    @EventSourcingHandler
    public void on(OwnerCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.dateOfBirth = event.getDateOfBirth();
        this.email = event.getEmail();
    }
}
