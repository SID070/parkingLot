package in.groww.bootcamp.parkinglot;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(force = true)
class Token  {
    @Id
    private final UUID id;
    private final UUID vehicleId;
    private final LocalDateTime timestamp;
    public Token(Vehicle vehicle) {
        this.id = UUID.randomUUID();
        this.vehicleId = vehicle.getId();
        this.timestamp = LocalDateTime.now();
    }

    static Token of(Vehicle vehicle) {
        return new Token(vehicle);
    }
}
