package tn.ilyeszouaoui.withbasicauth.resource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/api/external/v1/with-basic-auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BasicAuthResource {

    @GET
    @Path("/admin")
    @RolesAllowed("ADMIN")
    public Response basicAuthAdmin() {
        return Response
                .status(Status.OK)
                .entity("admin basic auth authentication successful!!!")
                .build();
    }

    @GET
    @Path("/user")
    @RolesAllowed("USER")
    public Response basicAuthUser() {
        return Response
                .status(Status.OK)
                .entity("user basic auth authentication successful!!!")
                .build();
    }

}
