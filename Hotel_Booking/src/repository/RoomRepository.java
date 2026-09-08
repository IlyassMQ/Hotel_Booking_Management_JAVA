package repository;

import model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {

    public void save(Room room);
    public Optional<Room> findByRoomNumber(String roomNumber);
    public List<Room> findAll();
}
