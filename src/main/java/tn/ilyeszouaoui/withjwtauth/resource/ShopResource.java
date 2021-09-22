package tn.ilyeszouaoui.withjwtauth.resource;

import tn.ilyeszouaoui.withjwtauth.common.RoleUtils;
import tn.ilyeszouaoui.withjwtauth.service.ShopService;
import tn.ilyeszouaoui.withjwtauth.dataobject.ShopDTO;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/api/external/v1/shops")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShopResource {

    @Inject
    ShopService shopService;

    @POST
    @Path("/")
    @RolesAllowed({RoleUtils.ADMIN,RoleUtils.SHOP_OWNER})
    public Response createShop(ShopDTO shopDTO) {
        shopService.createShop(
                shopDTO.getName(),
                shopDTO.getType(),
                shopDTO.getPrice()
        );
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Path("/")
    @RolesAllowed({RoleUtils.ADMIN,RoleUtils.SHOP_OWNER})
    public Response findShops(@QueryParam("type") String type) {
        return Response
                .status(Status.OK)
                .entity(shopService.findShopsByTypeOrElseFindAll(type))
                .build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({RoleUtils.ADMIN,RoleUtils.SHOP_OWNER})
    public Response findShopById(@PathParam("id") int id) {
        return Response
                .status(Status.OK)
                .entity(shopService.findShopById(id))
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({RoleUtils.ADMIN,RoleUtils.SHOP_OWNER})
    public Response updateShop(@PathParam("id") int id, ShopDTO shopDTO) {
        shopService.updateShop(
                id,
                shopDTO.getName(),
                shopDTO.getType(),
                shopDTO.getPrice()
        );
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({RoleUtils.ADMIN,RoleUtils.SHOP_OWNER})
    public Response deleteShop(@PathParam("id") int id) {
        shopService.deleteShop(id);
        return Response.status(Status.NO_CONTENT).build();
    }

}
