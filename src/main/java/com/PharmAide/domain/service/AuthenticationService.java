package com.PharmAide.domain.service;


import com.PharmAide.domain.dao.User;
import com.PharmAide.domain.repository.UserRepository;
import com.PharmAide.domain.repository.interfaces.IUserRepository;
import com.PharmAide.security.RoleUser;
import com.PharmAide.security.util.AuthHandler;
import com.PharmAide.web.dto.UserAuthDTO;
import com.PharmAide.web.dto.UserDTO;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.javalin.http.UnauthorizedResponse;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AuthenticationService {
    IUserRepository userRepository;

    @Inject
    public AuthenticationService(IUserRepository userRepository) {
        this.userRepository = userRepository  ;
    }

    private static User getUserfromUserDTO(UserDTO user) {
        User myUser = new User();
        myUser.setUsername(user.getUsername());
        myUser.setPasswordHash(AuthHandler.hashPassword(user.getPassword()));
        myUser.setRole(RoleUser.valueOf(user.getRole()));
        return myUser;
    }

    public Map<String,String> validate(DecodedJWT token) {
        var user = token.getClaim("user").asString();
        var level = token.getClaim("role").asString();
        return Map.of(user,level);
    }

    public Map<String,String> authenticate(UserAuthDTO userAuth) {
        System.out.println(userAuth.getUsername());
        var checkuser = userRepository.getbyUsername(userAuth.getUsername());
        if (checkuser.isPresent() && AuthHandler.checkPassword(userAuth.getPassword(),checkuser.get().getPasswordHash())){
            var myUser = new HashMap<String,String>();
            var passeduser = checkuser.get();
            myUser.put("TOKEN",getToken(passeduser));
            myUser.put("username",passeduser.getUsername());
            myUser.put("role",passeduser.getRole().toString());
            System.out.println(myUser);
            return myUser;
        } else {
            throw new UnauthorizedResponse("Wrong Username or Password");
        }
    }

    public String getToken(User userAuth) {
        return AuthHandler.refprovider.generateToken(userAuth);
    }

    public void register(UserDTO user) {
        User myUser = getUserfromUserDTO(user);
        Optional<User> checkuser = userRepository.getbyUsername(myUser.getUsername());
        if (checkuser.isEmpty()) {
            userRepository.addUser(myUser);
        }
    }
}
