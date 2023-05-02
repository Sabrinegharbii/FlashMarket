package com.example.marketplace.services;

import com.example.marketplace.entities.Panier;
import com.example.marketplace.entities.Product;

import java.util.List;

public interface IPanierServices {

//    Panier addPanierandaffectoUser(Panier panier, Integer IdUser); hedhy !!!
Panier addPanierandaffectoUser(Panier panier,Integer id);


    void addProductToPanier(Integer IdPanier, Integer IdProduct, Integer quantiteProduit);

    Panier updatePanier(Panier panier);

    Panier retrievePanier(Integer id);

    void removePanier(Integer id);
    public void romoveListPanier();
    Panier findPanier(Integer id);

    List<Product> similarProduct(Integer idprodSimilar, Integer idC);
}
