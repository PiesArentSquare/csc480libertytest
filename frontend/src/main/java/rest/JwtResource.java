package rest;

import com.ibm.websphere.security.jwt.Claims;
import com.ibm.websphere.security.jwt.JwtBuilder;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("jwt")
public class JwtResource {

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.TEXT_PLAIN)
    public String getJwt() {
//        return JwtBuilder.create("jwtFrontEndBuilder").claim(Claims.SUBJECT, )
        return "jwt";
    }
}
