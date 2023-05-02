package com.example.marketplace.entities;

import com.example.marketplace.enumerations.PackType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contract implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idContract;
    Integer price;
    Integer nbrMonths;
    Integer discount;
    Boolean paid;
    @Enumerated(EnumType.STRING)
    PackType packType;
    LocalDateTime dateDebutContract;
    LocalDateTime dateFinContract;
    @OneToOne
    private Market market;
}
