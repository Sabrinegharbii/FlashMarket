package com.example.marketplace.contollers;

import com.example.marketplace.Playload.Response.MessageResponse;
import com.example.marketplace.entities.Panier;
import com.example.marketplace.entities.Product;
import com.example.marketplace.entities.User;
import com.example.marketplace.repository.IUserRepository;
import com.example.marketplace.services.IPanierServices;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/panier")
@CrossOrigin(origins = "*")

public class PanierController {
      private final IPanierServices panierServices;
    final IUserRepository userRepository;

    @Operation(description = "Add panier")
    @PutMapping("/add")
     Panier addPanier( @PathVariable("id") Integer IdUser){
           Panier panier=new Panier();//Panier addPanier( @PathVariabl  ("id") Integer IdUser)
        return panierServices.addPanierandaffectoUser(panier,IdUser);
//        try {
//            String username = principal.getName();
//            User u = userRepository.findByUsername(username).orElse(null);
//            Integer id = u.getId();
//            String p= String.valueOf(panierServices.addPanierandaffectoUser(panier,id));
//
//
//            //return ResponseEntity.ok(new MessageResponse(panierServices.addPanierandaffectoUser(panier,id)));
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body(new MessageResponse("Erreur"));
//        }
    }

    @Operation (description = "Update panier")
    @PutMapping("/update")
    Panier updatePanier(@RequestBody Panier panier){
        return  panierServices.updatePanier(panier);
    }

    @Operation (description = "afficher panier")
    @GetMapping("/get/{id}")
    Panier getPanier(@PathVariable("id") Integer id){

        return panierServices.retrievePanier(id);
    }


    @GetMapping("/getbyuser/{id}")
    Panier getPanierUser(@PathVariable("id") Integer id){

        return panierServices.findPanier(id);
    }

    @Operation (description = "Delete panier")
    @DeleteMapping("/delete/{id}")
    void deletePanier(@PathVariable("id") Integer id){

        panierServices.removePanier(id);
    }
    @DeleteMapping("/testRemove")
    public void testRemove() {
        panierServices.romoveListPanier();
    }
    @GetMapping("/similar/{idProduit}/{idCommande}")
    public List<Product> similarProduct(@PathVariable("idProduit") Integer idprodSimilar, @PathVariable("idCommande")Integer idC){
        return panierServices.similarProduct(idprodSimilar,idC);
    }


}
