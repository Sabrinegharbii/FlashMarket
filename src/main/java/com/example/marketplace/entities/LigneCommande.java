package com.example.marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LigneCommande {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idLigneCommande;
    Integer quantiteProduit;
    @JsonIgnore

    @ManyToOne
    Commande commande;
@JsonIgnore
    @ManyToOne
    Panier paniers;
    @ManyToMany
    private Set<Product> products ;

/////reclamation////
    @OneToMany(mappedBy="lgcommande")

                    Set<Reclamation> reclgcommande;

    @JsonIgnore
    private Set<Reclamation> reclgcommande;



}