package users;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class UserService {
    @Inject
    UserRepository userRepository;

    public String createUser(String username, String email, String role, String password) {
        return userRepository.createUser(username, email, role, password);
    }

    public User authUser(String email, String password) throws RuntimeException{
        return userRepository.authUser(email, password);
    }
}
