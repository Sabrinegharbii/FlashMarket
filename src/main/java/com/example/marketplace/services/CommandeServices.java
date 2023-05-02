package com.example.marketplace.services;

import com.example.marketplace.entities.*;
import com.example.marketplace.enumerations.Categorie;
import com.example.marketplace.repository.*;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@AllArgsConstructor
public class CommandeServices implements ICommandeServices{
    private static final int SCORE_COLUMN_INDEX =4;
    final ILigneCommandeRepo ligneCommandeRepo;
    final IUserRepository userRepository;
    final IProductRepo productRepo;
    final ISoldeDonnation soldeDonnation;

    final IFactureCommandeRepo factureCommandeRepo;
    private final ICommandeRepo commandeRepo;
    //private final ICharityRepo charityRepo;
    @Override
    public List<Commande> getAllCommande(){
        List<Commande> p = new ArrayList<>();
        commandeRepo.findAll().forEach(p::add);
        return p;
    }

    @Override
    public Commande addCommande(Commande commande, Integer iduser, Integer idligne) {
        LigneCommande ligneCommande=ligneCommandeRepo.findById(idligne).orElse(null);
        User user=userRepository.findById(iduser).orElse(null);
        Commande c= commandeRepo.save(commande);
        ligneCommande.setCommande(c);

        ligneCommandeRepo.save(ligneCommande);
        //donn.setCommandes((Set<Commande>) c);

        FactureCommande f = new FactureCommande();
        f.setCommande(c);
        f.setDateCommande(new Date());
            if(ligneCommande.getPaniers().getPrixTotal()>200){
            f.setPrixFacture(ligneCommande.getPaniers().getPrixTotal()*ligneCommande.getPaniers().getRemise()*ligneCommande.getQuantiteProduit()-ConverPointsFidelite(user.getId()));
            }
              else{
            f.setPrixFacture(ligneCommande.getPaniers().getPrixTotal()*ligneCommande.getQuantiteProduit());
                  }
        factureCommandeRepo.save(f);

      if(f.getPrixFacture()!=0){
          System.out.println("traitement des points fidelité");
                        ///CarteFidelité
             //user.setPointsFidelite((int) commande.getFactureCommandes().getPrixFacture())
           float points = f.getPrixFacture();
           int y=(int)points;
          System.out.println("points fidelités ajoutés avec succes !");
          user.setPointsFidelite(y);
                       // user.setPointsFidelite((int) f.getPrixFacture());
        userRepository.save(user);
        commandeRepo.save(c);}
        return c;
    }


    @Override
    public Integer ConverPointsFidelite(Integer user) {
       User user1=userRepository.findById(user).orElse(null);
        System.out.println(user1.getPointsFidelite()%20);
       if(user1==null){
           return null;
       }
        //System.out.println("3");

        Integer point= (int) user1.getPointsFidelite();
        Integer reduction = point % 20;
        if(point>=200) {

            System.out.println("felicitation ! vous avez gagner :" + user1.getPointsFidelite() % 20 + "Dinar Bonus suite à la onvertion de vos points merci !");
        }
        if(point <200){
            System.out.println("Vous  ne pouvez convertir vos points qu'à partir de 200 points ou bien une commande qui depasse 200dt");
            return 0;

        }
        return reduction;
    }


@Override
    public int afficherPointsFidelite(Integer idu) {
         User user=userRepository.findById(idu).orElse(null);
        return (int) user.getPointsFidelite();
    }
    @Override
    public Commande addandaffectcommandeToCharity(Commande commande,Integer idCharity,Integer idLigne){
        LigneCommande ligneCommande=ligneCommandeRepo.findById(idLigne).orElse(null);
      // Charity charity=charityRepo.findById(idCharity).orElse(null);
         //charity.setCommande(commande);
        Commande c= commandeRepo.save(commande);
        FactureCommande f = new FactureCommande();
        f.setDateCommande(new Date());
        if(ligneCommande.getPaniers().getPrixTotal()>200){
            f.setPrixFacture(ligneCommande.getPaniers().getPrixTotal()*ligneCommande.getPaniers().getRemise()*ligneCommande.getQuantiteProduit());
        }
        else{
            f.setPrixFacture(ligneCommande.getPaniers().getPrixTotal()*ligneCommande.getQuantiteProduit());
        }

        c.setFactureCommandes(f);
        factureCommandeRepo.save(f);
        commandeRepo.save(c);

        return c;
    }

