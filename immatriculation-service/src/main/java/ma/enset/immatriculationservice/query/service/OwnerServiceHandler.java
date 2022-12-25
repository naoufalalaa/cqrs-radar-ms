package ma.enset.immatriculationservice.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.commonapi.events.OwnerCreatedEvent;
import ma.enset.commonapi.queries.GetOwner;
import ma.enset.commonapi.queries.GetOwners;
import ma.enset.immatriculationservice.query.entities.Owner;
import ma.enset.immatriculationservice.query.repositories.OwnerRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class OwnerServiceHandler {
    private OwnerRepository ownerRepository;


    @EventHandler
    @Transactional
    public void on(OwnerCreatedEvent event) {
        log.info("OwnerCreatedEvent: {}", event);
        Owner owner = new Owner();
        owner.setId(event.getId());
        owner.setName(event.getName());
        owner.setDateOfBirth(event.getDateOfBirth());
        owner.setEmail(event.getEmail());
        ownerRepository.save(owner);
    }


    @QueryHandler
    public List<Owner> on(GetOwners query) {
        return ownerRepository.findAll();
    }

    @QueryHandler
    public Owner on(GetOwner query) {
        return ownerRepository.findById(query.getId()).get();
    }
}
