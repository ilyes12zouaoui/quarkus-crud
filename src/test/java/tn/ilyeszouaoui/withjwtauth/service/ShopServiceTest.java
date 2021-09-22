package tn.ilyeszouaoui.withjwtauth.service;

import io.quarkus.test.junit.QuarkusTest;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.ilyeszouaoui.withjwtauth.dataobject.ShopDTO;
import tn.ilyeszouaoui.withjwtauth.persistence.ShopRepository;
import tn.ilyeszouaoui.withjwtauth.persistence.entity.ShopEntity;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@QuarkusTest
public class ShopServiceTest {

    @Inject
    ShopService shopService;
    @Inject
    ShopRepository shopRepository;

    @BeforeEach
    void init() {
        shopRepository.deleteAll();
    }

    @Test
    public void createShopTest() {
        shopService.createShop("baguette", "food", 123.44);

        ShopEntity shopEntity = shopRepository.findOneByName("baguette");
        assertThat(shopEntity.getName()).isEqualTo("baguette");
        assertThat(shopEntity.getType()).isEqualTo("food");
        assertThat(shopEntity.getPrice()).isEqualTo(123.44);
    }


    @Test
    public void updateShopTest() {
        ShopEntity baguetteEntity = new ShopEntity("baguette", "food", 11.2);
        shopRepository.persistAndFlush(baguetteEntity);
        int entityId = baguetteEntity.getId();

        shopService.updateShop(entityId, "BMW", "car", 112.22);

        ShopEntity shopEntity = shopRepository.findById(entityId);

        assertThat(shopEntity.getName()).isEqualTo("BMW");
        assertThat(shopEntity.getType()).isEqualTo("car");
        assertThat(shopEntity.getPrice()).isEqualTo(112.22);
    }

    @Test
    public void deleteShopTest() {
        shopRepository.persistAndFlush(new ShopEntity("baguette", "food", 15.2));
        int baguetteShopId = shopRepository.findOneByName("baguette").getId();
        shopService.deleteShop(baguetteShopId);

        ShopEntity shopEntity = shopService.findShopById(baguetteShopId);

        assertThat(shopEntity).isNull();
    }

    @Test
    public void getShopsByTypeTest() {
        shopRepository.persistAndFlush(new ShopEntity("baguette", "food", 15.2));
        shopRepository.persistAndFlush(new ShopEntity("audi", "car", 1431.23));
        shopRepository.persistAndFlush(new ShopEntity("BMW", "car", 1512.25));
        shopRepository.persistAndFlush(new ShopEntity("apple", "IT", 9999.9));

        List<ShopDTO> shopEntityList = shopService.findShopsByTypeOrElseFindAll("car");

        assertThat(shopEntityList)
                .extracting(ShopDTO::getName, ShopDTO::getType, ShopDTO::getPrice)
                .containsExactly(
                        Tuple.tuple("audi", "car", 1431.23),
                        Tuple.tuple("BMW", "car", 1512.25)
                );
    }

    @Test
    public void getShopsTest() {
        shopRepository.persistAndFlush(new ShopEntity("baguette", "food", 15.2));
        shopRepository.persistAndFlush(new ShopEntity("audi", "car", 1431.23));
        shopRepository.persistAndFlush(new ShopEntity("BMW", "car", 1512.25));

        List<ShopDTO> shopEntityList = shopService.findShopsByTypeOrElseFindAll(null);

        assertThat(shopEntityList)
                .extracting(ShopDTO::getName, ShopDTO::getType, ShopDTO::getPrice)
                .containsExactly(
                        Tuple.tuple("baguette", "food", 15.2),
                        Tuple.tuple("audi", "car", 1431.23),
                        Tuple.tuple("BMW", "car", 1512.25)
                );
    }


}
