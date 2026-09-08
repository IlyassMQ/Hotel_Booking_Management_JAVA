package repository;

import model.Reservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {

    public void save(Reservation reservation);
    public Optional<Reservation> findById(UUID id);
    public Optional<Reservation> findByCode(String code);
    public List<Reservation> findByUserId(UUID userId);
    public List<Reservation> findByRoomNumber(String roomNumber);
    public List<Reservation> findAll();

}
