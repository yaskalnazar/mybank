package org.itstep.model.service;


import org.itstep.model.entity.User;
import org.itstep.model.entity.dto.UserDTO;
import org.apache.log4j.Logger;


public class RegistrationService {
    private final static Logger logger = Logger.getLogger(RegistrationService.class);

    public User registerNewUser(UserDTO userDTO){
        logger.info("Fuck " + userDTO.getEmail()+" "+userDTO.getPassword());
        return new User(1,userDTO.getEmail(),userDTO.getPassword(), User.ROLE.USER);
    }
}
