package com.example.marketplace.repository;

import com.example.marketplace.entities.Panier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
public interface IPanierRepo extends CrudRepository<Panier,Integer> {
//CURRENT_DATE
    @Query("select p from Panier p where p.datePanier+hour(48)<CURRENT_DATE")
     public List<Panier> listPanier();


Panier findPanierByUserId(Integer userId);

//    @Query("select count(p.prixTotal) from Panier p where p.idPanier = :IdP")
//    public float prixPanier(Integer IdP);
}