    @Override
    public void removeCommande(Integer id) {
    commandeRepo.deleteById(id);

    }

    @Override
    public Commande retrieveCommande(Integer id) {
        return commandeRepo.findById(id).orElse(null);
    }
    @Override
    public Commande updateCommande(Commande commande) {
        return commandeRepo.save(commande);
    }




    public List<List<String>> readExcelcharitylist(String filePath) throws IOException {

        List<List<String>> excelData = new ArrayList<>();

        FileInputStream inputStream = new FileInputStream(filePath);

        Workbook workbook = new XSSFWorkbook(inputStream);

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

            Sheet sheet = workbook.getSheetAt(i);

            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {

                List<String> rowData = new ArrayList<>();

                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.iterator();

                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case STRING:
                            rowData.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            rowData.add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case BOOLEAN:
                            rowData.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        default:
                            rowData.add("");
                    }
                }

                excelData.add(rowData);
            }
        }

        workbook.close();
        inputStream.close();

        return excelData;

    }


    ///produit similaires
    @Override
    public List<Product> similarProduct(Integer idprodSimilar, Integer idC) {


        Product similar = productRepo.findById(idprodSimilar).orElse(null);
        Commande c=commandeRepo.findById(idC).orElse(null);
        LocalDate currentDate = LocalDate.now();
        LocalDate dateCommande=new java.sql.Date(c.getDateCommande().getTime()).toLocalDate();
        // Convertir la date de la commande en objet LocalDate

        long differenceInMonths = ChronoUnit.MONTHS.between(dateCommande, currentDate);

        double prixsimilar = similar.getPrice();
        /*declaration*/
        double bornInf=prixsimilar-20;
        double bornSup=prixsimilar+15;
        Categorie categorieSimilar = similar.getCategorie();
        List<Product> prod = new ArrayList<>();
        productRepo.findAll().forEach(prod::add);
        List<Product> addsimilarProduct = new ArrayList<>();
        for (Product product : prod) {
            ///njareb nshouf lazm tkoun f ekher 3 mois l commande
            if(differenceInMonths <= 3){
            if (product.getId() != similar.getId() && product.getCategorie().equals(categorieSimilar) &&(product.getPrice()>=bornInf || product.getPrice()<=bornSup )) {

                addsimilarProduct.add(product);
            }}
        }
        return addsimilarProduct;
    }



}



//        if (user.getPointsFidelite() >= 200) {//kol 200 points aandhom 40 euro
//            user.setPointsFidelite(user.getPointsFidelite() - 200);
//            user.getPanier().setBonus((int) (user.getPanier().getPrixTotal()- 40)); // Ajouter un bonus de 40€
//            userRepository.save(user);
//            return true;
//        } else {
//            return false; // Retourner false si l'utilisateur n'a pas suffisamment de points pour obtenir un bonus
//        }


///Excel Charity
//    public int calculateScore(List<String> rowData) {
//        int score = 0;
//        for (int i = 0; i < rowData.size(); i++) {
//            if (i != SCORE_COLUMN_INDEX) { // exclure la colonne score elle-même
//                String cellValue = rowData.get(i);
//                if (cellValue != null && !cellValue.isEmpty()) {
//                    try {
//                        score += Integer.parseInt(cellValue);
//                    } catch (NumberFormatException e) {
//                        // ignorer les valeurs non numériques
//                    }
//                }
//            }
//        }
//        return score;
//    }