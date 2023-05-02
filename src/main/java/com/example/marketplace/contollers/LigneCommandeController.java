package com.example.marketplace.contollers;

import com.example.marketplace.entities.LigneCommande;
import com.example.marketplace.services.ILigneCommandeServices;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/LigneCommande")
@CrossOrigin(origins = "*")



public class LigneCommandeController {
private final ILigneCommandeServices ligneCommandeServices;

    @Operation(description = "Add lignecommande")
    @PostMapping("/add/{idPa}/{idPr}/{qty}")
    LigneCommande addLigneCommande(@PathVariable("idPa") Integer IdPanier,@PathVariable("idPr") Integer IdProduct,@PathVariable("qty") Integer qty){
        LigneCommande ligneCommande=new LigneCommande();
        ligneCommande.setQuantiteProduit(qty);
        return ligneCommandeServices.affecterpanierAndProductlignedecommande(ligneCommande,IdPanier,IdProduct);

    }
@GetMapping("/listelgcommandes")
public List<LigneCommande> listeligencommande(){
       return ligneCommandeServices.listeligencommande();
}
    @Operation (description = "Update ligneCommande")
    @PutMapping("/update")
    LigneCommande updateLigneCommande(@RequestBody LigneCommande ligneCommande){
        return  ligneCommandeServices.updateLigneCommande(ligneCommande);
    }

    @Operation (description = "afficher ligne Commande")
    @GetMapping("/get/{id}")
    LigneCommande getLigneCommande(@PathVariable("id") Integer id){

        return ligneCommandeServices.retrieveLigneCommande(id);
    }
    @Operation (description = "afficher ligne Commande")
    @GetMapping("/getlistLigne/{id}")
    List<LigneCommande> getLigneCommandeByPanier(@PathVariable("id") Integer id){

        return ligneCommandeServices.retrieveLigneCommandeByPanier(id);
    }
    @Operation (description = "Delete ligneCommande")
    @DeleteMapping("/delete/{id}")
    void deletePanier(@PathVariable("id") Integer id){

        ligneCommandeServices.removeLigneCommande(id);
    }
}
