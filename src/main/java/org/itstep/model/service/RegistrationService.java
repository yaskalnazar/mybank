package org.itstep.model.service;

import org.itstep.model.dto.UserDTO;
import org.itstep.model.entity.User;
import org.apache.log4j.Logger;


public class RegistrationService {
    private final static Logger logger = Logger.getLogger(RegistrationService.class);

    public User registerNewUser(UserDTO userDTO){
        return User.getBuilder()
                .setEmail(userDTO.getEmail())
                .setPassword(userDTO.getPassword())
                .setUserRole(User.Role.USER)
                .build();
    }
}
