package ma.enset.radarservice.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.commonapi.dtos.EventDataResponseDTO;
import ma.enset.commonapi.dtos.OverSpeedResponseDTO;
import ma.enset.commonapi.dtos.RadarResponseDTO;
import ma.enset.commonapi.events.RadarCatchSpeedEvent;
import ma.enset.commonapi.events.RadarCreatedEvent;
import ma.enset.commonapi.queries.FindAllRadars;
import ma.enset.commonapi.queries.GetAllOverSpeedsByRegistrationNumberQuery;
import ma.enset.commonapi.queries.GetAllOverSpeedsQuery;
import ma.enset.commonapi.queries.SubscribeToEventsQuery;
import ma.enset.radarservice.query.entities.OverSpeedDetection;
import ma.enset.radarservice.query.entities.Radar;
import ma.enset.radarservice.query.repositories.OverSpeedDetectionRepository;
import ma.enset.radarservice.query.repositories.RadarRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class RadarServiceHandler {
    private RadarRepository radarRepository;
    private OverSpeedDetectionRepository overSpeedDetectionRepository;
    private QueryUpdateEmitter queryUpdateEmitter;

    @EventHandler
    @Transactional
    public void on(RadarCreatedEvent event){
        Radar radar = new Radar();
        radar.setId(event.getId());
        radar.setLatitude(event.getLatitude());
        radar.setLongitude(event.getLongitude());
        radar.setMaxSpeed(event.getMaxSpeed());
        radarRepository.save(radar);
    }

    @QueryHandler
    public List<Radar> on(FindAllRadars query){
        return radarRepository.findAll();
    }

    @QueryHandler
    public List<OverSpeedResponseDTO> handler(GetAllOverSpeedsQuery query){
        List<OverSpeedDetection> overSpeedDetections = overSpeedDetectionRepository.findAll();
        List<OverSpeedResponseDTO> overSpeedResponseDTOS = overSpeedDetections.stream()
                .map(os -> {
                    OverSpeedResponseDTO overSpeedResponseDTO = new OverSpeedResponseDTO();
                    overSpeedResponseDTO.setOverSpeedId(os.getOverSpeedId());
                    overSpeedResponseDTO.setVehicleSpeed(os.getVehicleSpeed());
                    overSpeedResponseDTO.setDate(os.getDate());
                    overSpeedResponseDTO.setMatricule(os.getMatricule());
                    RadarResponseDTO radarResponseDTO = new RadarResponseDTO();
                    radarResponseDTO.setRadarId(os.getRadar().getId());
                    radarResponseDTO.setLatitude(os.getRadar().getLatitude());
                    radarResponseDTO.setLongitude(os.getRadar().getLongitude());
                    radarResponseDTO.setRadarSpeed(os.getRadar().getMaxSpeed());
                    overSpeedResponseDTO.setRadar(radarResponseDTO);
                    return overSpeedResponseDTO;
                }).collect(Collectors.toList());
        return overSpeedResponseDTOS;
    }
    @QueryHandler
    public List<EventDataResponseDTO> handle(SubscribeToEventsQuery query) {
        return new ArrayList<>();
    }
    @EventHandler
    public void on (RadarCatchSpeedEvent event){
        log.info("RadarCatchSpeedEvent: {}");
        System.out.println("RadarCatchSpeedEvent: {}");
        OverSpeedDetection overSpeedDetection = new OverSpeedDetection();
        overSpeedDetection.setOverSpeedId(event.getId());
        overSpeedDetection.setMatricule(event.getMatricule());
        overSpeedDetection.setDate(event.getDate());
        overSpeedDetection.setVehicleSpeed(event.getVehiculeSpeed());
        Radar radar=radarRepository.findById(event.getRadarId())
                .orElseThrow(()->new RuntimeException("Radar not found"));
        overSpeedDetection.setRadar(radar);
        log.info("**********--********=> "+overSpeedDetection.getOverSpeedId());
        if(overSpeedDetection.getOverSpeedId()==null){
            overSpeedDetection.setOverSpeedId(UUID.randomUUID().toString());
        }
        overSpeedDetection.setDate(event.getDate());
        OverSpeedDetection savedOverSpeedDetection = overSpeedDetectionRepository.save(overSpeedDetection);
        EventDataResponseDTO eventDataResponseDTO=new EventDataResponseDTO(
                event.getClass().getSimpleName(),
                event
        );
        queryUpdateEmitter.emit(SubscribeToEventsQuery.class,
                (query)->true, eventDataResponseDTO);
    }

    @QueryHandler
    public List<OverSpeedResponseDTO> overSpeedsByRegNumber(GetAllOverSpeedsByRegistrationNumberQuery query){
        List<OverSpeedDetection> all = overSpeedDetectionRepository.findByMatricule(query.getRegistrationNumber());
        return all.stream()
                .map(
                        os->{
                            OverSpeedResponseDTO overSpeedResponseDTO = new OverSpeedResponseDTO();
                            overSpeedResponseDTO.setOverSpeedId(os.getOverSpeedId());
                            overSpeedResponseDTO.setVehicleSpeed(os.getVehicleSpeed());
                            overSpeedResponseDTO.setDate(os.getDate());
                            overSpeedResponseDTO.setMatricule(os.getMatricule());
                            RadarResponseDTO radarResponseDTO = new RadarResponseDTO();
                            radarResponseDTO.setRadarId(os.getRadar().getId());
                            radarResponseDTO.setLatitude(os.getRadar().getLatitude());
                            radarResponseDTO.setLongitude(os.getRadar().getLongitude());
                            radarResponseDTO.setRadarSpeed(os.getRadar().getMaxSpeed());
                            overSpeedResponseDTO.setRadar(radarResponseDTO);
                            return overSpeedResponseDTO;
                        }
                ).collect(Collectors.toList());
    }
}
