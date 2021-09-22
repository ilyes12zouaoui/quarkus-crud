package tn.ilyeszouaoui.withbasicauth.dataobject.mapper;

import org.mapstruct.Mapper;
import tn.ilyeszouaoui.withbasicauth.dataobject.FoodDTO;
import tn.ilyeszouaoui.withbasicauth.persistence.entity.FoodEntity;

@Mapper(componentModel = "cdi")
public interface FoodMapper {

    FoodDTO foodEntityToFoodDTO(FoodEntity foodEntity);
    FoodEntity foodDTOToFoodEntity(FoodDTO foodDTO);

}
