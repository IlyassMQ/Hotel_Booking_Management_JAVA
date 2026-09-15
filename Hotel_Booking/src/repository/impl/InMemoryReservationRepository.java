package repository.impl;

import model.Reservation;
import model.Room;
import repository.ReservationRepository;

import java.util.*;

public class InMemoryReservationRepository implements ReservationRepository {
        Map<String, Reservation> reservations = new HashMap<>();
    @Override
    public void save(Reservation reservation){
        reservations.put(reservation.getReservationCode(),reservation);
    }

    @Override
    public Optional<Reservation> findByCode(String reservationCode) {
        return Optional.ofNullable(reservations.get(reservationCode));
    }

    @Override
    public List<Reservation> findByRoomNumber(String roomNumber){
            List<Reservation> reservationResult = new ArrayList<>();
        for (Reservation r : reservations.values()){
            if (roomNumber.equals(r.getRoomNumber())){
                reservationResult.add(r);
            }
        }
        return reservationResult;
    }

    @Override
    public List<Reservation> findByUserId(UUID userId) {
        List<Reservation> reservationResult = new ArrayList<>();
        for (Reservation r : reservations.values()){
            if (userId.toString().equals(r.getRoomNumber())){
                reservationResult.add(r);
            }
        }
        return reservationResult;
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }
}
