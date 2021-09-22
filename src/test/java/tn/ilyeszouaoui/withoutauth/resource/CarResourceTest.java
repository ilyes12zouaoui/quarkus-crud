package tn.ilyeszouaoui.withoutauth.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;
import org.assertj.core.groups.Tuple;
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
public class CarResourceTest {

    @Inject
    CarRepository carRepository;

    @InjectMock
    FoodRestClient foodRestClient;

    @BeforeEach
    void init() {
        carRepository.deleteAll();
    }

    @Test
    public void createCarTest() {
        given()
                .contentType(ContentType.JSON)
                .body(new CarEntity("Banana", 13.123))
                .when().post("/api/external/v1/without-auth/cars")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
        CarEntity createdCarEntity = carRepository.findOneByName("Banana");
        assertThat(createdCarEntity.getName()).isEqualTo("Banana");
        assertThat(createdCarEntity.getPrice()).isEqualTo(13.123);

    }

    @Test
    public void updateCarTest() {
        CarEntity bananaCar = new CarEntity("Banana", 111.33);
        carRepository.persistAndFlush(bananaCar);

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", bananaCar.getId())
                .body(new CarDTO("Apple", 15.123))
                .when().put("/api/external/v1/without-auth/cars/{id}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        CarEntity updatedCarEntity = carRepository.findOneByName("Apple");

        assertThat(updatedCarEntity.getName()).isEqualTo("Apple");
        assertThat(updatedCarEntity.getPrice()).isEqualTo(15.123);
    }

    @Test
    public void deleteCarTest() {
        CarEntity bananaCar = new CarEntity("Banana", 111.33);
        carRepository.persistAndFlush(bananaCar);

        given()
                .pathParam("id", bananaCar.getId())
                .when().delete("/api/external/v1/without-auth/cars/{id}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        assertThat(carRepository.findOneByName("Banana")).isNull();
    }

    @Test
    public void getCarsTest() {

        carRepository.persistAndFlush(new CarEntity("Apple", 15.2));
        carRepository.persistAndFlush(new CarEntity("Banana", 11.23));
        carRepository.persistAndFlush(new CarEntity("BMW", 1512.25));

        List<CarDTO> carEntityList = given()
                .when().get("/api/external/v1/without-auth/cars")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().body().jsonPath().getList(".", CarDTO.class);

        assertThat(carEntityList)
                .extracting(CarDTO::getName, CarDTO::getPrice)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("Banana", 11.23),
                        Tuple.tuple("Apple", 15.2),
                        Tuple.tuple("BMW", 1512.25)
                );
    }

}
