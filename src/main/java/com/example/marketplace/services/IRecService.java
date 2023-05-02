package com.example.marketplace.services;

import com.example.marketplace.entities.*;
import com.example.marketplace.enumerations.Statuss;
import com.example.marketplace.enumerations.Sujetrec;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IRecService {
    String ajouterreclamation(Reclamation r ,Integer idligcmd, Integer iduser);
    List<String> listedesinterdechaqueuser();
    void  affecetruserlreclamation (Long idrec,Integer iduser);
    List<Reclamation> afficherrecdeuserx(Integer userrr_id);
    void deleteByIdrec(Long id);
    List<LigneCommande> listedelgcmddunuser(Integer userid);
    List<Reclamation> listedesreclamations();

    void updatereclamation(Long idrec, Statuss ticketstatus);
    Reclamation updatereclamation2 ( Reclamation  i);
    int countReclamation (Long iduser);
    Livreur retournesalaire (Long idrec) ;
   // void desactiveruser (Long iduser2);
    Set <Product> afficherproduitssimilaires (Long idrec);

    List<Reclamation> order();
    LocalDate calculerDateFinIntervention(Long idinter);
    String compteurdenrbdemots (Long idrec);
    Product prixproduit (Sujetrec description,Integer idprodrec);

    List<String> nombredereclamationpourchaqueproduit();

    Integer nombredereclamationdunproduit(Sujetrec description, Integer id);
   Set<String> readExcel(String filePath) throws IOException;
    List<String> retournescoredesatisfactionclient (String filepath, String filepathneutre,String filepathnegatifs)throws IOException ;
    User  lemeilleureemployedeumois();
    void  affecterinterventionareclamation(Long idinterv,Long idrec);
    void  affecterlignecommandereclamation (Integer idligcmd,Long idrec);
    public Reclamation returnrecbyid(Long idrec);
}
