package com.example.marketplace.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private Integer id;


    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;
    private Integer nbrpoints;
    private Integer pointsFidelite;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();
    String cinUser;
    String phoneNumber;
    Boolean status;
    String photo;

    String code;

    LocalDateTime createdAt;
    /////////////////////MARKET///////////////
    @JsonIgnore
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    Market market;
    ////////////////FRRUM///////////////////

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    Set<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    Set<PostLike> postLikes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    Set<Post> posts;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    Set<CommentLike> commentLikes;
    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "reported")
    Set<Post> PostsSignale;
    Integer ban=0;
    LocalDateTime banTime;
    ////////////////Product///////////////////

    ///////////Panier/////////////
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    Panier panier;
//////reclamation//////
    @OneToMany(mappedBy="userrr",cascade = CascadeType.ALL)
    Set<Reclamation> userrec;
////intervention /////

@OneToMany(mappedBy="userrrr",cascade = CascadeType.ALL)
Set<Intervention> interuser;

///Livreur/////

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Livreur livreur;

    @ManyToMany(mappedBy = "users" ,cascade = CascadeType.ALL)
    Set<Rating> ratings;



}
