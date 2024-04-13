package users;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.inject.Inject;
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
    PostService postService;
    @Inject
    UserService userService;
    @Inject
    StreamService streamService;
    //curl -X POST -H "Content-Type: application/json" -d "{\"username\": \"ejemploUsuario\", \"email\": \"ejemplo@correo.com\", \"password\": \"contrasena123\"}" http://localhost:8080/users/user
    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_JSON)
    public User authUser(User user) {
        String email = user.email;
        String password = user.password;
        return userService.authUser(email, password);
    }

    @POST
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(User user) {
        String username = user.username;
        String email = user.email;
        String password = user.password;
        String role = user.role;
        return userService.createUser(username, email, role, password);
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createPost(Post post) {
        String content = post.content;
        String authorId = post.authorID;
        String streamId = streamService.createStream(authorId);
        return postService.createPost(content, authorId, streamId);
    }

    @POST
    @Path("/reply")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createReply(Post post) {
        String content = post.content;
        String authorId = post.authorID;
        String streamId = post.streamID;
        return postService.createReply(content, authorId, streamId);
    }

    @GET
    @Path("/posts")
    public List<PostDTO> getPosts(@QueryParam("page") int page,
                                  @QueryParam("pageSize") int pageSize) {
        List<Stream> streams = streamService.getAllStreams(page, pageSize);
        return getPostDTOsFromStreams(streams);
    }

    @GET
    @Path("/postsByAuthor")
    public List<PostDTO> getPostsByAuthor(@QueryParam("authorID") String authorID,
                                          @QueryParam("page") int page,
                                          @QueryParam("pageSize") int pageSize) {
        List<Stream> streams = streamService.getStreamsByAuthor(authorID, page, pageSize);
        return getPostDTOsFromStreams(streams);
    }

    private List<PostDTO> getPostDTOsFromStreams(List<Stream> streams) {
        List<PostDTO> postDTOs = new ArrayList<>();
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

    static class PostDTO {
        private String streamId;
        private List<Post> head = new ArrayList<>();
        private List<Post> tail = new ArrayList<>();

        public PostDTO(String streamId) {
            this.streamId = streamId;
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
