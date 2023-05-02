package com.example.marketplace.services;

import com.example.marketplace.entities.Intervention;
import com.example.marketplace.entities.Reclamation;
import com.example.marketplace.entities.User;
import com.example.marketplace.repository.IRecrepo;
import com.example.marketplace.repository.IUserRepository;
import com.example.marketplace.repository.IinterventionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterventionServ  implements  IInterventionServ{
    @Autowired
    IinterventionRepo interrepo;

    @Autowired
    IRecrepo recrepo;
    @Autowired
    IUserRepository userRepository;
    @Override
    public String ajouterintervention(Integer idadmin,Intervention i ){
        User admin= userRepository.findById(idadmin).orElse(null);
        i.setUserrrr(admin);
        interrepo.save(i);
        String lf="added";
        return lf;
    }
    @Override
    public void deleteByIdd(Long id) {
        interrepo.deleteById(id);
    }

    @Override
    public List<Intervention> listedesinetrventions(){
        return interrepo.findAll();
    }
    @Override
    public  void updateintervention(Long id,Integer dureeinter){
        interrepo.updateintervention(id,dureeinter);}
    @Override
    public Intervention updateintervention2 ( Intervention  i) {
        Intervention interventionExistante = interrepo.findById(i.getId()).orElse(null);
        User user = interventionExistante.getUserrrr();

            // Extraire la valeur actuelle de l'ID d'utilisateur


            // Créer un nouvel objet d'intervention avec les valeurs modifiées
            Intervention interventionModifiee = new Intervention();
        interventionModifiee.setId(interventionExistante.getId());

            interventionModifiee.setDescription(i.getDescription());
            interventionModifiee.setDatedebinter(i.getDatedebinter());
            interventionModifiee.setDureeinter(i.getDureeinter());
            interventionModifiee.setUserrrr(user); // Réaffecter la valeur actuelle de l'ID d'utilisateur

            return interrepo.save(interventionModifiee);


    }
    @Override
    public void affecteradminintervention(Integer idadmin,Long idinter){
        Intervention inter=interrepo.findById(idinter).orElse(null);
        User admin= userRepository.findById(idadmin).orElse(null);
        inter.setUserrrr(admin);
        interrepo.save(inter);

    }
}
