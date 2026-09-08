package repository;

import model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    public void save(User user);
    public Optional<User> findById(UUID id);
    public Optional<User> findByEmail(String email);
    public boolean existsByEmail(String email);
    public List<User> findAll();

}
