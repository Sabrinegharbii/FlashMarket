package com.example.marketplace.contollers;

import com.example.marketplace.Playload.Response.MessageResponse;
import com.example.marketplace.entities.Contract;
import com.example.marketplace.entities.User;
import com.example.marketplace.repository.IUserRepository;
import com.example.marketplace.services.IContractServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contract")

public class ContractRestController {
    private final IContractServices contractServices;
    private final IUserRepository userRepository;

    @Operation(description = "Retrieve all contracts")
    @GetMapping("/all")
    List<Contract> getAllContracts(){

        return contractServices.retrieveAllContracts();
    }

    @Operation (description = "Retrieve contract")
    @GetMapping("/get/{id}")
    Contract getContract(@PathVariable("id") Integer idContract){

        return contractServices.retrieveContract(idContract);
    }

    @Operation (description = "Add contract")
    @PostMapping("/add")
    ResponseEntity<?> addContract(@RequestBody Contract c, Principal principal){
     String username = principal.getName();
        User u = userRepository.findUserByUsername(username) ;
        if(u.getMarket()!=null) {
            Integer id = u.getMarket().getIdMarket();
             contractServices.addContract(c, id);
             return ResponseEntity.ok().body(new MessageResponse("Contract added Successfully!"));
        }
        else return ResponseEntity.badRequest().body(new MessageResponse("Boutique not Found for this User!"));
    }

    @Operation (description = "Update contract")
    @PutMapping("/update")
    String updateContract(Principal principal,@RequestParam Integer monthsNumber){
        String username = principal.getName();
        User u = userRepository.findUserByUsername(username) ;
        if(u.getMarket().getContract()!=null) {
           Contract c = u.getMarket().getContract();
            return contractServices.updateContract(c,monthsNumber);
        }
        else return "Market Not found For this User"    ;
    }

    @Operation (description = "Delete contract")
    @DeleteMapping("/delete/{id}")
    void deleteContract(@PathVariable("id") Integer idContract){
        contractServices.removeContract(idContract);
    }
}
