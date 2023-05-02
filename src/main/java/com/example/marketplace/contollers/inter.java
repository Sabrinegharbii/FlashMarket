package com.example.marketplace.contollers;

import com.example.marketplace.Playload.Response.MessageResponse;
import com.example.marketplace.entities.*;
import com.example.marketplace.enumerations.Statuss;
import com.example.marketplace.enumerations.Sujetrec;
import com.example.marketplace.repository.IUserRepository;
import com.example.marketplace.services.IRecService;
import com.example.marketplace.services.InterventionServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class inter {
    @Autowired
    InterventionServ interserv;
    @Autowired
    IRecService recserv;
    @Autowired
    IUserRepository userRepository;
    @PostMapping("/assignadmintointer")
    public ResponseEntity<?> addandassignuserintervention(@RequestBody Intervention inter)  {
        try {
            //   String username = principal.getName();
            //     User u = userRepository.findByUsername(username).orElse(null);
            //   Integer id = u.getId();
            Integer id=1;
            return ResponseEntity.ok(new MessageResponse(interserv.ajouterintervention(id,inter)));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Erreur"));
        }

    }

    @DeleteMapping("/supprimerinter/{id}")
    public  void delete(@PathVariable("id") Long id ){
        interserv.deleteByIdd(id);
    }
    @GetMapping("/listeinterventions")
    public List<Intervention> listedesinetrventionss(){
        return interserv.listedesinetrventions();
    }
    @PutMapping("/updateintervention/{id}/{dureeinter}")
    public void updateTicket(@PathVariable Long id, @PathVariable int dureeinter) {
        interserv.updateintervention( id,dureeinter);

    }
    @PutMapping("/updateintervention2")
    public Intervention updateintervention2 (@RequestBody Intervention  i){
        return interserv.updateintervention2(i);
    }
    @GetMapping("/listecmd/{userid}")
    public List<LigneCommande> listedelgcmddunuser( @PathVariable  Integer userid) {
        return recserv.listedelgcmddunuser(userid);

    }
    @GetMapping("/getnbrinterventionsdechaqueuser")
    public List<String> listedesinterdechaqueuser(){
        return recserv.listedesinterdechaqueuser();
    }
    @PostMapping("/addAndAssignPostLiketoPostAndUser/{idligcmd}")
    public ResponseEntity<?> addandassignlgcmdtouser(@RequestBody Reclamation rec, @PathVariable("idligcmd") Integer idligcmd)  {
        try {
         //   String username = principal.getName();
       //     User u = userRepository.findByUsername(username).orElse(null);
         //   Integer id = u.getId();
Integer id=1;
            return ResponseEntity.ok(new MessageResponse(recserv.ajouterreclamation(rec, idligcmd, id)));
        }catch (Exception e){
           return ResponseEntity.badRequest().body(new MessageResponse("Erreur"));
        }

    }
  //  @PostMapping("/addreclamation/{idligcmd}/{iduser}")
    //public String addreclamation(@RequestBody Reclamation i,@PathVariable Integer idligcmd,@PathVariable Integer iduser ) {return recserv.ajouterreclamation(i,idligcmd,iduser);    }

 @GetMapping("/recuser/{userrr_id}")
        public List<Reclamation> userconeccte(@PathVariable("userrr_id") int userrr_id){
     return recserv.afficherrecdeuserx(userrr_id);
        }

    @DeleteMapping("/reclamationsupprimer/{id}")
    public  void deleterec(@PathVariable("id") Long id ){
        recserv.deleteByIdrec(id);
    }
    @GetMapping("/listereclamations")
    public List<Reclamation> listereclamations(){
        return recserv.listedesreclamations();
    }
    @GetMapping("/listeproduitsimilaires/{idrec}")
    public Set<Product> listeproduits(@PathVariable Long idrec ){
        return  recserv.afficherproduitssimilaires(idrec);

    }
    //@PutMapping("/updaterrecwithquery/{idrec}/{ticketstatus}")
    //public void updaterec1(@PathVariable Long idrec, @PathVariable Statuss ticketstatus) {
     //   recserv.updatereclamation( idrec,ticketstatus);

    //}
    @PutMapping("/updaterecwithrec")
    public Reclamation updatereccc ( @RequestBody Reclamation  i){
        return recserv.updatereclamation2(i);
    }
    @GetMapping("/nombredereclamationlivreur")
    public int nbrrec(Long iduser){
        return   recserv.countReclamation(iduser);
    }

    @GetMapping ("/calculsalaire/{idrec}")
    public Livreur retournesalaire (@PathVariable Long idrec ){
        return   recserv.retournesalaire(idrec);
    }
    //@GetMapping("/listeproduits/{idproduitreclame}")
    //public List<Product> afficherproduitsimilaire(@PathVariable int idproduitreclame){
      //  return recserv.afficherproduitssimilaires(idproduitreclame);
    //}

    @GetMapping("/order")
    public   List<Reclamation> order1(){
        return  recserv.order();
    }
    @GetMapping("/calculdeladatedefindeinter/{idinter}")
    public LocalDate calculerDateFinIntervention(@PathVariable Long idinter){
         return recserv.calculerDateFinIntervention(idinter);

    }
    @GetMapping("/nbrdemotsdanspriorite/{idrec}")
    public String nbrdemotsdanspriorite(@PathVariable Long idrec){
        return recserv.compteurdenrbdemots(idrec);

    }
    @GetMapping("/affetcteruserrec/{idrec}/{iduser}")
    public void nbrdemotsdanspriorite(@PathVariable Long idrec,@PathVariable Integer iduser){
         recserv.affecetruserlreclamation(idrec,iduser);

    }

    @GetMapping("/nbrderecpourchaqueproduit")
    List<String> nbrrecpourchaqueproduit(){
        return recserv.nombredereclamationpourchaqueproduit();
    }
    @GetMapping("/nombredereclamationsproduit/{description}/{id}")
    Integer nombredereclamationdunproduit(@PathVariable Sujetrec description,@PathVariable Integer id){
        return recserv.nombredereclamationdunproduit(description,id);
    }
    @GetMapping("/prixproduits/{description}/{idprodrec}")
   public  Product prixproduit (@PathVariable Sujetrec description,@PathVariable Integer idprodrec){
      return   recserv.prixproduit(  description, idprodrec);
   }
    @GetMapping("/listemotspositifs/{filePath}")
    public  Set<String> readExcellistemotspositifs(String filePath) throws IOException {
        return recserv.readExcel(filePath);
    }
    @GetMapping("scoresatisfaction/{filepath}/{filepathneutre}/{filepathnegatifs}")
    public  List<String> retournescoredesatisfactionclient1(String filepath,String filepathneutre,String filepathnegatifs) throws IOException {
        return   recserv.retournescoredesatisfactionclient(filepath,filepathneutre,filepathnegatifs);
    }

    @GetMapping("lemeilleuremployebrusque")
    public User   lemeilleureemployedeumois(){
        return   recserv.lemeilleureemployedeumois();

    }
    @GetMapping("returnbyid/{idrec}")
    public Reclamation reurnbyid(@PathVariable Long idrec){
        return recserv.returnrecbyid(idrec);
    }
    @GetMapping("affecterinterrec/{idinterv}/{idrec}")
    public void affecter(@PathVariable Long idinterv,@PathVariable Long idrec){
        recserv.affecterinterventionareclamation(idinterv,idrec);
    }
    @GetMapping("affecterlgcommandetoreclamation/{idligcmd}/{idrec}")
    public void affecterlg(@PathVariable Integer idligcmd,@PathVariable Long idrec){
        recserv.affecterlignecommandereclamation(idligcmd,idrec);
    }

    @GetMapping("affecteradminto/{iduser}/{idinter}")
    public void affecteradmininter(@PathVariable Integer iduser ,@PathVariable Long idinter){
        interserv.affecteradminintervention(iduser,idinter);
    }


}
