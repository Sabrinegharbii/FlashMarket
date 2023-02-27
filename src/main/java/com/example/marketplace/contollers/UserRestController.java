package com.example.marketplace.contollers;

import com.example.marketplace.entities.User;
import com.example.marketplace.services.IUserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User Management")

public class UserRestController {
    private final IUserServices userServices;

    @Operation(description = "Retrieve all users")
    @GetMapping("/all")
    List<User> getAllUsers(){

        return userServices.retrieveAllUsers();
    }

    @Operation (description = "Retrieve user")
    @GetMapping("/get/{id}")
    User getMarket(@PathVariable("id") Integer idUser){

        return userServices.retrieveUser(idUser);
    }

    @Operation (description = "Add user")
    @PostMapping("/add")
    User addUser(@RequestBody User u){
        return userServices.addUser(u);
    }

    @Operation (description = "Update user")
    @PutMapping("/update")
    User updateUser(@RequestBody User u){
        return userServices.updateUser(u);
    }

    @Operation (description = "Delete user")
    @DeleteMapping("/delete/{id}")
    void deleteMarket(@PathVariable("id") Integer idUser){
        userServices.removeUser(idUser);
    }
}
