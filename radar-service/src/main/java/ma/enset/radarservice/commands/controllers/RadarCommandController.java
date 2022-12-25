package ma.enset.radarservice.commands.controllers;

import lombok.AllArgsConstructor;
import ma.enset.commonapi.commands.CreateRadarCommand;
import ma.enset.commonapi.commands.PassedVehiculeRadarCommand;
import ma.enset.commonapi.dtos.CreateRadarRequestDTO;
import ma.enset.commonapi.dtos.PassingVehiculeDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/command/radar")
@AllArgsConstructor
@Service
@CrossOrigin(origins = "*")
public class RadarCommandController {
    private CommandGateway commandGateway;

    @PostMapping(path = "/create")
    public CompletableFuture<String> createRadar(@RequestBody CreateRadarRequestDTO createRadarRequestDTO) {
        CompletableFuture<String> response = commandGateway.send(new CreateRadarCommand(
                UUID.randomUUID().toString(),
                createRadarRequestDTO.getMaxSpeed(),
                createRadarRequestDTO.getLatitude(),
                createRadarRequestDTO.getLongitude()
        ));
        return response;
    }

    @PostMapping(path = "/passingVehicles")
    public ResponseEntity<String> passingVehicles(@RequestBody PassingVehiculeDTO passingVehiculeDTO) {
        CompletableFuture<String> response = commandGateway.send(new PassedVehiculeRadarCommand(
                UUID.randomUUID().toString(),
                passingVehiculeDTO.getMatricule(),
                passingVehiculeDTO.getVehicleSpeed(),
                passingVehiculeDTO.getRadarId(),
                passingVehiculeDTO.getRadarSpeed()
        ));
        return new ResponseEntity<>(response.join(), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}