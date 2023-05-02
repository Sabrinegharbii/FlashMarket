package com.example.marketplace.entities;

import com.example.marketplace.enumerations.action;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Intervention implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Integer dureeinter;
    @Enumerated(EnumType.STRING)
    private action description ;
    @Temporal(TemporalType.DATE)
    private Date datedebinter;


    @OneToMany(mappedBy="intervention",cascade = CascadeType.ALL)
@JsonIgnore
    Set<Reclamation> recinter;

    @ManyToOne
@JsonIgnore
    User userrrr;
}
