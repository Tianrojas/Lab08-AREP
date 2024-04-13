package posts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
@MongoEntity(collection="posts")
public class Post extends PanacheMongoEntity {
    @BsonProperty(value = "id")
    public String id;

    @BsonProperty(value = "content")
    public String content;

    @BsonProperty(value = "authorID")
    public String authorID;

    @BsonProperty(value = "head")
    public boolean head;

    @BsonProperty(value = "streamID")
    public String streamID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public boolean isHead() {
        return head;
    }

    public void setHead(boolean head) {
        this.head = head;
    }

    public String getStreamID() {
        return streamID;
    }

    public void setStreamID(String streamID) {
        this.streamID = streamID;
    }

    @JsonCreator
    public Post(@JsonProperty("id") String id,
                @JsonProperty("content") String content,
                @JsonProperty("authorID") String authorID,
                @JsonProperty("head") boolean head,
                @JsonProperty("streamID") String streamID) {
        this.id = id;
        this.content = content;
        this.authorID = authorID;
        this.head = head;
        this.streamID = streamID;
    }

    public Post() {
    }
}

