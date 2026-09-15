package service;

import model.Room;
import model.RoomStatus;
import model.RoomType;
import repository.RoomRepository;
import util.CodeGenerater;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomService {
    private final RoomRepository RoomRepository;

    public RoomService(RoomRepository RoomRepository) {
        this.RoomRepository = RoomRepository;
    }


    public void createRoom(RoomType type, int capacity , BigDecimal pricePerNight,RoomStatus status){
        String roomNNumber = CodeGenerater.randomCode();
        Room room = new Room(roomNNumber,type,capacity,pricePerNight,status);
        RoomRepository.save(room);

    }
    public List<Room> showAllRoom(){
       return RoomRepository.findAll();
    }

    public List<Room> findRoomDispo(){
        List<Room> allRoom = RoomRepository.findAll();
        List<Room> roomsDispo= new ArrayList<>();
        for (Room r : allRoom){
            if (r.getStatus() == RoomStatus.AVAILABLE){
                roomsDispo.add(r);
            }
        }
        return roomsDispo;
    }

    public Optional<Room> searchRoomByNumber(String roomNumber){
        return RoomRepository.findByRoomNumber(roomNumber);
    }


}
