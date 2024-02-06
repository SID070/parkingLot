package in.groww.bootcamp.parkinglot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@RestController
public class Controller {

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping(value="/park")
    ResponseEntity<Response<ParkResponse>> whilePark(@RequestBody Vehicle vehicle){
        Optional<Token> optionalToken = parkingLotService.park(vehicle);
        if(optionalToken.isEmpty()){
            return Response.failed(HttpStatus.BAD_REQUEST , "Not enough space");
        }
        ParkResponse parkResponse =new ParkResponse(optionalToken.get() ,vehicle);
        return  Response.success(HttpStatus.OK , parkResponse);
    }

    @GetMapping(value ="/unpark")
    ResponseEntity<Response<Vehicle>> whileUnpark(@RequestParam("tokenId") UUID tokenId){
        Optional<Vehicle>optionalVehicle = parkingLotService.unpark(tokenId);
        if(optionalVehicle.isEmpty()){
            return Response.failed(HttpStatus.BAD_REQUEST ,"Vehichle is not present");
        }
        return  Response.success(HttpStatus.OK ,optionalVehicle.get());
    }

}


//private final ParkingLot parkingLot = new ParkingLot(3);
//
//    @PostMapping(value = "/park")
//    ResponseEntity<TokenResponse> park(@RequestBody Vehicle vehicle) {
//        Optional<Token> optionalToken = parkingLot.park(vehicle);
//        TokenResponse tokenResponse = optionalToken
//                .map(TokenResponse::success)
//                .orElseGet(TokenResponse::failed);
//        return ResponseEntity.ok(tokenResponse);
//    }
//    @GetMapping(value = "/unpark")
//    ResponseEntity<VehicleResponse> unpark(@RequestParam UUID tokenId) {
//        Optional<Vehicle> optionalVehicle = parkingLot.unpark(tokenId);
//        VehicleResponse vehicleResponse = optionalVehicle
//                .map(VehicleResponse::success)
//                .orElseGet(VehicleResponse::failed);
//        return ResponseEntity.ok(vehicleResponse);
//    }