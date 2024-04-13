package streams;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
@MongoEntity(collection="streams")
public class Stream extends PanacheMongoEntity{
    @BsonProperty(value = "id")
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    @BsonProperty(value = "authorID")
    public String authorID;

    @JsonCreator
    public Stream(@JsonProperty("Id") String id,
                @JsonProperty("authorID") String authorID) {
        this.id = id;
        this.authorID = authorID;
    }

    public Stream() {
    }
}
