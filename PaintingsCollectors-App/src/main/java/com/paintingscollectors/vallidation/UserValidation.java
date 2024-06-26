package com.paintingscollectors.vallidation;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidation {

    private final UserRepository userRepository;
    private final UserSession userSession;

    public UserValidation(UserRepository userRepository, UserSession userSession) {
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    public boolean usernameIsNotValid(String fieldData) {
        if(fieldData.length() < 3 || fieldData.length() > 20){return true;}
        return false;
    }

    public boolean passwordIsNotValid(String fieldData){
        if(fieldData.length() < 3 || fieldData.length() > 20){return true;}
        return false;
    }

    public boolean emailIsEmpty(String email) {
        if(email.isEmpty()){return true;}
        return false;
    }

}
