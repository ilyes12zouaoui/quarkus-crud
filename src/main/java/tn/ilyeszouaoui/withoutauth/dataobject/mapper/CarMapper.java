package tn.ilyeszouaoui.withoutauth.dataobject.mapper;

import org.mapstruct.Mapper;
import tn.ilyeszouaoui.withoutauth.dataobject.CarDTO;
import tn.ilyeszouaoui.withoutauth.persistence.entity.CarEntity;

@Mapper(componentModel = "cdi")
public interface CarMapper {

    CarDTO carEntityToCarDTO(CarEntity carEntity);
    CarEntity carDTOToCarEntity(CarDTO carDTO);

}
