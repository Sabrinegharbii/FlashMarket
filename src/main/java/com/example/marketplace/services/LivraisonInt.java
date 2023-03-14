package com.example.marketplace.services;


import com.example.marketplace.entities.Livraison;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public interface LivraisonInt extends Serializable {
    List<Livraison> retrieveAllLivraison();
   // Livraison saveLivraison(Livraison livraison);

    //AjoutLivraison
    Livraison saveandaffectLivreurtoLivraison(Livraison livraison, Long idLivreur);

    Livraison getLivraisonById(Long idlivraison);
    Livraison updateLivraison(Long idlivraison, Livraison livraisonDetails);
    void deleteLivraison(Long idlivraison);

    Livraison tst(Long id , Livraison lv);

    public double calculerPrixTotal(Integer id);
    public Livraison ajouterlivraison(Livraison livraison );
    byte[] generateQRCode(Long idCampsite) throws WriterException, IOException;
}
