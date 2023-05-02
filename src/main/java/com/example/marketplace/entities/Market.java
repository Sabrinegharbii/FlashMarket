package com.example.marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Market implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idMarket;
    String marketName;
    String immatriculation;
    String marketAddress;
    String phoneNumber;
    @JsonIgnore
    @OneToOne(mappedBy = "market", cascade = CascadeType.ALL)
    private Contract contract;

    @OneToOne
    private User user;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="market",fetch = FetchType.EAGER)
    private Set<Product> product;
}
