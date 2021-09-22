package tn.ilyeszouaoui.withjwtauth.service;

import tn.ilyeszouaoui.withjwtauth.dataobject.ShopDTO;
import tn.ilyeszouaoui.withjwtauth.dataobject.mapper.ShopMapper;
import tn.ilyeszouaoui.withjwtauth.persistence.ShopRepository;
import tn.ilyeszouaoui.withjwtauth.persistence.entity.ShopEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class ShopService {

    @Inject
    ShopMapper shopMapper;

    @Inject
    ShopRepository foodRepository;

    public void createShop(String name, String type, double price) {
        ShopEntity shopEntity = new ShopEntity(name, type, price);
        foodRepository.persist(shopEntity);
    }

    public void updateShop(int id, String name, String type, double price) {
        ShopEntity shopEntity = foodRepository.findById(id);
        shopEntity.setName(name);
        shopEntity.setType(type);
        shopEntity.setPrice(price);
        shopEntity.persist();
    }

    public void deleteShop(int id) {
        foodRepository.deleteById(id);
    }

    public List<ShopDTO> findShops() {
        return foodRepository
                .listAll()
                .stream()
                .map(shopEntity -> shopMapper.shopEntityToShopDTO(shopEntity))
                .collect(Collectors.toList());
    }

    public List<ShopDTO> findShopsByTypeOrElseFindAll(String type) {
        List<ShopEntity> shopEntityList = type == null ? foodRepository.listAll() : foodRepository.findAllByType(type);
        return shopEntityList
                .stream()
                .map(shopEntity -> shopMapper.shopEntityToShopDTO(shopEntity))
                .collect(Collectors.toList());
    }

    public ShopEntity findShopById(int id) {
        return foodRepository.findById(id);
    }


}
