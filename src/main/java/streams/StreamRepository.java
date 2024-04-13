package streams;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
public class StreamRepository implements PanacheMongoRepository<Stream> {

    @Transactional
    public String createStream(String authorID) {
        String id = "S-" + generateAutomaticId();
        Stream stream = new Stream(id, authorID);
        stream.persist();
        return id;
    }

    private static String generateAutomaticId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
    }

    public List<Stream> getAllStreams(int page, int pageSize) {
        return findAll().page(page, pageSize).list();
    }

    public List<Stream> getStreamsByAuthor(String authorID, int page, int pageSize) {
        return find("authorID", authorID).page(page, pageSize).list();
    }
}
