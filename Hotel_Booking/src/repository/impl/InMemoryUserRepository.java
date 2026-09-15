package repository.impl;

import model.User;
import repository.UserRepository;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {

    Map <UUID,User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getId(),user);
    }
    @Override
    public Optional<User> findById(UUID id){
                return Optional.ofNullable(users.get(id));
    }
    @Override
    public Optional<User> findByEmail(String email){
        for (User user : users.values()) {
            if (user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }


    @Override
    public boolean existsByEmail(String email){
        return users.values().stream().anyMatch(u -> u.getEmail().equals(email));
    }
    @Override
    public List<User> findAll(){
       return new ArrayList<>(users.values());
    }


}
