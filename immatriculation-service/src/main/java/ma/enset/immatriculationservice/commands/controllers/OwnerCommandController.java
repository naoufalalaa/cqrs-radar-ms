package ma.enset.immatriculationservice.commands.controllers;

import lombok.AllArgsConstructor;
import ma.enset.commonapi.commands.CreateOwnerCommand;
import ma.enset.commonapi.dtos.CreateOwnerRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/command/owner")
@AllArgsConstructor
@Service
public class OwnerCommandController {
    private CommandGateway commandGateway;

    @PostMapping(path = "/create")
    public CompletableFuture<String> createOwner(@RequestBody CreateOwnerRequestDTO createOwnerRequestDTO) {
        CompletableFuture<String> response = commandGateway.send(new CreateOwnerCommand(
                UUID.randomUUID().toString(),
                createOwnerRequestDTO.getName(),
                createOwnerRequestDTO.getDateOfBirth(),
                createOwnerRequestDTO.getEmail()
        ));
        return response;
    }
}
