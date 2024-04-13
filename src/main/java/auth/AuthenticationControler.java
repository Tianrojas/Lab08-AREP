package auth;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import users.User;
import users.UserService;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationControler {
    @Inject
    UserService userService;

    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    public User authUser(User user) {
        String email = user.email;
        String password = user.password;
        return userService.authUser(email, password);
    }
}
