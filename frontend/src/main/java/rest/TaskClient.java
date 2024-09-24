package rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://localhost:8443/api")
@Path("/tasks")
@RequestScoped
public interface TaskClient extends AutoCloseable {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTasks(@HeaderParam("Authorization") String authHeader);
}
