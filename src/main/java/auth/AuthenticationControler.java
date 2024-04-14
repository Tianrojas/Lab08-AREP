package auth;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import users.User;
import users.UserService;
import org.eclipse.microprofile.jwt.Claims;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationControler {
    @Inject
    UserService userService;

    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authUser(User user) {
        String email = user.email;
        String password = user.password;

        User authenticatedUser = userService.authUser(email, password);

        if (authenticatedUser != null) {
            String role = authenticatedUser.getRole();
            String token = Jwt.issuer("https://localhost:4573/auth")
                    .upn(email)
                    .claim(Claims.email.name(), email)
                    .claim("role", role)
                    .sign();
            return Response.ok(token).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Authentication failed").build();
        }
    }

}
