package com.example.marketplace.services;


import com.example.marketplace.entities.*;
import com.example.marketplace.entities.Comment;
import com.example.marketplace.enumerations.Categorie;
import com.example.marketplace.enumerations.Statuss;
import com.example.marketplace.enumerations.Sujetrec;
import com.example.marketplace.repository.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class RecService implements  IRecService {
    @Autowired
    IRecrepo recrepo;
    @Autowired
    IProductServ prodserv;
    @Autowired
    IProductRepo prodrepo;
    @Autowired
    ILivreurRepository livrepo;
    @Autowired
    IinterventionRepo interrepo;

    @Autowired
    IUserRepository userrepo;
    @Autowired
    CommentServ mdserv;
    @Autowired
    ICommentRepo commentrepo;
    @Autowired
    InterventionServ interserv;
    @Autowired
    UserService userserv;
    @Autowired
    ILigneCommandeRepo lgrepo;
    @Autowired IUserRepository userRepository;
    @Override
    public String ajouterreclamation(Reclamation i ,Integer idligcmd,Integer iduser) {

User u =userrepo.findById(iduser).orElse(null);
            LigneCommande lgcmd=lgrepo.findById(idligcmd).orElse(null);
            i.setLgcommande(lgcmd);
i.setUserrr(u);
         recrepo.save(i);
         String lg ="added";
         return lg ;
    }
public List<LigneCommande> listedelgcmddunuser(Integer userid){
        return lgrepo.findAllByPaniersUserId(userid);
}
    @Override
    public void deleteByIdrec(Long id) {
        recrepo.deleteById(id);
    }

    @Override
    public List<Reclamation> listedesreclamations() {
        return recrepo.findAll();
    }
 @Override
    public List<Reclamation> afficherrecdeuserx(Integer userrr_id) {
     User user = userRepository.findById(userrr_id).orElse(null);
     List<Reclamation> p = new ArrayList<>();
     List<Reclamation> rectotal = listedesreclamations();
     for (Reclamation recc : rectotal) {
         if (recc.getUserrr().getId().equals(userrr_id)) {
             p.add(recc);
         }

     }
     return rectotal;

 }
    @Override
    public void updatereclamation(Long idrec, Statuss ticketstatus) {
        recrepo.updaterecc(idrec, ticketstatus);
    }

    @Override
    public Reclamation updatereclamation2( Reclamation i) {
        Reclamation reclamationexistante = recrepo.findById(i.getIdrec()).orElse(null);
        User user = reclamationexistante.getUserrr();
LigneCommande lg=reclamationexistante.getLgcommande();
        // Extraire la valeur actuelle de l'ID d'utilisateur


        // Créer un nouvel objet d'intervention avec les valeurs modifiées
        Reclamation Reclamationmodifie = new Reclamation();
        Reclamationmodifie.setIdrec(reclamationexistante.getIdrec());

        Reclamationmodifie.setDescription(i.getDescription());
        Reclamationmodifie.setTyperec(i.getTyperec());
        Reclamationmodifie.setTicketstatus(i.getTicketstatus());
        Reclamationmodifie.setPriorite(i.getPriorite());
        Reclamationmodifie.setUserrr(user); // Réaffecter la valeur actuelle de l'ID d'utilisateur
        Reclamationmodifie.setLgcommande(lg); // Réaffecter la valeur actuelle de l'ID d'utilisateur

        return recrepo.save(Reclamationmodifie);


    }


@Override
public Reclamation returnrecbyid(Long idrec){
        Reclamation rec =recrepo.findById(idrec).orElse(null);
        return rec ;
}
    /// thsyb les reclamations ta livreur passe en parmetre //////
    ////user houni houa livreur //////
    @Override
    public int countReclamation(Long idlivreur) {
        int a = 0;
        List<Reclamation> amal = listedesreclamations();

        for (Reclamation reclamation : amal) {

            if (reclamation.getDescription().equals(Sujetrec.livreur) && reclamation.getLgcommande().getCommande().getFactureCommandes().getLivraison().getLivreur().getIdLivreur().equals(idlivreur)) {
                a++;
            }
        }
        return a;
    }
    @Override
    public List<String> listedesinterdechaqueuser() {
        List<String> listenombredinterventions = new ArrayList<>();
        List<Intervention> inter = interserv.listedesinetrventions();
        List<User> users = userserv.retrieveAllUsers();
        Map<Integer, Integer> nbInterventionsParUser = new HashMap<>();

        // Calculer le nombre d'interventions pour chaque utilisateur
        for (Intervention intee : inter) {
            int iduserinter = intee.getUserrrr().getId();
            nbInterventionsParUser.put(iduserinter, nbInterventionsParUser.getOrDefault(iduserinter, 0) + 1);
        }

        // Générer une chaîne de caractères pour chaque utilisateur avec le nombre d'interventions
        for (User user : users) {
            int iduser = user.getId();
            if (nbInterventionsParUser.containsKey(iduser)) {
                int nbInterventions = nbInterventionsParUser.get(iduser);
                String ligne = "lenombredinterventiondechaque" + user.getFirstName() + "est:" + nbInterventions;
                listenombredinterventions.add(ligne);
            }
        }
        return listenombredinterventions;
    }









@Override
public List<String> nombredereclamationpourchaqueproduit() {
    List<String> listedesnbrreclamation = new ArrayList<>();
    Set<Integer> idsProduitsTraites = new HashSet<>();
    List<Reclamation> rec = listedesreclamations();

    for (Reclamation recc : rec) {
        {
            Set<Product> id = recc.getLgcommande().getProducts();
            for (Product pr : id) {
                int iddd = pr.getId();
                if (!idsProduitsTraites.contains(iddd)) {
                    Sujetrec description = recc.getDescription();
                    if (description.equals(Sujetrec.produit)) {
                        int nb = nombredereclamationdunproduit(description, iddd);
                        String ligne = "le nombre dereclamation de" +pr.getName()  + " : " + nb;
                        listedesnbrreclamation.add(ligne);
                    }
                    idsProduitsTraites.add(iddd);
                }
            }
        }

    }
    return listedesnbrreclamation;
}
    public Integer nombredereclamationdunproduit(Sujetrec description,Integer id){
        return prodrepo.nombredereclamationdunproduit(description,id);
    }

    public double calculatePercentage(double numerator, int denominator) {
        return (numerator * denominator) / 100;
    }
    // @Override
    // public void desactiveruser (Long iduser2,int duree){

    //int i =0;
    // Optional<Livreur> livreur =livrepo.findById(iduser2);

    //if (livreur.isPresent() ){
    //  Livreur amalfares= livreur.get();
    // amalfares.setActif(false);
    //clrepo.save(amalfares);}}
    @Override
    public Livreur retournesalaire(Long idrec) {
        double salaire = (float) 25.5;
        Reclamation rec=recrepo.findById(idrec).orElse(null);
      Livreur liv=rec.getUserrr().getLivreur();
      Long idlivreur=liv.getIdLivreur();
        if (countReclamation(idlivreur) ==3) {
            salaire = calculatePercentage(liv.getSalaire(), 90);
        } else if (countReclamation(idlivreur) >3) {
            salaire = calculatePercentage(liv.getSalaire(), 80);
        }//else {
        liv.setSalaire((float)salaire);
        livrepo.save(liv);
        //desactiveruser(amalfares);
        return liv;
    }


    @Override
    public Set<Product> afficherproduitssimilaires(Long idrec) {


        Set<Product> p = new HashSet<>();
        prodrepo.findAll().forEach(p::add);
        Set<Product> produitreclame = recrepo.findById(idrec).orElse(null).getLgcommande().getProducts();


        Set<Product> produitajouterprodsimil = new HashSet<>();
        for (Product pr : produitreclame) {
            for (Product product : p) {
                double prixreclame = pr.getPrice();
                Categorie categoriereclame = pr.getCategorie();
                if (product.getId() != pr.getId() && product.getCategorie().equals(categoriereclame) && product.getPrice() == (prixreclame)) {

                    produitajouterprodsimil.add(product);
                }
            }

        }
        return produitajouterprodsimil;
    }




    @Override
    public List<Reclamation> order() {
        return recrepo.orderbypriorite();
    }

    @Override
    public LocalDate calculerDateFinIntervention(Long idinter) {
        Optional<Intervention> inter = interrepo.findById(idinter);
        Intervention intervention = inter.get();
        Date dateDebut = intervention.getDatedebinter();
        Integer duree = intervention.getDureeinter();
        LocalDate localDateDebut = new java.sql.Date(dateDebut.getTime()).toLocalDate();
        LocalDate dateFin = localDateDebut;
        for (int i = 0; i < duree; i++) {
            dateFin = dateFin.plusDays(1);
        }
        return dateFin;
    }

    @Override
    public String compteurdenrbdemots(Long idrec) {
        Optional<Reclamation> rec = recrepo.findById(idrec);
        Reclamation reclamation = rec.get();
        String recc = reclamation.getPriorite();
        String message = "";
        int longueur = recc.split(" ").length;
        if (longueur > 2) {
            return message.concat("la longueur ne doit pas depasser les deux mots ");
        }
        return message.concat("la chaine est validee");
    }

    @Override
    public  Product prixproduit (Sujetrec description,Integer idprodrec) {
        Product produitrec = prodrepo.findById(idprodrec).orElse(null);

            if (nombredereclamationdunproduit(description,idprodrec)>3) {
                produitrec.setPrice(calculatePercentage(produitrec.getPrice(),95));

                prodrepo.save(produitrec);

            }
return produitrec;
        }
    @Override
    public Set<String> readExcel(String filepath) throws IOException {
        Set<String> words = new HashSet<>();
        FileInputStream file = new FileInputStream(filepath);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getCellType() == CellType.STRING) {
                    words.add(cell.getStringCellValue().toLowerCase());
                }
            }
        }
        workbook.close();
        file.close();
        return words;
    }
    public Set<String> readExcelmotsneutres(String filepathneutre) throws IOException {
        Set<String> words = new HashSet<>();
        FileInputStream file = new FileInputStream(filepathneutre);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getCellType() == CellType.STRING) {
                    words.add(cell.getStringCellValue().toLowerCase());
                }
            }
        }
        workbook.close();
        file.close();
        return words;
    }

    public Set<String> readExcelmotsnegatifs(String filepathnegatifs) throws IOException {
        Set<String> words = new HashSet<>();
        FileInputStream file = new FileInputStream(filepathnegatifs);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getCellType() == CellType.STRING) {
                    words.add(cell.getStringCellValue().toLowerCase());
                }
            }
        }
        workbook.close();
        file.close();
        return words;
    }

    @Override
    public List<String> retournescoredesatisfactionclient(String filepath, String filepathneutre, String filepathnegatifs) throws IOException {
        int borneInf = 0;
        int borneSup = 20;
        List<String> result = new ArrayList<>();
        List<Comment> comment = mdserv.getAllComment();
        double moyenne = 0.0;
        int score = 35;
        int scorenegatifs = -92;
        int scoreneutre = 0;
        String message = "";

        // Créer des ensembles pour stocker les mots positifs, neutres et négatifs
        Set<String> motsPositifs = new HashSet<>(readExcel(filepath));
        Set<String> motsNeutres = new HashSet<>(readExcelmotsneutres(filepathneutre));
        Set<String> motsNegatifs = new HashSet<>(readExcelmotsnegatifs(filepathnegatifs));
        Set<Integer> idusertraites = new HashSet<>();
        for (Comment comment1 : comment) {
            String[] chaine = comment1.getText().split(" ");
            for (String ch : chaine) {
                // Vérifier si le mot est positif, neutre ou négatif en utilisant les ensembles
                if (motsPositifs.contains(ch)) {
                    score++;
                } else if (motsNeutres.contains(ch)) {
                    scoreneutre++;
                } else if (motsNegatifs.contains(ch)) {
                    scorenegatifs++;
                }
            }
            int iduser = comment1.getUser().getId();
            moyenne = (score + scoreneutre + scorenegatifs) / 3;
            String Ligne = "";
            if (!idusertraites.contains(iduser)) {
                if (moyenne < 0) {
                    Ligne = "le client " + comment1.getUser().getUsername() + " n est pas satisfait du tout du site et ses services"+"avec une moyenne "+moyenne;
                } else if (moyenne >= borneInf && moyenne <= borneSup) {
                    Ligne = "leclient " + comment1.getUser().getUsername() + " est moyennement satisfait "+"avec une moyenne "+moyenne;
                } else if (moyenne > borneSup) {
                    Ligne = "leclient " + comment1.getUser().getUsername() + " est moyennement satisfait "+"avec une moyenne "+moyenne;
                }
                result.add(Ligne);
            }
            idusertraites.add(iduser);

        }

        return result;
    }


    @Override
    public User lemeilleureemployedeumois(){
        String message="";
        List<User> listedesusers=userserv.retrieveAllUsers();
        List<Intervention> listeinterventions= interserv.listedesinetrventions();
        for (User user : listedesusers){
            int nbrpoint = 0;
            for (Intervention intervention :listeinterventions) {
                LocalDate date = LocalDate.of(2023, 3, 1);
                LocalDate datefindumois = LocalDate.of(2023, 3, 31);
                LocalDate localDateDebut = new java.sql.Date(intervention.getDatedebinter().getTime()).toLocalDate();
                if (intervention.getUserrrr().getId().equals(user.getId()) && localDateDebut.isAfter(date) ) {
                    nbrpoint=nbrpoint+1;
                }
            }
            user.setNbrpoints(nbrpoint);
            userrepo.save(user);

        }

        User pp = new User();
      String  messagemeilleur="";
        int max=0;
        for ( User user :listedesusers) {
           int nbrpoint=user.getNbrpoints();
               if(nbrpoint>max){
                   max=nbrpoint;

                   messagemeilleur=user.getFirstName()+ "avec le cin suivant"
                           +user.getCinUser()+"avec l 'email suivant "+user.getEmail()+"avec le numero de telepone corresponsant"+user.getPhoneNumber()+"est le meilleur";
            }
            pp =user;
        }


        return pp;
        }
    @Override
    public void  affecterinterventionareclamation(Long idinterv,Long idrec){
        Intervention inter= interrepo.findById(idinterv).orElse(null);
        Reclamation rec=recrepo.findById(idrec).orElse(null);
        rec.setIntervention(inter);
        recrepo.save(rec);
    }
   @Override
    public void  affecterlignecommandereclamation (Integer idligcmd,Long idrec){
        Reclamation rec=recrepo.findById(idrec).orElse(null);
        LigneCommande lgcmd=lgrepo.findById(idligcmd).orElse(null);
        rec.setLgcommande(lgcmd);
        recrepo.save(rec);
    }
    @Override
    public void  affecetruserlreclamation (Long idrec,Integer iduser){
        Reclamation rec=recrepo.findById(idrec).orElse(null);
        User user=userRepository.findById(iduser).orElse(null);
        rec.setUserrr(user);
        recrepo.save(rec);
    }


    }

