package com.example.marketplace.contollers;

import com.example.marketplace.entities.Market;
import com.example.marketplace.entities.User;
import com.example.marketplace.repository.IUserRepository;
import com.example.marketplace.services.IMarketServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")

@RequiredArgsConstructor
@RequestMapping("/market")


public class MarketRestController {

    private final IMarketServices marketServices;
    private final IUserRepository userRepository;

    @Operation(description = "Retrieve all markets")
    @GetMapping("/all")
    List<Market> getAllMarkets(){

        return marketServices.retrieveAllMarkets();
    }

    @Operation (description = "Retrieve market")
    @GetMapping("/get/{id}")
    Market getMarket(@PathVariable("id") Integer idMarket){

        return marketServices.retrieveMarket(idMarket);
    }

    /*@Operation (description = "Add market")
    @PostMapping("/add")
    Market addMarket(@RequestBody Market m){
        return marketServices.addMarket(m);
    }*/

    @Operation (description = "Update market")
    @PutMapping("/update")
    Market updateMarket(@RequestBody Market m){
        return marketServices.updateMarket(m);
    }

    @Operation (description = "Delete market")
    @DeleteMapping("/delete/{id}")
    void deleteMarket(@PathVariable("id") Integer idMarket){
        marketServices.removeMarket(idMarket);
    }

    @Operation (description = "Add market")
    @PostMapping("/addMarket")
    String addMarket(@RequestBody Market m, Principal principal){
        String username = principal.getName();
        User user = userRepository.findUserByUsername(username);
        if (user.getMarket()==null) {
            Integer idCurrent = user.getId();
            marketServices.addAndAsignToUser(m, idCurrent);
            return "Market Added Successfully ! Please Proceed to finalising your Contract or Your Market will be automatically Deleted ";
        }
        else return " You already have a market named : "+ user.getMarket().getMarketName();
    }


}
