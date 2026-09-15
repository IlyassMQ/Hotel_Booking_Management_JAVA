import UI.ConsoleReservation;
import UI.ConsoleRoom;
import UI.ConsoleUser;
import exception.*;
import initializer.RoomInitializer;
import initializer.UserInitializer;
import repository.impl.InMemoryReservationRepository;
import repository.impl.InMemoryRoomRepository;
import repository.impl.InMemoryUserRepository;
import service.AuthService;
import service.ReservationService;
import service.RoomService;
import util.Checkers;
import util.InputUtils;

import java.util.Scanner;

public class Main {

    public void main() {

        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemoryReservationRepository reservationRepository = new InMemoryReservationRepository();
        InMemoryRoomRepository roomRepository = new InMemoryRoomRepository();
        Checkers checker = new Checkers(userRepository,roomRepository);

        AuthService authService = new AuthService(userRepository);
        RoomService roomService = new RoomService(roomRepository);
        ReservationService reservationService = new ReservationService(authService,reservationRepository,checker);

        ConsoleUser consoleUI = new ConsoleUser(authService);
        ConsoleRoom consoleRoom = new ConsoleRoom(roomService);
        ConsoleReservation consoleReservation = new ConsoleReservation(reservationService,authService);

        RoomInitializer roomInitializer = new RoomInitializer(roomRepository);
        UserInitializer userInitializer = new UserInitializer(userRepository);

        userInitializer.initializer();
        roomInitializer.initializer();
        Scanner scanner = new Scanner(System.in);

        boolean continuer = true;

        while (continuer) {

                if (authService.getCurrentUser() == null){
                    consoleUI.afficherMenuNonConneter();
                    int choix = InputUtils.lireInt(scanner,"Entrer Votre choix");
                    switch (choix) {
                        case 1:
                            consoleUI.register();
                            break;

                        case 2:
                            consoleUI.login();
                            break;
                        case 0:
                            continuer = false;
                            break;

                        default:
                            System.out.println("Choix invalide.");
                    }
                }else {
                    consoleUI.afficherMenu();
                    int choix = InputUtils.lireInt(scanner,"Entrer Votre choix");
                    switch (choix) {
                        case 1:
                            consoleRoom.roomDisponible();
                            break;
                        case 2:
                            consoleRoom.viewAllRoom();
                            break;
                        case 3:
                            consoleReservation.makeReservation();
                            break;
                        case 4:
                            consoleReservation.myReservation();
                            break;
                        case 5:
                            consoleReservation.showReservation();
                            break;
                        case 6:
                            consoleReservation.modifierReservation();
                            break;
                        case 7:
                            consoleReservation.cancelReservation();
                            break;
                        case 8:
                            consoleUI.profil();
                            break;
                        case 9:
                            consoleUI.profileModif();
                            break;
                        case 10:
                            consoleUI.passModif();
                            break;
                        case 11:
                            consoleUI.logout();
                            break;
                        case 0 :
                            continuer = false;
                            break;
                        default:
                            System.out.println("choix invalid.");
                            break;
                    }

                }



        }

        scanner.close();


    }
}

