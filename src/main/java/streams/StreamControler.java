package streams;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.inject.Inject;

import java.util.List;

@Path("/streams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StreamControler {
    @Inject
    StreamService streamService;

    @POST
    @Path("/stream")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createReply(Stream stream) {
        String authorID = stream.authorID;
        return streamService.createStream(authorID);
    }

    @GET
    @Path("/streamsbyauthor")
    public List<Stream> getStreamsByAuthor(@QueryParam("authorID") String authorID,
                                           @QueryParam("page") int page,
                                           @QueryParam("pageSize") int pageSize) {
        return streamService.getStreamsByAuthor(authorID, page, pageSize);
    }

}
