package com.example.marketplace.services;

import com.example.marketplace.entities.Intervention;

import java.util.List;

public interface IInterventionServ {
    String ajouterintervention(Integer idadmin,Intervention i);

    void deleteByIdd(Long id);

    List<Intervention> listedesinetrventions();

    void updateintervention( Long id,Integer dureeinter);
    Intervention updateintervention2 ( Intervention  i);
    void affecteradminintervention(Integer idadmin,Long idinter);
}
