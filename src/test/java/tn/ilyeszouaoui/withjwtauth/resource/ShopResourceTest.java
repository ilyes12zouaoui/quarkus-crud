package tn.ilyeszouaoui.withjwtauth.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.ilyeszouaoui.withjwtauth.dataobject.ShopDTO;
import tn.ilyeszouaoui.withjwtauth.persistence.entity.ShopEntity;
import tn.ilyeszouaoui.withoutauth.adapter.FoodRestClient;
import tn.ilyeszouaoui.withjwtauth.persistence.ShopRepository;
import tn.ilyeszouaoui.withoutauth.dataobject.FoodRestClientDTO;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@QuarkusTest
public class ShopResourceTest {

    @Inject
    ShopRepository foodRepository;

    @InjectMock
    FoodRestClient foodRestClient;

    @BeforeEach
    void init() {
        foodRepository.deleteAll();
    }

    @Test
    @TestSecurity(user = "ilyes", roles = {"PRODUCT_OWNER"})
    public void createShopTest() {
        given()
                .contentType(ContentType.JSON)
                .body(new ShopEntity("Banana", "food", 13.123))
                .when().post("/api/external/v1/shops")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
        ShopEntity createdShopEntity = foodRepository.findOneByName("Banana");
        assertThat(createdShopEntity.getName()).isEqualTo("Banana");
        assertThat(createdShopEntity.getType()).isEqualTo("food");
        assertThat(createdShopEntity.getPrice()).isEqualTo(13.123);
    }

    @Test
    @TestSecurity(user = "ilyes", roles = {"PRODUCT_OWNER"})
    public void updateShopTest() {
        ShopEntity bananaShop = new ShopEntity("Banana", "food", 111.33);
        foodRepository.persistAndFlush(bananaShop);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", bananaShop.getId())
                .body(new ShopEntity("Apple", "food", 15.123))
                .when().put("/api/external/v1/shops/{id}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        ShopEntity updatedShopEntity = foodRepository.findOneByName("Apple");

        assertThat(updatedShopEntity.getName()).isEqualTo("Apple");
        assertThat(updatedShopEntity.getType()).isEqualTo("food");
        assertThat(updatedShopEntity.getPrice()).isEqualTo(15.123);
    }

    @Test
    @TestSecurity(user = "ilyes", roles = {"PRODUCT_OWNER"})
    public void deleteShopTest() {
        ShopEntity bananaShop = new ShopEntity("Banana", "food", 111.33);
        foodRepository.persistAndFlush(bananaShop);

        given()
                .pathParam("id", bananaShop.getId())
                .when().delete("/api/external/v1/shops/{id}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        assertThat(foodRepository.findOneByName("Banana")).isNull();
    }

    @Test
    @TestSecurity(user = "ilyes", roles = {"PRODUCT_OWNER"})
    public void getShopsTest() {

        foodRepository.persistAndFlush(new ShopEntity("Apple", "food", 15.2));
        foodRepository.persistAndFlush(new ShopEntity("Banana", "food", 11.23));
        foodRepository.persistAndFlush(new ShopEntity("BMW", "car", 1512.25));

        List<ShopDTO> shopEntityList = given()
                .when().get("/api/external/v1/shops")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().body().jsonPath().getList(".", ShopDTO.class);

        assertThat(shopEntityList)
                .extracting(ShopDTO::getName, ShopDTO::getType, ShopDTO::getPrice)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("Banana", "food", 11.23),
                        Tuple.tuple("Apple", "food", 15.2),
                        Tuple.tuple("BMW", "car", 1512.25)
                );
    }

    @Test
    @TestSecurity(user = "ilyes", roles = {"PRODUCT_OWNER"})
    public void getShopsByTypeTest() {

        foodRepository.persistAndFlush(new ShopEntity("Apple", "food", 15.2));
        foodRepository.persistAndFlush(new ShopEntity("Banana", "food", 11.23));
        foodRepository.persistAndFlush(new ShopEntity("BMW", "car", 1512.25));

        List<ShopDTO> shopEntityList = given()
                .queryParam("type", "food")
                .when().get("/api/external/v1/shops")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().body().jsonPath().getList(".", ShopDTO.class);

        assertThat(shopEntityList)
                .extracting(ShopDTO::getName, ShopDTO::getType, ShopDTO::getPrice)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("Banana", "food", 11.23),
                        Tuple.tuple("Apple", "food", 15.2)
                );
    }


}
