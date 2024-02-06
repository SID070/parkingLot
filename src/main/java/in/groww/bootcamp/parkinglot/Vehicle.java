package in.groww.bootcamp.parkinglot;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(force = true)
class Vehicle {
    @Id
    private final UUID id= UUID.randomUUID();
    private final String name;
    private final String licenseNumber;

    @Enumerated(value = EnumType.STRING)
    private VehicleStatus status = VehicleStatus.PARKED;

    Vehicle(String name, String licenseNumber) {
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    void unpark() {
        status = VehicleStatus.UNPARKED;
    }
}
