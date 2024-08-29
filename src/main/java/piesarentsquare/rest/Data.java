package piesarentsquare.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/data")
public class Data {

    @Inject
    Resource resource;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int getStuff() {
        return resource.getValue();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@QueryParam("value") Integer v) {
        if (v == null)
            return fail("invalid value");
        resource.setValue(v);
        return success("value was updated");
    }

    private Response success(String message) {
        return Response.ok("{ \"ok\" : \"" + message + "\" }").build();
    }

    private Response fail(String message) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{ \"error\" : \"" + message + "\" }")
                .build();
    }

}
