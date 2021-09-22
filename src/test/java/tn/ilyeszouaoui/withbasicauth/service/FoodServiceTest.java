package tn.ilyeszouaoui.withbasicauth.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.assertj.core.groups.Tuple;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.ilyeszouaoui.withbasicauth.dataobject.FoodDTO;
import tn.ilyeszouaoui.withbasicauth.persistence.FoodRepository;
import tn.ilyeszouaoui.withbasicauth.persistence.entity.FoodEntity;
import tn.ilyeszouaoui.withoutauth.adapter.FoodRestClient;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@QuarkusTest
public class FoodServiceTest {

    @Inject
    FoodService foodService;
    @Inject
    FoodRepository foodRepository;

    @BeforeEach
    void init() {
        foodRepository.deleteAll();
    }


    @Test
    public void createFoodTest() {
        foodService.createFood("apple", 123.44);

        FoodEntity foodEntity = foodRepository.findOneByName("apple");
        assertThat(foodEntity.getName()).isEqualTo("apple");
        assertThat(foodEntity.getPrice()).isEqualTo(123.44);
    }


    @Test
    public void updateFoodTest() {
        FoodEntity bananaEntity = new FoodEntity("apple", 11.2);
        foodRepository.persistAndFlush(bananaEntity);
        int entityId = bananaEntity.getId();

        foodService.updateFood(entityId, "banana", 112.22);

        FoodEntity foodEntity = foodRepository.findById(entityId);

        assertThat(foodEntity.getName()).isEqualTo("banana");
        assertThat(foodEntity.getPrice()).isEqualTo(112.22);
    }

    @Test
    public void deleteFoodTest() {
        foodRepository.persistAndFlush(new FoodEntity("apple", 15.2));
        int AppleFoodId = foodRepository.findOneByName("apple").getId();
        foodService.deleteFood(AppleFoodId);

        FoodEntity foodEntity = foodRepository.findById(AppleFoodId);

        assertThat(foodEntity).isNull();
    }


    @Test
    public void getFoodTest() {
        foodRepository.persistAndFlush(new FoodEntity("apple", 12.2));
        foodRepository.persistAndFlush(new FoodEntity("banana", 13.2));
        foodRepository.persistAndFlush(new FoodEntity("carrot", 11.1));
        List<FoodDTO> shopEntityList = foodService.findFoods();

        assertThat(shopEntityList)
                .extracting(FoodDTO::getName, FoodDTO::getPrice)
                .containsExactly(
                        Tuple.tuple("apple",  12.2),
                        Tuple.tuple("banana", 13.2),
                        Tuple.tuple("carrot", 11.1)
                );
    }
}
