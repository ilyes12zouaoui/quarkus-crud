package tn.ilyeszouaoui.withjwtauth.dataobject.mapper;

import org.mapstruct.Mapper;
import tn.ilyeszouaoui.withjwtauth.dataobject.ShopDTO;
import tn.ilyeszouaoui.withjwtauth.persistence.entity.ShopEntity;

@Mapper(componentModel = "cdi")
public interface ShopMapper {

    ShopDTO shopEntityToShopDTO(ShopEntity shopEntity);
    ShopEntity shopDTOToShopEntity(ShopDTO shopDTO);

}
