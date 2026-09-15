package service;

import exception.*;
import model.*;
import repository.ReservationRepository;
import repository.RoomRepository;
import repository.UserRepository;
import util.CalculUtil;
import util.Checkers;
import util.CodeGenerater;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AuthService authService;
    private final Checkers checkers;




    public ReservationService(AuthService authService, ReservationRepository reservationRepository,Checkers checkers) {

        this.reservationRepository = reservationRepository;
        this.authService = authService;
        this.checkers = checkers;
    }

    public void createReservation(String roomNumber, UUID userId, LocalDate checkIn, LocalDate checkOut, int numberOfGuests) throws RoomNotFoundException, UserNoteFoundException, RoomUnavailableException, InvalidReservationDateException {

        Room room = checkers.roomCheck(roomNumber);
        User user = checkers.userCheck(userId);

        checkers.roomDispo(room.getRoomNumber());
        checkers.timeCheck(checkIn,checkOut);
        List<Reservation> roomReservation = reservationRepository.findByRoomNumber(roomNumber);
        for (Reservation r : roomReservation) {
            if (r.getStatus().equals(ReservationStatus.CANCELLED)) {
                continue;
            }
            if (checkIn.isBefore(r.getCheckOut()) && checkOut.isAfter(r.getCheckIn())) {
                throw new InvalidReservationDateException();
            }
        }
        long numberOfNights = ChronoUnit.DAYS.between(checkIn, checkOut);
        BigDecimal TotalPrice = CalculUtil.PriceCalcul(checkIn, checkOut, room.getPricePerNight());
        String reservationCode =CodeGenerater.resevationCode();
        checkers.guestsCheck(room,numberOfGuests);
        Reservation reservation = new Reservation(UUID.randomUUID(), reservationCode, user.getId(), room.getRoomNumber(),
                checkIn, checkOut, numberOfGuests, numberOfNights, TotalPrice, ReservationStatus.CONFIRMED, LocalDateTime.now());
        reservationRepository.save(reservation);

    }

    public List<Reservation> getUserReservations() throws ReservationNotFoundException {
        User user = authService.getCurrentUser();
        List<Reservation> allReservations = reservationRepository.findAll();
        if (allReservations.isEmpty()){
            throw new ReservationNotFoundException();
        }
        List<Reservation> userReservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getUserId().equals(user.getId())) {
                userReservations.add(r);
            }
        }
        return userReservations;
    }

    public void UpdateReservation(String reservationCode, String roomNumber, LocalDate checkOut, LocalDate checkIn, int numberOfGuests) throws ReservationNotFoundException, RoomNotFoundException, RoomUnavailableException, InvalidReservationDateException {
        List<Reservation> userReservations = getUserReservations();
        Reservation reservationToModify = null;
        for (Reservation r : userReservations) {
            if (r.getReservationCode().equals(reservationCode)) {
                reservationToModify = r;
            }
        }
        if (reservationToModify == null || reservationToModify.getStatus().equals(ReservationStatus.CANCELLED)) {
            throw new ReservationNotFoundException();
        }
        Room room = checkers.roomCheck(roomNumber);

        checkers.roomDispo(room.getRoomNumber());
        checkers.timeCheck(checkIn, checkOut);

        List<Reservation> roomReservations = reservationRepository.findByRoomNumber(roomNumber);

        for (Reservation r : roomReservations) {
            if (r.getId().equals(reservationToModify.getId())) {
                continue;
            }

            if (checkIn.isBefore(r.getCheckOut()) && checkOut.isAfter(r.getCheckIn()) || checkIn.equals(checkOut)) {
                throw new InvalidReservationDateException();
            }
        }

        long numberOfNights = ChronoUnit.DAYS.between(checkIn, checkOut);
        BigDecimal totalPrice = CalculUtil.PriceCalcul(checkIn, checkOut, room.getPricePerNight());
        reservationToModify.setRoomNumber(room.getRoomNumber());
        reservationToModify.setCheckIn(checkIn);
        reservationToModify.setCheckOut(checkOut);
        reservationToModify.setNumberOfGuests(numberOfGuests);
        reservationToModify.setNumberOfNights(numberOfNights);
        reservationToModify.setTotalPrice(totalPrice);

        reservationRepository.save(reservationToModify);
    }


    public void cancelReservation(String reservationCode) throws ReservationNotFoundException {
        List<Reservation> userReservations = getUserReservations();
        Reservation reservationToCancel = null;
        for (Reservation r : userReservations) {
            if (r.getReservationCode().equals(reservationCode)) {
                reservationToCancel = r;
            }
        }
        if (reservationToCancel == null) {
            throw new ReservationNotFoundException();
        }
        reservationToCancel.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservationToCancel);
    }


    public Optional<Reservation> findReservationByCode(String Code) throws ReservationNotFoundException {
        Optional<Reservation> reservation = reservationRepository.findByCode(Code);
        if (reservation.isEmpty()){
            throw new ReservationNotFoundException();
        }
        return reservation;
    }
    
}




