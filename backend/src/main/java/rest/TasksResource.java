package rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class TasksResource {

    @GET
    @RolesAllowed({ "user" })
    @Produces(MediaType.APPLICATION_JSON)
    public String getTasks() {
        return "{\"tasks\":[\"do some stuff\",\"do some other stuff\"]}";
    }
}
