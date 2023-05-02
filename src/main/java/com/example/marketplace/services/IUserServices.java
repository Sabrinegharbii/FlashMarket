package com.example.marketplace.services;

import com.example.marketplace.entities.User;
import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;
import java.util.List;

public interface IUserServices {
    List<User> retrieveAllUsers();

  //  User addUser (User u);

    User updateUser (User u);

    User retrieveUser(Integer idUser);

    void removeUser(Integer idUser);
    List<User> rechercheDynamique (String search);
    ResponseEntity Verification(String email, String code);

    ResponseEntity forgotPassword(String email);
    ResponseEntity resetPassword(String verifCode, String newPass);
    User getbyUsername(String username);


}
