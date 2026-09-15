package UI;

import model.Room;
import service.RoomService;

import java.util.List;
import java.util.Scanner;

public class ConsoleRoom {

    private RoomService roomService;

    public ConsoleRoom(RoomService roomService) {
        this.roomService = roomService;
    }

    public void roomDisponible(){
       List<Room> dispoRooms = roomService.findRoomDispo();
        printRooms(dispoRooms);
    }

    private void printRooms(List<Room> dispoRooms) {
        for (Room r : dispoRooms){
            System.out.println("=====================================");
            System.out.println("Room Number : " + r.getRoomNumber());
            System.out.println("Room Type : " + r.getType());
            System.out.println("Room Capacity : " + r.getCapacity());
            System.out.println("Room Price : " + r.getPricePerNight() + " Per night");
            System.out.println("Status : " + r.getStatus());
            System.out.println("====================================");
        }
    }

    public void viewAllRoom(){
        List<Room> rooms = roomService.showAllRoom();
        printRooms(rooms);

    }

}
