package ma.enset.commonapi.events;

import lombok.Getter;

import java.util.Date;

public class InfractionCreatedEvent extends BaseEvent<String> {

    @Getter private String matricule;
    @Getter private double vehicleSpeed;
    @Getter private Date date;
    @Getter private String radarId;
    @Getter private double maxSpeedAllowed;
    @Getter private double amende;
    public InfractionCreatedEvent(String id, String matricule, double vehicleSpeed, Date date, String radarId, double maxSpeedAllowed, double amende) {
        super(id);
        this.matricule = matricule;
        this.vehicleSpeed = vehicleSpeed;
        this.date = date;
        this.radarId = radarId;
        this.maxSpeedAllowed = maxSpeedAllowed;
        this.amende = amende;
    }
}
