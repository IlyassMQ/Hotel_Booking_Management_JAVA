package service;

import exception.EmailAlreadyExistsException;
import exception.InvalidCredentialsException;
import exception.UserNoteFoundException;
import model.User;
import repository.UserRepository;
import util.ValidationUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AuthService {
    private final UserRepository userRepository;

    private User currentUser;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(String fullName ,String email,String phone,String password) throws EmailAlreadyExistsException{
       if (userRepository.existsByEmail(email)){
           throw new EmailAlreadyExistsException();
       } else if (ValidationUtils.emailVerfication(email) && ValidationUtils.passwordVerfication(password) && ValidationUtils.phoneVerfication(phone)) {
           User user = new User(UUID.randomUUID(),fullName,email,phone,password);
           userRepository.save(user);
       }

    }

    public void login (String email,String password) throws InvalidCredentialsException{
        Optional<User> users = userRepository.findByEmail(email);

            if (users.isEmpty()) {
                throw new InvalidCredentialsException();
            }

            User user = users.get();

            if (!user.getPassword().equals(password)) {
                throw new InvalidCredentialsException();
            }
            currentUser = user;

    }

    public void logout(){
        currentUser = null;
    }

    public void profileModif(String newFullname,String newEmail,User user) throws InvalidCredentialsException, EmailAlreadyExistsException {
        Optional<User> currentUser = userRepository.findByEmail(user.getEmail());
        if (currentUser.isEmpty()){
            throw new InvalidCredentialsException();
        }
        User userNow = currentUser.get();

        if (ValidationUtils.emailVerfication(newEmail)) {
            boolean emailEX = userRepository.existsByEmail(newEmail);
            if (emailEX && !userNow.getEmail().equals(newEmail)) {
                throw new EmailAlreadyExistsException();
            }
            userNow.setEmail(newEmail);
        }
        userNow.setFullName(newFullname);
        userRepository.save(userNow);
    }

public void passModif(User user,String newPassword ,String oldPassword) throws InvalidCredentialsException, UserNoteFoundException {
    Optional<User> currentUser = userRepository.findByEmail(user.getEmail());
    if (currentUser.isEmpty()) {
        throw new UserNoteFoundException();
    }
    User userNow = currentUser.get();

    if (!oldPassword.equals(userNow.getPassword())) {
        throw new InvalidCredentialsException();
    }
    if (ValidationUtils.passwordVerfication(newPassword)) {
        userNow.setPassword(newPassword);
    }

    userRepository.save(userNow);

}

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
