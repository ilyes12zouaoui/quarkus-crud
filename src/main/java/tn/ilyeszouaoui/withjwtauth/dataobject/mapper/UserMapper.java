package tn.ilyeszouaoui.withjwtauth.dataobject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import tn.ilyeszouaoui.withjwtauth.dataobject.UserDTO;
import tn.ilyeszouaoui.withjwtauth.persistence.entity.UserEntity;

@Mapper(componentModel = "cdi",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDTO userEntityToUserDTO(UserEntity userEntity);
    UserEntity userDTOToUserEntity(UserDTO userDTO);

}
