package in.groww.bootcamp.parkinglot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParkingLotServiceTest {

    @Autowired
    private ParkingLotService parkingLotService;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @AfterEach
    void deleteAll() {
        vehicleRepository.deleteAll();
        tokenRepository.deleteAll();
    }

    @Test
    void expectTokenWhenParkingVehicle() {
        Vehicle vehicle = new Vehicle("car", "1234");
        Optional<Token> optionalToken = parkingLotService.park(vehicle);
        assertTrue(optionalToken.isPresent());

    }

    @Test
    void expectNoTokenWhenParkingIsFull() {
        parkingLotService.park(autoPark());
        parkingLotService.park(autoPark());
        parkingLotService.park(autoPark());
        parkingLotService.park(autoPark());
        Vehicle vehicle1 = new Vehicle("car4", "123456");
        Optional<Token> optionalToken = parkingLotService.park(vehicle1);
        assertTrue(optionalToken.isEmpty());
    }

    @Test
    void expectVehicleWhenWeReturnToken() {
        Vehicle vehicle = new Vehicle("car3", "233");
        Optional<Token> optionalToken = parkingLotService.park(vehicle);
        assertTrue((optionalToken.isPresent()));
        Optional<Vehicle> optionalVehicle = parkingLotService.unpark(optionalToken.get().getId());
        assertTrue(optionalVehicle.isPresent());
    }

    @Test
    void expectNoVehicleWhenWeHaveInsufficientToken(){
        Vehicle vehicle=new Vehicle("bike" , "2313");
        Optional<Token> optionalToken=parkingLotService.park(vehicle);
       // assertTrue((optionalToken.isPresent()));
        Optional<Vehicle>optionalVehicle =parkingLotService.unpark(optionalToken.get().getId());
       // assertTrue(optionalVehicle.isPresent());
        Optional<Vehicle>secondAttemptToUnpark = parkingLotService.unpark(optionalToken.get().getId());
        assertTrue(secondAttemptToUnpark.isEmpty());
    }


    private Vehicle autoPark() {
        return new Vehicle("random", "DL013445");
    }

}

