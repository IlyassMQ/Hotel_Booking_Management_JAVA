package initializer;

import model.Room;
import model.RoomStatus;
import model.RoomType;
import repository.RoomRepository;
import util.CodeGenerater;

import java.math.BigDecimal;

public class RoomInitializer {

    private final RoomRepository roomRepository;

    public RoomInitializer(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void initializer(){
        roomRepository.save(new Room(
                CodeGenerater.roomNumber(),
                RoomType.SINGLE,
                1,
                new BigDecimal("300"),
                RoomStatus.AVAILABLE
        ));

        roomRepository.save(new Room(
                CodeGenerater.roomNumber(),
                RoomType.SINGLE,
                1,
                new BigDecimal("300"),
                RoomStatus.AVAILABLE
        ));

        roomRepository.save(new Room(
                CodeGenerater.roomNumber(),
                RoomType.DOUBLE,
                2,
                new BigDecimal("450"),
                RoomStatus.AVAILABLE
        ));

        roomRepository.save(new Room(
                CodeGenerater.roomNumber(),
                RoomType.DOUBLE,
                2,
                new BigDecimal("450"),
                RoomStatus.MAINTENANCE
        ));

        roomRepository.save(new Room(
                CodeGenerater.roomNumber(),
                RoomType.SUITE,
                5,
                new BigDecimal("600"),
                RoomStatus.AVAILABLE
        ));

        roomRepository.save(new Room(
                CodeGenerater.roomNumber(),
                RoomType.SUITE,
                7,
                new BigDecimal("600"),
                RoomStatus.AVAILABLE
        ));

        roomRepository.save(new Room(
                "555",
                RoomType.SUITE,
                8,
                new BigDecimal("1000"),
                RoomStatus.AVAILABLE
        ));

        roomRepository.save(new Room(
                CodeGenerater.roomNumber(),
                RoomType.SUITE,
                4,
                new BigDecimal("900"),
                RoomStatus.MAINTENANCE
        ));
    }
}
