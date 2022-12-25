package ma.enset.radarservice.commands.aggregates;

import lombok.extern.slf4j.Slf4j;
import ma.enset.commonapi.commands.CreateRadarCommand;
import ma.enset.commonapi.commands.PassedVehiculeRadarCommand;
import ma.enset.commonapi.events.RadarCatchSpeedEvent;
import ma.enset.commonapi.events.RadarCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Aggregate
@Slf4j
public class RadarAggregate {
    @AggregateIdentifier
    private String radarId;
    private double maxSpeed;
    private double latitude;
    private double longitude;

    @AggregateMember
    private List<OverSpeeder> overSpeeders = new ArrayList<>();

    public RadarAggregate() {
        //Required by Axon
    }

    @CommandHandler
    public RadarAggregate(CreateRadarCommand command){
        if (command.maxSpeed < 0) {
            throw new IllegalArgumentException("Max Speed cannot be negative");
        }
        AggregateLifecycle.apply(new RadarCreatedEvent(
                command.getId(),
                command.getMaxSpeed(),
                command.getLatitude(),
                command.getLongitude()));
    }

    @EventSourcingHandler //change the state of the aggregate
    public void on(RadarCreatedEvent event){
        this.radarId = event.getId();
        this.maxSpeed = event.getMaxSpeed();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
    }

    @CommandHandler
    public void handle (PassedVehiculeRadarCommand command){
        if(command.getVehicleSpeed() > command.getRadarSpeed()){
            log.info("---New Vehicle OversSpeed Detected---");
            // publish event
            AggregateLifecycle.apply(new RadarCatchSpeedEvent(
                    UUID.randomUUID().toString(),
                    command.getMatricule(),
                    command.getVehicleSpeed(),
                    command.getRadarId(),
                    command.getRadarSpeed(),
                    UUID.randomUUID().toString()
            ));
        }
    }

    @EventSourcingHandler
    public void on(RadarCatchSpeedEvent event){
        log.info("RadarCatchSpeedEvent occured");
        this.radarId = event.getRadarId();
        // new over speeder
        OverSpeeder overSpeeder = new OverSpeeder(
                UUID.randomUUID().toString(),
                event.getMatricule(),
                event.getVehiculeSpeed()
        );
        // add to list
        this.overSpeeders.add(overSpeeder);
        log.info("New OverSpeeder added to list : " + overSpeeder.getMatricule());
    }

    @ExceptionHandler
    public void handle(IllegalArgumentException e){
        log.error("Exception occured : " + e.getMessage());
    }

}
