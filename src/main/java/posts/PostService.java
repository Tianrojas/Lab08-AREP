package posts;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class PostService {

    @Inject
    PostRepository postRepository;

    public String createReply(String content, String authorID, String streamID) {
        return postRepository.createReply(content, authorID, streamID);
    }

    public String createPost(String content, String authorID, String streamID) {
        return postRepository.createPost(content, authorID, streamID);
    }
    public Post getPostById(String postID) {
        return postRepository.findByPostId(postID);
    }

    public List<Post> getPostsByStream(String streamID, int page, int pageSize) {
        return postRepository.getPostsByStream(streamID, page, pageSize);
    }


}
