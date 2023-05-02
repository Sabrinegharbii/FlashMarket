package com.example.marketplace.services;

import com.example.marketplace.entities.Commande;
import com.example.marketplace.entities.Product;
import com.example.marketplace.entities.User;

//import javax.jnlp.IntegrationService;
import java.io.IOException;
import java.util.List;

public interface ICommandeServices {
    Commande addCommande(Commande commande, Integer iduser,Integer idligne);

    //private final ICharityRepo charityRepo;
    //Commande addCommande(Commande commande, Integer idLigne, User user, Integer idDonnation);

    ///
    Integer ConverPointsFidelite(Integer user);

    int afficherPointsFidelite(Integer idu);

    Commande addandaffectcommandeToCharity(Commande commande, Integer idCharity, Integer idLigne);

    void removeCommande(Integer id);


    Commande retrieveCommande(Integer id);

    Commande updateCommande(Commande commande);

    List<List<String>> readExcelcharitylist(String filePath) throws IOException;

    ///produit similaires
    List<Product> similarProduct(Integer idprodSimilar, Integer idC);
}
