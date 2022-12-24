package ma.enset.immatriculationservice.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.commonapi.events.VehiculeCreatedEvent;
import ma.enset.commonapi.queries.GetVehicule;
import ma.enset.commonapi.queries.GetVehicules;
import ma.enset.immatriculationservice.query.entities.Owner;
import ma.enset.immatriculationservice.query.entities.Vehicule;
import ma.enset.immatriculationservice.query.repositories.OwnerRepository;
import ma.enset.immatriculationservice.query.repositories.VehiculeRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class VehiculeServiceHandler {
    private VehiculeRepository vehiculeRepository;
    private OwnerRepository ownerRepository;

    @EventHandler
    @Transactional
    public void on(VehiculeCreatedEvent event) {
        log.info("VehiculeCreatedEvent: {}", event);
        Owner owner = ownerRepository.findById(event.getProprietaire()).get();
        Vehicule vehicule = new Vehicule();
        vehicule.setId(event.getId());
        vehicule.setMatricule(event.getMatricule());
        vehicule.setMarque(event.getMarque());
        vehicule.setModele(event.getModele());
        vehicule.setPuissance(event.getPuissance());
        vehicule.setOwner(owner);
        vehicule.setProprietaireId(owner.getId());
        vehiculeRepository.save(vehicule);
    }


    @QueryHandler
    public List<Vehicule> on(GetVehicules query) {
        return vehiculeRepository.findAll();
    }

    @QueryHandler
    public Vehicule on(GetVehicule query) {
        return vehiculeRepository.findById(query.getId()).get();
    }


}
