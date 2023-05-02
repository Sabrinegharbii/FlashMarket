package com.example.marketplace.services;

import com.example.marketplace.entities.LigneCommande;
import com.example.marketplace.entities.Panier;
import com.example.marketplace.entities.Product;
import com.example.marketplace.repository.ICommandeRepo;
import com.example.marketplace.repository.ILigneCommandeRepo;
import com.example.marketplace.repository.IPanierRepo;
import com.example.marketplace.repository.IProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class LigneCommandeServices implements ILigneCommandeServices {
    private final ILigneCommandeRepo ligneCommandeRepo;
    private final IProductRepo productRepo;
    private  final IPanierRepo panierRepo;
    private final ICommandeRepo commandeRepo;
    @Override

    public LigneCommande retrieveLigneCommande(Integer id) {
        return ligneCommandeRepo.findById(id).orElse(null);
    }
public List<LigneCommande> listeligencommande(){
        return ligneCommandeRepo.findAll();
}
    @Override
    public LigneCommande updateLigneCommande(LigneCommande ligneCommande) {
        return ligneCommandeRepo.save(ligneCommande);
    }

    @Override
    public LigneCommande affecterpanierAndProductlignedecommande(LigneCommande ligneCommande, Integer idpanier, Integer idProduct) {
        Panier panier1 =panierRepo.findById(idpanier).orElse(null);
        //Commande commande=commandeRepo.findById(idCommande).orElse(null);
        Product product1=productRepo.findById(idProduct).orElse(null);
       // LigneCommande ligneCommande=ligneCommandeRepo.findById(idligneCommande).orElse(null);
       // ligneCommande.setCommande(commande);
        ligneCommande.setPaniers(panier1);
        if(ligneCommande.getProducts()==null){
            System.out.println("first time");
            Set<Product> pr=new HashSet<>();
            pr.add(product1);
            ligneCommande.setProducts(pr);
        }else{
            System.out.println("exist");
            ligneCommande.getProducts().add(product1);
        }
        Date d=new Date();
      panier1.setDateExpirationPanier(d);
      panier1.setPrixTotal((float) (panier1.getPrixTotal()+ product1.getPrice()));
        return ligneCommandeRepo.save(ligneCommande);





//        Set<Product> products=new HashSet<>();
     //
//        ligneCommande.setProducts(products);
//
//        ligneCommande.setPaniers(panier1);
//        Date d=new Date();
//        panier1.setDateExpirationPanier(d);
        //Commande commande1=commandeRepo.findById(ligneCommande.getCommande().getIdCommande()).orElse(null);
        //ligneCommande.setCommande(commande1);


    }
}
