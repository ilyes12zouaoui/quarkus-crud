package tn.ilyeszouaoui.withbasicauth.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.ilyeszouaoui.withbasicauth.common.BasicAuthRoles;
import tn.ilyeszouaoui.withbasicauth.dataobject.FoodDTO;
import tn.ilyeszouaoui.withbasicauth.persistence.FoodRepository;
import tn.ilyeszouaoui.withbasicauth.persistence.entity.FoodEntity;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class FoodResourceTest {

    @Inject
    FoodRepository foodRepository;

    @BeforeEach
    void init() {
        foodRepository.deleteAll();
    }
    
    @Test
    @TestSecurity(user = "ilyes", roles = {BasicAuthRoles.BASIC_ADMIN})
    public void getAdminAuthenticated() {
        String response = given()
                .when().get("/api/external/v1/with-basic-auth/foods/admin")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().asString();

        assertThat(response).isEqualTo("BASIC_ADMIN was authenticated successfully!!!");

    }

    @Test
    @TestSecurity(user = "ilyes", roles = {BasicAuthRoles.BASIC_USER})
    public void getAdminForbidden() {

        given()
                .when().get("/api/external/v1/with-basic-auth/foods/admin")
                .then()
                .statusCode(Response.Status.FORBIDDEN.getStatusCode());

    }

    @Test
    @TestSecurity(user = "ilyes", roles = {BasicAuthRoles.BASIC_USER})
    public void getUserAuthenticated() {

        String response = given()
                .when().get("/api/external/v1/with-basic-auth/foods/user")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().asString();

        assertThat(response).isEqualTo("BASIC_USER was authenticated successfully!!!");

    }

    @Test
    @TestSecurity(user = "ilyes", roles = {BasicAuthRoles.BASIC_USER})
    public void getUserForbidden() {

        given()
                .when().get("/api/external/v1/with-basic-auth/foods/admin")
                .then()
                .statusCode(Response.Status.FORBIDDEN.getStatusCode());

    }


    @Test
    @TestSecurity(user = "ilyes", roles = {BasicAuthRoles.BASIC_USER})
    public void createFoodTest() {
        given()
                .contentType(ContentType.JSON)
                .body(new FoodEntity("Banana", 13.123))
                .when().post("/api/external/v1/with-basic-auth/foods")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
        FoodEntity createdFoodEntity = foodRepository.findOneByName("Banana");
        assertThat(createdFoodEntity.getName()).isEqualTo("Banana");
        assertThat(createdFoodEntity.getPrice()).isEqualTo(13.123);

    }

    @Test
    public void createFoodUnauthorizedTest() {
        given()
                .contentType(ContentType.JSON)
                .body(new FoodEntity("Banana", 13.123))
                .when().post("/api/external/v1/with-basic-auth/foods")
                .then()
                .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }


    @Test
    @TestSecurity(user = "ilyes", roles = {BasicAuthRoles.BASIC_USER})
    public void updateFoodTest() {
        FoodEntity bananaFood = new FoodEntity("Banana", 111.33);
        foodRepository.persistAndFlush(bananaFood);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", bananaFood.getId())
                .body(new FoodDTO("Apple", 15.123))
                .when().put("/api/external/v1/with-basic-auth/foods/{id}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        FoodEntity updatedFoodEntity = foodRepository.findOneByName("Apple");

        assertThat(updatedFoodEntity.getName()).isEqualTo("Apple");
        assertThat(updatedFoodEntity.getPrice()).isEqualTo(15.123);
    }

    @Test
    @TestSecurity(user = "ilyes", roles = {BasicAuthRoles.BASIC_USER})
    public void deleteFoodTest() {
        FoodEntity bananaFood = new FoodEntity("Banana", 111.33);
        foodRepository.persistAndFlush(bananaFood);

        given()
                .pathParam("id", bananaFood.getId())
                .when().delete("/api/external/v1/with-basic-auth/foods/{id}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        assertThat(foodRepository.findOneByName("Banana")).isNull();
    }

    @Test
    @TestSecurity(user = "ilyes", roles = {BasicAuthRoles.BASIC_USER})
    public void getFoodsTest() {

        foodRepository.persistAndFlush(new FoodEntity("Apple", 15.2));
        foodRepository.persistAndFlush(new FoodEntity("Banana", 11.23));
        foodRepository.persistAndFlush(new FoodEntity("BMW", 1512.25));

        List<FoodDTO> foodEntityList = given()
                .when().get("/api/external/v1/with-basic-auth/foods")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().body().jsonPath().getList(".", FoodDTO.class);

        assertThat(foodEntityList)
                .extracting(FoodDTO::getName, FoodDTO::getPrice)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("Banana", 11.23),
                        Tuple.tuple("Apple", 15.2),
                        Tuple.tuple("BMW", 1512.25)
                );
    }



}
