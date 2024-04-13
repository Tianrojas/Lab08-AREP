package users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;

@MongoEntity(collection="users")
public class User extends PanacheMongoEntity{
    @BsonProperty(value = "id")
    public String id;
    @BsonProperty(value = "username")
    public String username;
    @BsonProperty(value = "email")
    public String email;
    @BsonProperty(value = "password")
    public String password;
    @BsonProperty(value = "role")
    public String role;

    @JsonCreator
    public User(@JsonProperty("id") String id,
                @JsonProperty("username") String username,
                @JsonProperty("email") String email,
                @JsonProperty("role") String role,
                @JsonProperty("password") String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
