package ma.enset.infractionservice.query.controllers;

import lombok.AllArgsConstructor;
import ma.enset.commonapi.queries.*;
import ma.enset.infractionservice.query.entities.Infraction;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/infraction")
@AllArgsConstructor
@Service
@CrossOrigin(origins = "*")
public class InfractionQueryHandler {
    private QueryGateway queryGateway;

    @GetMapping("/All")
    public CompletableFuture<List<Infraction>> getAll(){
        return queryGateway.query(new GetAllInfractions(), ResponseTypes.multipleInstancesOf(Infraction.class));
    }

    @GetMapping("/byIdProprietaire/{id}")
    public CompletableFuture<List<Infraction>> getByIdProprietaire(@PathVariable String id){
        return queryGateway.query(new GetInfractionsByProprietaire(id), ResponseTypes.multipleInstancesOf(Infraction.class));
    }

    @GetMapping("/byIdVehicule/{id}")
    public CompletableFuture<List<Infraction>> getByIdVehicule(@PathVariable String id){
        return queryGateway.query(new GetInfractionsByVehicle(id), ResponseTypes.multipleInstancesOf(Infraction.class));
    }
    @GetMapping("/byOwnerId/{id}")
    public CompletableFuture<List<Infraction>> getByOwnerId(@PathVariable String id){
        return queryGateway.query(new GetInfractionsByOwnerId(id), ResponseTypes.multipleInstancesOf(Infraction.class));
    }

    @GetMapping("/byId/{id}")
    public CompletableFuture<Infraction> getById(@PathVariable String id){
        return queryGateway.query(new GetInfraction(id), ResponseTypes.instanceOf(Infraction.class));
    }


}
