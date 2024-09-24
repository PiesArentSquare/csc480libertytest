package rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import util.SessionUtils;

@Path("tasks")
public class TaskResource {

    @Inject
    @RestClient
    private TaskClient restClient;

    public String getJwtAuthHeader() {
        return "Bearer: " + SessionUtils.getJwtToken();
    }


    @GET
//    @RolesAllowed({ "user" })
    @Produces(MediaType.TEXT_PLAIN)
    public String getTasks() {
        try {
            return getJwtAuthHeader();
//            return restClient.getTasks(getJwtAuthHeader());
        } catch (Exception e) {
            return "{\"response\":\"You are not authorized to access this resource\"}";
        }
    }
}
