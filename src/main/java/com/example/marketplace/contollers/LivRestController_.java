package com.example.marketplace.contollers;

import com.example.marketplace.entities.Livraison;
import com.example.marketplace.entities.Livreur;
import com.example.marketplace.services.FactureCommandeServices;
import com.example.marketplace.services.LivraisonService;
import com.example.marketplace.services.LivreurService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Gestionslivraison")
public class LivRestController_ {


@Autowired
private FactureCommandeServices factureCommandeServices;
    @Autowired
    private LivraisonService livraisonService;
    @Autowired
    private LivreurService livreurService;
    ////////////////////////////////////////////////////////////////////////////////////////
    //GestionLivrasion

    @PostMapping("/ajouterlivraison")
    public Livraison addlivraison(@RequestBody Livraison livraison ) {
        return livraisonService.ajouterlivraison(livraison);
    }
    @GetMapping("/afficherdetailsdelivraison")
    public List<Livraison> retrieveAllLivraison() {
        return livraisonService.retrieveAllLivraison();
    }



    @GetMapping("Rechercherunelivraison/{idlivraison}")
    public Livraison getLivraisonById(@PathVariable(value = "idlivraison") Long idlivraison) {
        return livraisonService.getLivraisonById(idlivraison);
    }

    @PutMapping("Modifierlalivraison")
    public Livraison updateLivraison(@RequestBody Livraison livraisonDetails) {
        return livraisonService.updateLivraison(livraisonDetails);
    }

    @DeleteMapping("Supprimerunelivraison/{idlivraison}")
    public ResponseEntity<?> deleteLivraison(@PathVariable(value = "idlivraison") Long idlivraison) {
        livraisonService.deleteLivraison(idlivraison);
        return ResponseEntity.ok().build();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //GestionLivreur
    @PostMapping("/ajouterlivreur")
    public Livreur addlivreur(@RequestBody Livreur livreur ) {
        return livreurService.ajouterlivreur(livreur);
    }
    @GetMapping("/afficherdetailsdulivreur")
    public List<Livreur> retrieveAllLivreur() {
        return livreurService.retrieveAllLivreur();
    }

    @PutMapping("AjouterunelivraisonEtlAffecteraunlivreur/{idlivreur}")
    public void saveandaffectLivreurtoLivraison(@RequestBody Livraison livraison,@PathVariable("idlivreur") Long idLivreur) {
         livraisonService.saveandaffectLivreurtoLivraison(livraison,idLivreur);
    }

    @GetMapping("Rechercherunlivreur/{idlivreur}")
    public Livreur getLivreurById(@PathVariable(value = "idlivreur") Long idlivreur) {
        return livreurService.getLivreurById(idlivreur);
    }

    @PutMapping("Modifierlelivreur/{idlivreur}")
    public Livreur updateLivreur(@PathVariable(value = "idlivreur") Long idlivreur,
                                 @RequestBody Livreur livreurDetails) {
        return livreurService.updateLivreur(idlivreur, livreurDetails);
    }

    @DeleteMapping("Supprimerunlivreur/{idlivreur}")
    public ResponseEntity<?> deleteLivreur(@PathVariable(value = "idlivreur") Long idlivreur) {
        livreurService.deletelivreur(idlivreur);
        return ResponseEntity.ok().build();
    }
   // @GetMapping("livvv")
   // public List<Livreur> getLiv() {
       // return livreurService.getLivreurDispo();
   // }
   // @GetMapping("nombredelivraison")
    //public int nbreliv(Long idlivreur){return livreurService.nbredelivraison(idlivreur);}

    @GetMapping("retounerstatut")
    public void activer(String x2, String y2) throws IOException
    { livreurService.retournestatut(x2,y2);}

   // @GetMapping("anneedembauche/{id}")
   // public int getdatedembauche(@PathVariable(value = "id") Long idLivreur) {return livreurService.getdatedembauche(idLivreur);}

    @GetMapping("ajouterbonusausalaire/{id}")
    public void ajouterunbonus(@PathVariable(value = "id") Long idLivreur){ livreurService.ajouterunbonus(idLivreur);}
    @GetMapping("ajouterbonusausalaireadd/{id}")
    public void ajouterunbonusadd(@PathVariable(value = "id") Long idLivreur){ livreurService.ajouterunbonusencasdelivadd(idLivreur);}

    @GetMapping("Meilleurlivreurdumois")
    public Livreur lemeilleureemployedeumois() {return livreurService.lemeilleureemployedeumois();}

        /////////////////////////////////
   /* @GetMapping("/distance/{origin}/{destination}")

    public ResponseEntity<Double> getDistance(
            @RequestParam String origin,
            @RequestParam String destination) {
        try {
            double distance = livreurService.calculateDistance(origin, destination);
            return ResponseEntity.ok(distance);
        } catch (ApiException | InterruptedException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/


    @PostMapping("tst/{id}")
    public Livraison tst(@PathVariable("id") Long id, @RequestBody Livraison lv) {
       return livraisonService.tst(id,lv);
    }

    @PostMapping("/prix-total/{id}")
    public double calculerPrixTotal(@PathVariable("id") Integer id) {
        return livraisonService.calculerPrixTotal(id);
    }

    @GetMapping(value = "/{idLivraison}/qr-code", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody ResponseEntity<byte[]> generateQRCode(@PathVariable Long idLivraison) {
        try {
            byte[] qrCode = livraisonService.generateQRCode(idLivraison);
            return ResponseEntity.ok(qrCode);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
