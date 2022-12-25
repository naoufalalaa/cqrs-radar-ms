package ma.enset.radarservice.query.controllers;

import lombok.AllArgsConstructor;
import ma.enset.commonapi.dtos.OverSpeedResponseDTO;
import ma.enset.commonapi.queries.FindAllRadars;
import ma.enset.commonapi.queries.GetAllOverSpeedsByRegistrationNumberQuery;
import ma.enset.commonapi.queries.GetAllOverSpeedsQuery;
import ma.enset.radarservice.query.entities.Radar;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/radar")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class RadarRestController {
    private QueryGateway queryGateway;

    @GetMapping("/all")
    public List<Radar> getAll(){
        return queryGateway.query(new FindAllRadars(), ResponseTypes.multipleInstancesOf(Radar.class)).join();
    }

    @GetMapping("/overSpeeds/all")
    public CompletableFuture<List<OverSpeedResponseDTO>> allOverSpeeds(){
        return queryGateway.query(
                new GetAllOverSpeedsQuery()
                , ResponseTypes.multipleInstancesOf(OverSpeedResponseDTO.class)
        );
    }

    @GetMapping("/overSpeeds/byRegNumber/{regNumber}")
    public CompletableFuture<List<OverSpeedResponseDTO>> overSpeedsByRegNumber(@PathVariable String regNumber){
        return queryGateway.query(
                new GetAllOverSpeedsByRegistrationNumberQuery(regNumber)
                , ResponseTypes.multipleInstancesOf(OverSpeedResponseDTO.class)
        );
    }

}
