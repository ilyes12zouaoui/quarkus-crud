package tn.ilyeszouaoui.withoutauth.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.assertj.core.groups.Tuple;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.ilyeszouaoui.withoutauth.adapter.FoodRestClient;
import tn.ilyeszouaoui.withoutauth.dataobject.CarDTO;
import tn.ilyeszouaoui.withoutauth.dataobject.FoodRestClientDTO;
import tn.ilyeszouaoui.withoutauth.persistence.CarRepository;
import tn.ilyeszouaoui.withoutauth.persistence.entity.CarEntity;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
public class FoodRestClientResourceTest {

    @InjectMock
    @RestClient
    FoodRestClient foodRestClient;

    @Test
    public void getFoodCarByNameTest() {

        when(foodRestClient.getFoodName(any())).thenReturn(new FoodRestClientDTO("food image test"));

        FoodRestClientDTO foodRestClientDTOResponse = given()
                .pathParam("name", "food_name")
                .when().get("/api/external/v1/without-auth/food-using-rest-client/{name}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(FoodRestClientDTO.class);

        assertThat(foodRestClientDTOResponse.getImage()).isEqualTo("food image test");

    }

}
