package in.groww.bootcamp.parkinglot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingLotService {
    private static final int DEFAULT_CAPACITY = 3;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private final int capacity;

    public ParkingLotService() {
        this.capacity = DEFAULT_CAPACITY;
    }

    Optional<Token> park(Vehicle vehicle) {
        if (parkingLotFull()) {
            return Optional.empty();
        }
        Token token = Token.of(vehicle);
        vehicleRepository.save(vehicle);
        tokenRepository.save(token);
        return Optional.of(token);
    }

    private boolean parkingLotFull() {
        int parkedVehicles = vehicleRepository.findByStatus(VehicleStatus.PARKED).size();
        return parkedVehicles >= capacity;
    }

    public Optional<Vehicle> unpark(UUID tokenId) {
        Optional<Token> token = tokenRepository.findById(tokenId);
        if (token.isEmpty()) {
            return Optional.empty();
        }
        UUID VehicleId = token.get().getVehicleId();
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(VehicleId);
        if (optionalVehicle.isEmpty()) {
            return Optional.empty();
        }
        Vehicle vehicle = unparkVehicle(optionalVehicle.get());
        return Optional.of(vehicle);
    }

    Vehicle unparkVehicle(Vehicle vehicle) {
        vehicle.unpark();
        return vehicleRepository.save(vehicle);
    }


}


//
//private final int capacity;
//private final Map<UUID,Vehicle> tokenToVehicle;
//
//public ParkingLot(int capacity) {
//    this.capacity = capacity;
//    this.tokenToVehicle = new HashMap<>();
//}
//
//
//Optional<Token> park(Vehicle vehicle) {
//    if(tokenToVehicle.size() >= capacity) {
//        return Optional.empty();
//    }
//    Token token = new Token(vehicle);
//
//    tokenToVehicle.put(token.getId(),vehicle);
//    return Optional.of(token);
//}
//
//Optional<Vehicle> unpark(UUID tokenId){
//    return Optional.ofNullable(tokenToVehicle.remove(tokenId));
//
//}