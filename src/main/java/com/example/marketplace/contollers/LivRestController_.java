package com.example.marketplace.contollers;

import com.example.marketplace.entities.Livraison;
import com.example.marketplace.entities.Livreur;
import com.example.marketplace.services.LivraisonService;
import com.example.marketplace.services.LivreurService;
import com.google.maps.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/Gestionslivraison")
public class LivRestController_ {

    @Autowired
    private LivraisonService livraisonService;
    @Autowired
    private LivreurService livreurService;
    ////////////////////////////////////////////////////////////////////////////////////////
    //GestionLivrasion
    @GetMapping("/afficherdetailsdelivraison")
    public List<Livraison> retrieveAllLivraison() {
        return livraisonService.retrieveAllLivraison();
    }



    @GetMapping("Rechercherunelivraison/{id}")
    public Livraison getLivraisonById(@PathVariable(value = "idlivraison") Long idlivraison) {
        return livraisonService.getLivraisonById(idlivraison);
    }

    @PutMapping("Modifierlalivraison/{id}")
    public Livraison updateLivraison(@PathVariable(value = "idlivraison") Long idlivraison,
                                     @RequestBody Livraison livraisonDetails) {
        return livraisonService.updateLivraison(idlivraison, livraisonDetails);
    }

    @DeleteMapping("Supprimerunelivraison/{id}")
    public ResponseEntity<?> deleteLivraison(@PathVariable(value = "idlivraison") Long idlivraison) {
        livraisonService.deleteLivraison(idlivraison);
        return ResponseEntity.ok().build();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //GestionLivreur
    @GetMapping("/afficherdetailsdulivreur")
    public List<Livreur> retrieveAllLivreur() {
        return livreurService.retrieveAllLivreur();
    }

    @PutMapping("AjouterunelivreurEtAffecter/{id}")
    public Livraison saveandaffectLivreurtoLivraison(@RequestBody Livraison livraison,@PathVariable("id") Long idLivreur) {
        return livraisonService.saveandaffectLivreurtoLivraison(livraison,idLivreur);
    }

    @GetMapping("Rechercherunlivreur/{id}")
    public Livreur getLivreurById(@PathVariable(value = "idlivreur") Long idlivreur) {
        return livreurService.getLivreurById(idlivreur);
    }

    @PutMapping("Modifierlelivreur/{id}")
    public Livreur updateLivreur(@PathVariable(value = "idlivreur") Long idlivreur,
                                 @RequestBody Livreur livreurDetails) {
        return livreurService.updateLivreur(idlivreur, livreurDetails);
    }

    @DeleteMapping("Supprimerunlivreur/{id}")
    public ResponseEntity<?> deleteLivreur(@PathVariable(value = "idlivreur") Long idlivreur) {
        livreurService.deletelivreur(idlivreur);
        return ResponseEntity.ok().build();
    }
    @GetMapping("livvv")
    public List<Livreur> getLiv() {
        return livreurService.getLivreurDispo();
    }
    @GetMapping("nombredelivraison")
    public int nbreliv(Long idlivreur){return livreurService.nbredelivraison(idlivreur);}

   // @GetMapping("test/{idlivreur}")
    //public void activer(@PathVariable(value = "idlivreur")Long idlivreur){ livreurService.retournestatut(idlivreur);}

    @GetMapping("anneedembauche/{id}")
    public int getdatedembauche(@PathVariable(value = "id") Long idLivreur) {return livreurService.getdatedembauche(idLivreur);}

    @GetMapping("ajouterbonusausalaire/{id}")
    public void ajouterunbonus(@PathVariable(value = "id") Long idLivreur){ livreurService.ajouterunbonus(idLivreur);}
    @GetMapping("ajouterbonusausalaireadd/{id}")
    public void ajouterunbonusadd(@PathVariable(value = "id") Long idLivreur){ livreurService.ajouterunbonusencasdelivadd(idLivreur);}


        /////////////////////////////////
    @GetMapping("/distance/{origin}/{destination}")

    public ResponseEntity<Double> getDistance(
            @RequestParam String origin,
            @RequestParam String destination) {
        try {
            double distance = livreurService.calculateDistance(origin, destination);
            return ResponseEntity.ok(distance);
        } catch (ApiException | InterruptedException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
