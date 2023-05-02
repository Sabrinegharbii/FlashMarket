package com.example.marketplace.repository;

import com.example.marketplace.entities.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ILigneCommandeRepo extends JpaRepository<LigneCommande,Integer> {
    List<LigneCommande>findAllByPaniersUserId(Integer userid);
}
