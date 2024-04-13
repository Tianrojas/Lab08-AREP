package posts;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
public class PostRepository implements PanacheMongoRepository<Post> {
    public Post findByPostId(String postId) {
        return find("id", postId).firstResult();
    }

    @Transactional
    public String createReply(String content, String authorId, String streamID) {
        String id = "P-" + generarIdAutomatico();
        Post post = new Post(id, content, authorId, false, streamID);
        post.persist();
        return id;
    }

    @Transactional
    public String createPost(String content, String authorId, String streamID) {
        String id = "P-" + generarIdAutomatico();
        Post post = new Post(id, content, authorId, true, streamID);
        post.persist();
        return id+" "+ streamID;
    }

    private static String generarIdAutomatico() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
    }

    public List<Post> getPostsByStream(String streamId, int page, int pageSize) {
        return find("streamID", streamId).page(page, pageSize).list();
    }
}
