package com.example.marketplace.entities;

import com.example.marketplace.enumerations.Statut_livreur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Livreur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idLivreur;
    @Temporal(TemporalType.DATE)
    private Date datedembauche;
    float salaire;
//   float bonus;
   // float penaliser;
    String latitude;
    String longitude;
    Integer Nbrelivraison;
    String origin;
    String destination;
    @Enumerated(EnumType.STRING)
    Statut_livreur statutlivreur;


    @OneToMany(mappedBy="livreur")
    @JsonIgnore
    private List<Livraison> livraisons ;

    @OneToOne
    @JsonIgnore
    private User user;



}
