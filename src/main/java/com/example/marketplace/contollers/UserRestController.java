package com.example.marketplace.contollers;
import com.example.marketplace.entities.Market;
import com.example.marketplace.entities.User;
import com.example.marketplace.repository.IUserRepository;
import com.example.marketplace.services.IUserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping("/user")


public class UserRestController {

    private final IUserServices userServices;
    private final IUserRepository userRepository;


    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "Retrieve all users")
    @GetMapping("/all")
    List<User> getAllUsers(){

        return userServices.retrieveAllUsers();
    }

    @Operation(description = "Retrieve by Username")
    @GetMapping("/getusername/{usr}")
    User getUsername(@PathVariable("usr") String username){

        return userServices.getbyUsername(username);
    }



    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation (description = "Retrieve User")
    @GetMapping("/get/{id}")
    User getUser(@PathVariable("id") Integer idUser){

        return userServices.retrieveUser(idUser);
    }

    @Operation (description = "Retrieve My Account")
    @GetMapping("/getMyAccount")
    User getMyAccount(Principal principal){
        if (principal.getName()!=null) {
            String username = principal.getName();
            User user = userRepository.findUserByUsername(username);
            Integer idCurrent = user.getId();


            return userServices.retrieveUser(idCurrent);
        }
        else {
            System.out.println("Please SignIn or SignUp");
            return null;
        }
    }

   /* @Operation (description = "Add user")
    @PostMapping("/add")
    User addUser(@RequestBody User u){
        return userServices.addUser(u);
    }*/

    @Operation (description = "Update user")
    @PutMapping("/update")
    User updateUser(@RequestBody User u){
        return userServices.updateUser(u);
    }

    @Operation (description = "Delete user")
    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable("id") Integer idUser){
        userServices.removeUser(idUser);
    }
    @Operation (description = "Search user")
    @GetMapping("/rechercheDynamique")
    List<User> searchUsers(@RequestParam(required = false) String recherche) {
        return userServices.rechercheDynamique(recherche);
    }



}

