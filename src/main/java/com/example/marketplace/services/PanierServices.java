package com.example.marketplace.services;

import com.example.marketplace.entities.*;
import com.example.marketplace.enumerations.Categorie;
import com.example.marketplace.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PanierServices implements IPanierServices{
private final IPanierRepo panierRepo;
private final IProductRepo productRepo;
private final ILigneCommandeRepo ligneCommandeRepo;
private final IUserRepository userRepository;
final ICommandeRepo commandeRepo;

    @Override
    public Panier addPanierandaffectoUser(Panier panier,Integer id) {
        //Panier panier, Integer IdUser

        User user=userRepository.findById(id).orElse(null);
        System.out.println(user.getId());
        if(user.getPanier()==null) {
            panier.setUser(user);
            panier.setRemise(0.2f);
            panier.setPrixTotal(0);
            return panierRepo.save(panier);
        }
        return null;
    }
    /*@Override
    public Panier addAndAssignPanierToUser(Panier panier,Integer Id){
        User user=userRepo.findById(Id).orElse(null);
        user.setPanier(panier);
        return panierRepo.save(panier);
    }*/
   @Override
   public void addProductToPanier(Integer IdPanier, Integer IdProduct, Integer quantiteProduit){
       Panier panier = panierRepo.findById(IdPanier)
               .orElseThrow(() -> new RuntimeException("Panier non trouvé"));
       Product product = productRepo.findById(IdProduct)
               .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
       LigneCommande ligneCommande = panier.getLigneCommandes().stream()
               .filter(lc -> lc.getProducts().equals(product))
               .findFirst()
               .orElse(null);

       if (ligneCommande == null) {
           ligneCommande = new LigneCommande();
           ligneCommande.setProducts((Set<Product>) product);
           ligneCommande.setPaniers(panier);
           panier.getLigneCommandes().add(ligneCommande);
       }

       ligneCommande.setQuantiteProduit(ligneCommande.getQuantiteProduit() + quantiteProduit);
   }



    @Override
    public Panier updatePanier(Panier panier) {
        return panierRepo.save(panier);
    }

    @Override
    public Panier retrievePanier(Integer idPanier) {
        return panierRepo.findById(idPanier).orElse(null);
    }
 @Override
    public Panier findPanier(Integer id) {
        return panierRepo.findPanierByUserId(id);
    }

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

    @Override
    public void removePanier(Integer idPanier) {
        panierRepo.deleteById(idPanier);

    }
    //@Scheduled(cron="*/10 * * * * * ")
    public void romoveListPanier(){
      // Panier panier=new Panier();
     //   LocalDate datePanier = new java.sql.Date(panier.getDatePanier().getTime()).toLocalDate();
       List<Panier> p=panierRepo.listPanier();
        for (int i=0;i<p.size();i++){
            System.out.println(p.get(i).getIdPanier());
            Panier panier=p.get(i);
            p.get(i).setUser(null);
            Integer p1=p.get(i).getIdPanier();
            System.out.println("panier est selecté");

            panierRepo.delete(panier);
            System.out.println("bb");

        }
        //panierRepo.save(p);

    }
}
