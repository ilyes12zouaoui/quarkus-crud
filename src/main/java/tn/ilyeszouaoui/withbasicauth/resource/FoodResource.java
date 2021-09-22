package tn.ilyeszouaoui.withbasicauth.resource;

import tn.ilyeszouaoui.withbasicauth.common.BasicAuthRoles;
import tn.ilyeszouaoui.withbasicauth.dataobject.FoodDTO;
import tn.ilyeszouaoui.withbasicauth.service.FoodService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/api/external/v1/with-basic-auth/foods")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FoodResource {

    @Inject
    FoodService foodService;

    @GET
    @Path("/admin")
    @RolesAllowed(BasicAuthRoles.BASIC_ADMIN)
    public Response basicAuthAdmin() {
        return Response
                .status(Status.OK)
                .entity("BASIC_ADMIN was authenticated successfully!!!")
                .build();
    }

    @GET
    @Path("/user")
    @RolesAllowed(BasicAuthRoles.BASIC_USER)
    public Response basicAuthUser() {
        return Response
                .status(Status.OK)
                .entity("BASIC_USER was authenticated successfully!!!")
                .build();
    }


    @POST
    @Path("/")
    @RolesAllowed({BasicAuthRoles.BASIC_USER, BasicAuthRoles.BASIC_ADMIN})
    public Response createFood(FoodDTO foodDTO) {
        foodService.createFood(
                foodDTO.getName(),
                foodDTO.getPrice()
        );
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Path("/")
    @RolesAllowed({BasicAuthRoles.BASIC_USER, BasicAuthRoles.BASIC_ADMIN})
    public Response findFoods() {
        return Response
                .status(Status.OK)
                .entity(foodService.findFoods())
                .build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({BasicAuthRoles.BASIC_USER, BasicAuthRoles.BASIC_ADMIN})
    public Response findFood(@PathParam("id") int id) {
        return Response
                .status(Status.OK)
                .entity(foodService.findFoodById(id))
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({BasicAuthRoles.BASIC_USER, BasicAuthRoles.BASIC_ADMIN})
    public Response updateFood(@PathParam("id") int id, FoodDTO foodDTO) {
        foodService.updateFood(
                id,
                foodDTO.getName(),
                foodDTO.getPrice()
        );
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({BasicAuthRoles.BASIC_USER, BasicAuthRoles.BASIC_ADMIN})
    public Response deleteFood(@PathParam("id") int id) {
        foodService.deleteFood(id);
        return Response.status(Status.NO_CONTENT).build();
    }

}
