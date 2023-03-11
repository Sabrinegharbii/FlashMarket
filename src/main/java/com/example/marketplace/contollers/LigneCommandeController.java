package com.example.marketplace.contollers;

import com.example.marketplace.entities.LigneCommande;
import com.example.marketplace.services.ILigneCommandeServices;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/LigneCommande")
public class LigneCommandeController {
private final ILigneCommandeServices ligneCommandeServices;

    @Operation(description = "Add lignecommande")
    @PutMapping("/add/{idPa}/{idPr}")
    LigneCommande addLigneCommande(@RequestBody LigneCommande ligneCommande,@PathVariable("idPa") Integer IdPanier,@PathVariable("idPr") Integer IdProduct){
        return ligneCommandeServices.affecterpanierAndProductlignedecommande(ligneCommande,IdPanier,IdProduct);

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
}
