package rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import client.TaskClient;

@Path("/")
public class TasksResource {

    @Inject
    @RestClient
    private TaskClient taskClient;

    @Context
    private HttpServletRequest request;

    @GET
    @RolesAllowed({ "user" })
    @Produces(MediaType.APPLICATION_JSON)
    public String getTasks() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            try {
                String jwt = (String) session.getAttribute("jwt");
                return taskClient.getTasks("Bearer " + jwt);
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        return "Invalid session";
    }
}
