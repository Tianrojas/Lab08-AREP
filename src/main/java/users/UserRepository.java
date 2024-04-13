package users;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jose4j.jwk.Use;
import org.mindrot.jbcrypt.BCrypt;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.mindrot.jbcrypt.BCrypt;
import posts.Post;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<User> {
    @Transactional
    public String createUser(String username, String email, String role, String password) {
        String id = "U-" + generateAutomaticId();
        User user = new User(id, username, email, role, password);
        user.persist();
        return id;
    }

    private static String generateAutomaticId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
    }

    public User authUser(String email, String password) throws RuntimeException {
        User user = find("email", email).firstResult();
        if (user == null) {
            return null;
        }
        if (password.equals(user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

}
