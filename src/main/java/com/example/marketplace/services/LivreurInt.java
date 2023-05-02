package com.example.marketplace.services;


import com.example.marketplace.entities.Livreur;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public interface LivreurInt extends Serializable {
    List<Livreur> retrieveAllLivreur();
    Livreur saveLivreur(Livreur livreur);
    Livreur getLivreurById(Long idlivreur);
    Livreur updateLivreur(Long idlivreur, Livreur livreurDetails);
    public void deletelivreur(Long idlivreur);
    public List<Livreur> getLivreurDispo();
   // public int countLivraison(Long idlivreur) ;
   public int nbredelivraison(Long idLivreur);

    public int getdatedembauche(Long idLivreur) ;
    public void ajouterunbonus(Long idLivreur);


        ////////////////////
    public double calculateDistance(String origin, String destination) throws ApiException, InterruptedException, IOException ;

    public void retournestatut(String x2,String y2) throws IOException;
    public void ajouterunbonusencasdelivadd(Long idLivreur);

    public Livreur lemeilleureemployedeumois();
    public Livreur ajouterlivreur(Livreur livreur );
    }
