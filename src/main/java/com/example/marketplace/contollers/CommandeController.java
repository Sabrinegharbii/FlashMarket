package com.example.marketplace.contollers;

import com.example.marketplace.entities.*;
import com.example.marketplace.services.ICommandeServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Commande")
@CrossOrigin(origins = "*")
public class CommandeController {
    private final ICommandeServices commandeServices;
    @Operation(description = "Add commande")
    @GetMapping("/add1/{idU}/{idl}/{adr}")
    Commande addCommande(@PathVariable("adr") String adresse ,@PathVariable("idU") Integer userId,@PathVariable("idl") Integer idligne){
        Commande commande = new Commande();
        Date date=new Date();
        System.out.println("ccc");
       // Random r = new Random();
        commande.setAdressCommande(adresse);
        commande.setDateCommande(date);
//        commande.setNumCommande((long) (r.nextInt((10 - 1) + 1)));
return commandeServices.addCommande(commande,userId,idligne);


    }

    @Operation (description = "Update Commande")
    @PutMapping("/update")
    Commande updateCommande(@RequestBody Commande commande){
        return  commandeServices.updateCommande(commande);
    }

    @Operation (description = "afficher Commande")
    @GetMapping("/get/{id}")
    Commande getPanier(@PathVariable("id") Integer id){

        return commandeServices.retrieveCommande(id);
    }
    @GetMapping("/allcommande")
    List<Commande> getAllCommande() {
        return commandeServices.getAllCommande();
    }

    @Operation (description = "Delete Commande")
    @DeleteMapping("/delete/{id}")
    void deleteCommande(@PathVariable("id") Integer id){

        commandeServices.removeCommande(id);
    }
    @Operation(description = "ajouter commande au charity")
    @PutMapping("/add/{idCharity}/{idLigne}")
    Commande addcommandeToCharity(@RequestBody Commande commande,@PathVariable("idCharity") Integer IdCharity,@PathVariable("idLigne") Integer IdLigne){
        return commandeServices.addandaffectcommandeToCharity(commande,IdCharity,IdLigne);
    }
    @Operation(description = "utiliser points fidlt")
    @GetMapping("/ConverPointsFidelite/{idu}")
    Integer ConverPointsFidelite(@PathVariable("idu") Integer user){
       return commandeServices.ConverPointsFidelite(user);
    }
    @Operation(description = "afficher points fidlt")
    @PutMapping("/afficherPointsFidelité/{id}")
    int AfficherPointsFidelite(@PathVariable("id") Integer idu){
        return commandeServices.afficherPointsFidelite(idu);
    }
    @Operation(description = "excel")
    @GetMapping("/listeCharity/{filePath}")
    public List<List<String>> readExcelcharitylist(String filePath) throws IOException {
        return commandeServices.readExcelcharitylist(filePath);

    }
//    @GetMapping("/similar/{idProduit}/{idCommande}")
//    public List<Product> similarProduct(@PathVariable("idProduit") Integer idprodSimilar, @PathVariable("idCommande")Integer idC){
//        return commandeServices.similarProduct(idprodSimilar,idC);
//    }

    }
