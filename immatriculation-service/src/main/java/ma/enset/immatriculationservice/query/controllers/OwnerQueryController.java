package ma.enset.immatriculationservice.query.controllers;

import lombok.AllArgsConstructor;
import ma.enset.commonapi.queries.GetOwner;
import ma.enset.commonapi.queries.GetOwners;
import ma.enset.immatriculationservice.query.entities.Owner;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/query/owner")
@AllArgsConstructor
@Service
public class OwnerQueryController {
    private QueryGateway queryGateway;


    @GetMapping(path = "/")
    public List<Owner> getOwner() {
        return queryGateway.query(new GetOwners(), ResponseTypes.multipleInstancesOf(Owner.class)).join();
    }

    @GetMapping(path = "/{id}")
    public Owner getOwner(@PathVariable String id) {
        return queryGateway.query(new GetOwner(id), Owner.class).join();
    }


}
