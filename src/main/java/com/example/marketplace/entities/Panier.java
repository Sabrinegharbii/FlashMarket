package com.example.marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Panier {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idPanier;
    float prixTotal;

    LocalDateTime datePanier=LocalDateTime.now();;
    @Temporal(TemporalType.DATE)
    Date dateExpirationPanier;

    float remise=0.8f;
    int Bonus;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paniers")
    Set<LigneCommande> ligneCommandes;
    @JsonIgnore
    @OneToOne
    User user;
}
