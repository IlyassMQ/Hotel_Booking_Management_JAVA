package util;

import exception.*;
import model.*;
import repository.RoomRepository;
import repository.UserRepository;
import repository.impl.InMemoryRoomRepository;
import repository.impl.InMemoryUserRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class Checkers {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    public Checkers(UserRepository userRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public User userCheck(UUID userId) throws UserNoteFoundException{
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new UserNoteFoundException();
        }
        return user.get();
    }

    public Room roomCheck(String roomNumber) throws RoomNotFoundException {
        Optional<Room> room = roomRepository.findByRoomNumber(roomNumber);
        if (room.isEmpty()){
            throw new RoomNotFoundException();
        }
        return room.get();
    }

    public boolean roomDispo(String roomNumber) throws RoomNotFoundException, RoomUnavailableException {
        if (roomCheck(roomNumber).getStatus() != RoomStatus.AVAILABLE){
            throw new RoomUnavailableException();
        }
        return true;
    }
    public boolean timeCheck(LocalDate checkIn,LocalDate checkOut) throws InvalidReservationDateException {
        if (checkIn.isAfter(checkOut) || checkIn.isBefore(LocalDate.now()) ||checkIn.equals(checkOut)) {
            throw new InvalidReservationDateException();
        }
        return true;
    }
    public boolean guestsCheck(Room room, int numberOfGuests){
                if (room.getCapacity() < numberOfGuests){
                    throw new RoomCapacityException();
                }
                return true;
    }


}
