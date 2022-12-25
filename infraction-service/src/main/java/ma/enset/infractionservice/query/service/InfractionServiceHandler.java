package ma.enset.infractionservice.query.service;

import lombok.extern.slf4j.Slf4j;
import ma.enset.commonapi.commands.CreateInfractionCommand;
import ma.enset.commonapi.dtos.InfractionResponseDTO;
import ma.enset.commonapi.events.InfractionCreatedEvent;
import ma.enset.commonapi.events.OwnerCreatedEvent;
import ma.enset.commonapi.events.RadarCatchSpeedEvent;
import ma.enset.commonapi.events.VehiculeCreatedEvent;
import ma.enset.commonapi.queries.GetInfractionsByVehicle;
import ma.enset.infractionservice.commands.models.Owner;
import ma.enset.infractionservice.commands.models.Vehicule;
import ma.enset.infractionservice.commands.repositories.OwnerRepository;
import ma.enset.infractionservice.commands.repositories.VehiculeRepository;
import ma.enset.infractionservice.query.entities.Infraction;
import ma.enset.infractionservice.query.repositories.InfractionRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InfractionServiceHandler {
    private InfractionRepository infractionRepository;
    private VehiculeRepository vehiculeRepository;
    private OwnerRepository ownerRepository;

    private CommandGateway commandGateway;
    private EventStore eventStore;

    public InfractionServiceHandler(InfractionRepository infractionRepository, VehiculeRepository vehiculeRepository, OwnerRepository ownerRepository, EventStore eventStore) {
        this.infractionRepository = infractionRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.ownerRepository = ownerRepository;
        this.eventStore = eventStore;
    }

    @EventHandler
    public void on(InfractionCreatedEvent event) {
        log.info("InfractionCreatedEvent: {}");
        if(event.getMaxSpeedAllowed()< event.getVehicleSpeed()){
            Infraction infraction = new Infraction();
            infraction.setId(event.getId());
            infraction.setRadarId(event.getRadarId());
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
        System.out.println("RadarCatchSpeedEvent: {}");
        if(event.getContraventionId()==null) return;
        Infraction infraction = new Infraction();
        List<? extends DomainEventMessage<?>> domainEventMessages=new ArrayList<>();
        DomainEventStream domainEventStream = eventStore.readEvents(event.getContraventionId());
        domainEventMessages = domainEventStream.asStream().collect(Collectors.toList());
        if(domainEventMessages.size()==0){
            commandGateway.send(new CreateInfractionCommand(
                    event.getContraventionId(),
                    event.getMatricule(),
                    event.getVehiculeSpeed(),
                    event.getDate(),
                    event.getRadarId(),
                    event.getRadarSpeed(),
                    300 + 300 * (event.getVehiculeSpeed()-event.getRadarSpeed())/event.getRadarSpeed()
            ));
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

    @EventHandler
    public void on(VehiculeCreatedEvent event) {
        log.info("VehiculeCreatedEvent: {}");
        System.out.println("VehiculeCreatedEvent: {}");

        Vehicule vehicule = new Vehicule();
        vehicule.setId(UUID.randomUUID().toString());
        vehicule.setMatricule(event.getMatricule());
        vehicule.setMarque(event.getMarque());
        vehicule.setModele(event.getModele());
        String ownerId = event.getProprietaire();
        vehicule.setOwner(ownerRepository.findById(ownerId).get());
        vehicule.setPuissance(event.getPuissance());vehicule.setProprietaireId(event.getProprietaire());
        vehiculeRepository.save(vehicule);

    }
    @EventHandler
    public void on(OwnerCreatedEvent event) {
        log.info("OwnerCreatedEvent: {}");
        System.out.println("OwnerCreatedEvent: {}");
        Owner owner = new Owner();
        owner.setId(UUID.randomUUID().toString());
        owner.setName(event.getName());
        owner.setDateOfBirth(event.getDateOfBirth());
        owner.setEmail(event.getEmail());
        owner.setVehicules(new ArrayList<>());
        ownerRepository.save(owner);
    }
}
