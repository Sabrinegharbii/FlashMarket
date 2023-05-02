package com.example.marketplace.entities;

import com.example.marketplace.enumerations.Categorie;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Float tauxRemise;
    private String Description;

    private double price;
    private String images;


    private LocalDate dateExpiration;

    @JsonIgnore
    private Integer QtySold;

    private String name;
    private Integer Nutriscore;

    @Enumerated(EnumType.STRING)
    private Categorie categorie;

    @JsonIgnore
    @ManyToOne
    Market market;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    private Set<LigneCommande>  ligneCommandes  ;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "product")
    Image image;




    @Column(name = "quantity")
    private Integer quantity;
    @JsonIgnore
    @ManyToMany(mappedBy = "products" ,cascade = CascadeType.ALL)
    Set<Rating> ratings;


}