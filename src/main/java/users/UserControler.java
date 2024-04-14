package users;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import posts.Post;
import posts.PostService;
import streams.Stream;
import streams.StreamService;

import java.util.ArrayList;
import java.util.List;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserControler {

    @Inject
    JsonWebToken jwt;
    @Inject
    PostService postService;
    @Inject
    UserService userService;
    @Inject
    StreamService streamService;

    @POST
    @Path("/auth")
    @RolesAllowed("Admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public User authUser(@Context SecurityContext ctx, User user) {
        String email = user.email;
        String password = user.password;
        return userService.authUser(email, password);
    }

    @POST
    @Path("/user")
    @RolesAllowed({ "User", "Admin" })
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(@Context SecurityContext ctx, User user) {
        String username = user.username;
        String email = user.email;
        String password = user.password;
        String role = user.role;
        return userService.createUser(username, email, role, password);
    }

    @POST
    @Path("/post")
    @RolesAllowed({ "User", "Admin" })
    @Consumes(MediaType.APPLICATION_JSON)
    public String createPost(@Context SecurityContext ctx, Post post) {
        String content = post.content;
        String authorId = post.authorID;
        String streamId = streamService.createStream(authorId);
        return postService.createPost(content, authorId, streamId);
    }

    @POST
    @Path("/reply")
    @RolesAllowed({ "User", "Admin" })
    @Consumes(MediaType.APPLICATION_JSON)
    public String createReply(@Context SecurityContext ctx, Post post) {
        String content = post.content;
        String authorId = post.authorID;
        String streamId = post.streamID;
        return postService.createReply(content, authorId, streamId);
    }

    @GET
    @Path("/posts")
    @RolesAllowed({ "Admin" })
    public List<PostDTO> getPosts(@QueryParam("page") int page,
                                  @QueryParam("pageSize") int pageSize,
                                  @Context SecurityContext ctx) {
        List<Stream> streams = streamService.getAllStreams(page, pageSize);
        return getPostDTOsFromStreams(streams, ctx);
    }

    @GET
    @Path("/postsByAuthor")
    @RolesAllowed({ "User", "Admin" })
    public List<PostDTO> getPostsByAuthor(@QueryParam("authorID") String authorID,
                                          @QueryParam("page") int page,
                                          @QueryParam("pageSize") int pageSize,
                                          @Context SecurityContext ctx) {
        List<Stream> streams = streamService.getStreamsByAuthor(authorID, page, pageSize);
        return getPostDTOsFromStreams(streams, ctx);
    }

    private List<PostDTO> getPostDTOsFromStreams(List<Stream> streams, SecurityContext ctx) {
        List<PostDTO> postDTOs = new ArrayList<>();
        postDTOs.add(new PostDTO(null, getResponseString(ctx)));
        for (Stream stream : streams) {
            List<Post> posts = postService.getPostsByStream(stream.getId(), 0, Integer.MAX_VALUE);
            PostDTO postDTO = new PostDTO(stream.getId());
            for (Post post : posts) {
                if (post.isHead()) {
                    postDTO.addToHead(post);
                } else {
                    postDTO.addToTail(post);
                }
            }
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }

    private String getResponseString(SecurityContext ctx) {
        if (ctx.getUserPrincipal() == null) {
            return "anonymous";
        } else {
            return ctx.getUserPrincipal().getName();
        }
    }

    static class PostDTO {
        private String streamId;
        private String user;
        private List<Post> head = new ArrayList<>();
        private List<Post> tail = new ArrayList<>();

        public PostDTO(String streamId) {
            this.streamId = streamId;
        }
        public PostDTO(String streamId, String user) {
            this.user = user;
        }

        public String getStreamId() {
            return streamId;
        }

        public void setStreamId(String streamId) {
            this.streamId = streamId;
        }

        public List<Post> getHead() {
            return head;
        }

        public void addToHead(Post post) {
            this.head.add(post);
        }

        public List<Post> getTail() {
            return tail;
        }

        public void addToTail(Post post) {
            this.tail.add(post);
        }
    }

}
