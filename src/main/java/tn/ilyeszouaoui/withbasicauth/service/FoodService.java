package tn.ilyeszouaoui.withbasicauth.service;

import tn.ilyeszouaoui.withbasicauth.dataobject.FoodDTO;
import tn.ilyeszouaoui.withbasicauth.dataobject.mapper.FoodMapper;
import tn.ilyeszouaoui.withbasicauth.persistence.FoodRepository;
import tn.ilyeszouaoui.withbasicauth.persistence.entity.FoodEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class FoodService {
    

    @Inject
    FoodMapper foodMapper;

    @Inject
    FoodRepository foodRepository;
    
    public void createFood(String name, double price) {
        FoodEntity foodEntity = new FoodEntity(name, price);
        foodRepository.persist(foodEntity);
    }

    public void updateFood(int id, String name, double price) {
        FoodEntity foodEntity = foodRepository.findById(id);
        foodEntity.setName(name);
        foodEntity.setPrice(price);
        foodEntity.persist();
    }

    public void deleteFood(int id) {
        foodRepository.deleteById(id);
    }


    public List<FoodDTO> findFoods() {
        return foodRepository.listAll()
                .stream()
                .map(foodEntity -> foodMapper.foodEntityToFoodDTO(foodEntity))
                .collect(Collectors.toList());
    }

    public FoodEntity findFoodById(int id) {
        return foodRepository.findById(id);
    }


}
