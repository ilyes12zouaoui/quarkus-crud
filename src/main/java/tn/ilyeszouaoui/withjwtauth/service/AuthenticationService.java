package tn.ilyeszouaoui.withjwtauth.service;

import tn.ilyeszouaoui.withjwtauth.common.JWTUtils;
import tn.ilyeszouaoui.withjwtauth.common.PasswordUtils;
import tn.ilyeszouaoui.withjwtauth.common.RoleUtils;
import tn.ilyeszouaoui.withjwtauth.dataobject.LoginResponseDTO;
import tn.ilyeszouaoui.withjwtauth.persistence.UserRepository;
import tn.ilyeszouaoui.withjwtauth.persistence.entity.UserEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class AuthenticationService {

    @Inject
    UserRepository userRepository;

    public void registerShowOwnerUser(String firstName, String lastName, String email, String password, int age) {
        userRepository.persist(new UserEntity(firstName, lastName, email, PasswordUtils.hashPassword(password), RoleUtils.SHOP_OWNER, age));
    }

    public void registerAdminUser(String firstName, String lastName, String email, String password, int age) {
        userRepository.persist(new UserEntity(firstName, lastName, email, PasswordUtils.hashPassword(password), RoleUtils.ADMIN, age));
    }

    public LoginResponseDTO login(String email, String password) {
        UserEntity userEntity = userRepository.findOneByEmail(email)
                .orElseThrow(() -> new RuntimeException("wrong email or password!"));

        if (!PasswordUtils.verifyPassword(password, userEntity.getPassword())) {
            throw new RuntimeException("wrong email or password!");
        }

        String jwt = JWTUtils.generateJWT(
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getRole(),
                userEntity.getAge()
        );

        return new LoginResponseDTO(jwt);
    }
}
