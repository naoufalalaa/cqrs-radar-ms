package ma.enset.infractionservice.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.commonapi.dtos.InfractionResponseDTO;
import ma.enset.commonapi.events.InfractionCreatedEvent;
import ma.enset.commonapi.events.RadarCatchSpeedEvent;
import ma.enset.commonapi.queries.GetInfractionsByVehicle;
import ma.enset.infractionservice.query.entities.Infraction;
import ma.enset.infractionservice.query.repositories.InfractionRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class InfractionServiceHandler {
    private InfractionRepository infractionRepository;

    @EventHandler
    public void on(InfractionCreatedEvent event) {
        log.info("InfractionCreatedEvent: {}");
        if(event.getMaxSpeedAllowed()< event.getVehicleSpeed()){
            Infraction infraction = new Infraction();
            infraction.setId(event.getId());
            infraction.setMatricule(event.getMatricule());
            infraction.setDateInfraction(event.getDate());
            infraction.setVehicleSpeed(event.getVehicleSpeed());
            infraction.setMaxSpeedAllowed(event.getMaxSpeedAllowed());
            infraction.setAmande(300+300*(event.getVehicleSpeed() - event.getMaxSpeedAllowed())/event.getMaxSpeedAllowed());
            infractionRepository.save(infraction);
        }
    }
    @EventHandler
    public void on (RadarCatchSpeedEvent event){
        log.info("RadarCatchSpeedEvent: {}");
        if(event.getRadarSpeed()< event.getVehiculeSpeed()){
            Infraction infraction = new Infraction();
            infraction.setId(event.getId());
            infraction.setMatricule(event.getMatricule());
            infraction.setDateInfraction(event.getDate());
            infraction.setVehicleSpeed(event.getVehiculeSpeed());
            infraction.setMaxSpeedAllowed(event.getRadarSpeed());
            infraction.setAmande(300+300*(event.getVehiculeSpeed() - event.getRadarSpeed())/event.getRadarSpeed());
            infractionRepository.save(infraction);
        }
    }
    @QueryHandler
    public List<InfractionResponseDTO> on(GetInfractionsByVehicle query) {
        List<Infraction> infractions = infractionRepository.findByMatriculeEquals(query.getId());
        List<InfractionResponseDTO> infractionResponseDTOS = new ArrayList<>();
        for (Infraction infraction: infractions) {
            InfractionResponseDTO infractionResponseDTO = new InfractionResponseDTO();
            infractionResponseDTO.setId(infraction.getId());
            infractionResponseDTO.setMatricule(infraction.getMatricule());
            infractionResponseDTO.setDateInfraction(infraction.getDateInfraction());
            infractionResponseDTO.setVehicleSpeed(infraction.getVehicleSpeed());
            infractionResponseDTO.setMaxSpeedAllowed(infraction.getMaxSpeedAllowed());
            infractionResponseDTO.setAmande(infraction.getAmande());
            infractionResponseDTOS.add(infractionResponseDTO);
        }
        return infractionResponseDTOS;
    }
}
