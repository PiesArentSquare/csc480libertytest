package client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://localhost:8443/api")
@Path("tasks")
public interface TaskClient extends AutoCloseable {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTasks(@HeaderParam("Authorization") String authHeader);
}
