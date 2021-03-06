package tn.ilyeszouaoui.withoutauth.resource;

import tn.ilyeszouaoui.withoutauth.service.CarService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/api/external/v1/without-auth/food-using-rest-client")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FoodRestClientResource {

    @Inject
    CarService carService;

    @GET
    @Path("/{name}")
    public Response findFoodCars(@PathParam("name") String name) {
        return Response
                .status(Status.OK)
                .entity(carService.findFoodRestClientByName(name))
                .build();
    }

}
