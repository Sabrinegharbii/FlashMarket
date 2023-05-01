package com.example.marketplace.entities;

import com.example.marketplace.enumerations.Statut_livraison;
import com.example.marketplace.enumerations.Type_livraison;
import com.example.marketplace.enumerations.description;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idLivraison;
    String nomdestinataire;
    String prenomdestinataire;
    String adresse;
    Integer numerotel;
    String mail;

    @Temporal(TemporalType.DATE)
    private Date dateLivraison;
 //   float latitude;
 //   float longitude;


    @Enumerated(EnumType.STRING)
    Type_livraison typelivraison;
    @Enumerated(EnumType.STRING)
    Statut_livraison statutlivraison;
    @Enumerated(EnumType.STRING)
    description descpriton;


    @ManyToOne
    @JsonIgnore
    Livreur livreur ;

    @OneToOne
    @JsonIgnore
    FactureCommande facture;

}
