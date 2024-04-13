package streams;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;


@ApplicationScoped
public class StreamService {

    @Inject
    StreamRepository streamRepository;

    public String createStream(String authorID) {
        return streamRepository.createStream(authorID);
    }

    public List<Stream> getStreamsByAuthor(String authorID, int page, int pageSize) {
        return streamRepository.getStreamsByAuthor(authorID, page, pageSize);
    }

    public List<Stream> getAllStreams(int page, int pageSize) {
        return streamRepository.getAllStreams(page, pageSize);
    }
}
