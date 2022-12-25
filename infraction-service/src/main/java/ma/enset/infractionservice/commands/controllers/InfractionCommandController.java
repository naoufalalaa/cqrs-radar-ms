package ma.enset.infractionservice.commands.controllers;

import lombok.AllArgsConstructor;
import ma.enset.commonapi.commands.CreateInfractionCommand;
import ma.enset.commonapi.dtos.InfractionCreationRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/command/infraction")
@AllArgsConstructor
@Service
@CrossOrigin(origins = "*")
public class InfractionCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping("/create")
    public CompletableFuture<String> create(@RequestBody InfractionCreationRequestDTO infractionCreationRequestDTO){
        CompletableFuture<String> response = commandGateway.send(new CreateInfractionCommand(
                UUID.randomUUID().toString(),
                infractionCreationRequestDTO.getMatricule(),
                infractionCreationRequestDTO.getSpeed(),
                infractionCreationRequestDTO.getDate(),
                infractionCreationRequestDTO.getRadarId(),
                infractionCreationRequestDTO.getRadarSpeed(),
                infractionCreationRequestDTO.getAmende()));
        return response;
    }
    @GetMapping("/events/{infractionId}")
    public Stream eventsStream(@PathVariable String infractionId){
        return eventStore.readEvents(infractionId).asStream();
    }
}
