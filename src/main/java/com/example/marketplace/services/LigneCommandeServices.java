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

import java.util.*;

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
         }

    @Override
    public List<LigneCommande> retrieveLigneCommandeByPanier(Integer id) {
        Panier panier=panierRepo.findById(id).orElse(null);
        List<LigneCommande> ligneCommandes= new ArrayList<>();
        for(LigneCommande l :ligneCommandeRepo.findAll()){
            if (l.getPaniers().getIdPanier()==id){
                ligneCommandes.add(l);
            }
        } return ligneCommandes;

    }

    @Override
    public void removeLigneCommande(Integer id) {
        ligneCommandeRepo.deleteById(id);
    }


}
