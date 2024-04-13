package posts;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.inject.Inject;
import streams.StreamService;

import java.util.List;


@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostController {

    @Inject
    PostService postService;

    @Inject
    StreamService streamService;

    //curl -X POST -H "Content-Type: application/json" -d "{\"content\": \"primer_reply_q\", \"authorID\": \"U-20240410203052\", \"streamID\": \"S-20240410203957\"}" http://localhost:8080/posts/reply
    @POST
    @Path("/reply")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createReply(Post post) {
        String content = post.content;
        String authorID = post.authorID;
        String streamID = post.streamID;
        return postService.createReply(content, authorID, streamID);
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createPost(Post post) {
        String content = post.content;
        String authorID = post.authorID;
        String streamId = streamService.createStream(authorID);
        return postService.createPost(content, authorID, streamId);
    }

    @GET
    @Path("/{id}")
    public Post getPost(@PathParam("id") String postId) {
        return postService.getPostById(postId);
    }

    //http://localhost:8080/posts/postsbystream?streamID=S-20240412145744&page=0&pageSize=2
    @GET
    @Path("/postsbystream")
    public List<Post> getPostsByStream(@QueryParam("streamID") String streamID,
                                       @QueryParam("page") int page,
                                       @QueryParam("pageSize") int pageSize) {
        return postService.getPostsByStream(streamID, page, pageSize);
    }

}
