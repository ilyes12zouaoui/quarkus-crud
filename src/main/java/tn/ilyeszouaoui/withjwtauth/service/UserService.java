package tn.ilyeszouaoui.withjwtauth.service;

import tn.ilyeszouaoui.withjwtauth.dataobject.UserDTO;
import tn.ilyeszouaoui.withjwtauth.dataobject.mapper.UserMapper;
import tn.ilyeszouaoui.withjwtauth.persistence.UserRepository;
import tn.ilyeszouaoui.withjwtauth.persistence.entity.UserEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class UserService {

    @Inject
    UserMapper userMapper;

    @Inject
    UserRepository userRepository;

    public void updateUser(int id, String name, String type, double price) {
        UserEntity userEntity = userRepository.findById(id);
        userEntity.setName(name);
        userEntity.setType(type);
        userEntity.setPrice(price);
        userEntity.persist();
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public List<UserDTO> findUsers() {
        return userRepository
                .listAll()
                .stream()
                .map(userEntity -> userMapper.userEntityToUserDTO(userEntity))
                .collect(Collectors.toList());
    }

    public UserEntity findUserById(int id) {
        return userRepository.findById(id);
    }

    public
}
