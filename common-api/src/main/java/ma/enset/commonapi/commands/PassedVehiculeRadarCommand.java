package ma.enset.commonapi.commands;

import lombok.Getter;

public class PassedVehiculeRadarCommand extends BaseCommand<String> {
    @Getter private String matricule;
    @Getter private double vehicleSpeed;
    @Getter private String radarId;
    @Getter private double radarSpeed;

    public PassedVehiculeRadarCommand(String id, String matricule, double vehicleSpeed, String radarId, double radarSpeed) {
        super(id);
        this.matricule = matricule;
        this.vehicleSpeed = vehicleSpeed;
        this.radarId = radarId;
        this.radarSpeed = radarSpeed;
    }
}
