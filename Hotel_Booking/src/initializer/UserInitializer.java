package initializer;

import model.User;
import repository.UserRepository;

import java.util.UUID;

public class UserInitializer {

    private final UserRepository userRepository;

    public UserInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void initializer(){
        userRepository.save(new User(UUID.randomUUID(),"ilyass","ilyass@gmail.com","0987654321","ilyass"));
        userRepository.save(new User(UUID.randomUUID(),"ilyass2","ilyass2@gmail.com","0987654231","ilyass2"));
    }
}
