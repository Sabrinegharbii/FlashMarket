package com.example.marketplace.services;

import com.example.marketplace.entities.LigneCommande;
import com.example.marketplace.entities.Panier;
import com.example.marketplace.entities.Product;
import com.example.marketplace.entities.User;
import com.example.marketplace.repository.ILigneCommandeRepo;
import com.example.marketplace.repository.IPanierRepo;
import com.example.marketplace.repository.IProductRepo;
import com.example.marketplace.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public Panier addPanierandaffectoUser(Panier panier, Integer IdUser) {

        User user=userRepository.findById(IdUser).orElse(null);
        System.out.println(user.getId());
        if(user.getPanier()==null) {
         //   System.out.println("awel mara");
            panier.setUser(user);
            panier.setRemise(0.2f);
            panier.setPrixTotal(0);
            return panierRepo.save(panier);
        }
    //    System.out.println("deja");
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
