package com.example.marketplace.entities;

import com.example.marketplace.enumerations.Statuss;
import com.example.marketplace.enumerations.Sujetrec;
import com.example.marketplace.enumerations.typerecl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Reclamation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idrec;
    @Enumerated(EnumType.STRING)
    private typerecl typerec;
    @Enumerated(EnumType.STRING)
    private Sujetrec description;
    @Enumerated(EnumType.STRING)
    private Statuss ticketstatus ;
    @Temporal(TemporalType.DATE)
    private Date  createdDate;
    private String priorite;
        private String reponsealareclamation  ;
    @ManyToOne
    @JsonIgnore
    User userrr;
    @ManyToOne
    @JsonIgnore
    LigneCommande lgcommande;


    @ManyToOne
    @JsonIgnore
    Intervention intervention;


}
