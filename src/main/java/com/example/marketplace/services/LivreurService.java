package com.example.marketplace.services;

import com.example.marketplace.entities.Livraison;
import com.example.marketplace.entities.Livreur;
import com.example.marketplace.repository.ILivraisonRepository;
import com.example.marketplace.repository.ILivreurRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.Distance;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.TravelMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class LivreurService implements LivreurInt{
    //@Value("${google.maps.apikey}")
   // private String apiKey;
    @Autowired
    LivraisonService livraisonService;
    @Autowired
    ILivraisonRepository livraisonRepository;
    @Autowired
    ILivreurRepository livreurRepository;
    @Autowired
    ServiceMapbox serviceMapbox;

    //Affichage
    @Override
    public List<Livreur> retrieveAllLivreur() {
        List<Livreur> livreurs = livreurRepository.findAll();
        return livreurs;
    }
    //Ajout
    @Override
    public Livreur saveLivreur(Livreur livreur) {
        return livreurRepository.save(livreur);
    }

    //Afficher grace l id
    @Override
    public Livreur getLivreurById(Long idlivreur) {
        return livreurRepository.findById(idlivreur)
                .orElse(null);
    }

    //ModifierLivraison
    @Override
    public Livreur updateLivreur(Long idlivreur, Livreur livreurDetails) {
        Livreur livreur = getLivreurById(idlivreur);
        livreur.setSalaire(livreurDetails.getSalaire());
        return livreurRepository.save(livreur);
    }

    //Supprimerlivreur
    @Override
    public void deletelivreur(Long idlivreur) {
        Livreur livreur = getLivreurById(idlivreur);
        livreurRepository.delete(livreur);
    }

    @Override
    public List<Livreur> getLivreurDispo() {
        return livreurRepository.GetLivreur();
    }

   /* @Override
    public int countLivraison(Long idlivreur) {
        int a = 0;

        List<Livraison> listeliv = livraisonRepository.findAll();

        for (Livraison livraison : listeliv) {
            if (livraison.getLivreur().getIdLivreur()==null ){
                return a;
            }
            if (livraison.getLivreur().getIdLivreur().equals(idlivreur) ) {
                a++;
            }
        }
        return a;
    }*/
    @Override
    public int nbredelivraison(Long idLivreur){
        return livreurRepository.nbredelivraisonq(idLivreur);
    }

    @Override
    public Livreur ajouterlivreur(Livreur livreur ) {
        return livreurRepository.save(livreur);
    }
    @Override
    public void retournestatut(String x2,String y2) throws IOException  {
        try {


            double distance = 10000;
            long idlivreur = 0;
            String Longitude = "";
            String Latitude = "";

            for (Livreur liv : livreurRepository.findAll()) {
                Longitude = liv.getLongitude();
                Latitude = liv.getLatitude();
                //distance=distanceLiv(Longitude.toString(),Latitude.toString(),x2,y2);
                if (liv.getLongitude() == null || liv.getLatitude() == null) {
                    System.out.println("nullllllll");
                }
                //    if(distance<distanceLiv(liv.getLongitude(),Longitude,x2,y2)&&liv.getStatutlivreur().toString().equals("Disponible")){
                distance = distanceLiv(liv.getLongitude(), liv.getLatitude(), x2, y2);
                idlivreur = liv.getIdLivreur();
                //   }
            }
            System.out.println(idlivreur);
       /* Livreur livreur=livreurRepository.findById(idlivreur).orElse(null);
        if (nbredelivraison(idlivreur)>6) {
            livreur.setStatutlivreur(Statut_livreur.Nondisponible);
            System.out.println("non");
            livreurRepository.save(livreur);

        }else {
            livreur.setStatutlivreur(Statut_livreur.Disponible);
            System.out.println("dispo");*/
        }
        catch (IOException exception){
            System.out.println(exception);
        }
        }





    public double distanceLiv(String x1 , String y1 , String x2 , String y2) throws IOException {
        URL url = new URL("https://api.mapbox.com/directions/v5/mapbox/driving/"+x1+"%2C"+y1+"%3B"+x2+"%2C"+y2+"?access_token=pk.eyJ1IjoibmFzcmVkZGluZTEyMzQiLCJhIjoiY2xlaDU4OHJyMTdkejNvb3lkenhtOXlyeSJ9.57G8_EmIkUVPPjPNfT71jw");
    //    Map<String, String> result = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Object object = objectMapper.readValue(url, Object.class);
        ObjectMapper mapper = new ObjectMapper();
        String jsonstr = mapper.writeValueAsString(object);
        JsonNode json = mapper.readTree(jsonstr);
        JsonNode routes = json.get("routes");
        JsonNode firstRoute = routes.get(0);
        JsonNode legs = firstRoute.get("legs");
        JsonNode firstLeg = legs.get(0);
        JsonNode distance = firstLeg.get("distance");

        double d = distance.asDouble()/1000;
        return d;
    }



    @Override
    public void ajouterunbonus(Long idLivreur){
        Livreur livreur=livreurRepository.findById(idLivreur).orElse(null);
        float sal=livreur.getSalaire();
        if (livreurRepository.nbredannee(idLivreur)>=1095.75){
            livreur.setSalaire(sal+50);
            Date d = new Date();
            livreur.setDatedembauche(d);
            livreurRepository.save(livreur);
        }
    }

    @Override
    public void ajouterunbonusencasdelivadd(Long idLivreur){
        Livreur livreur=livreurRepository.findById(idLivreur).orElse(null);
        float sal=livreur.getSalaire();
        if (livreurRepository.nbredelivraisonq(idLivreur)>3){
            System.out.println("c on");
            livreur.setSalaire(sal+7);
            livreurRepository.save(livreur);
        }
    }
  @Override
  public int getdatedembauche(Long idLivreur) {
        return livreurRepository.nbredannee(idLivreur);
    }

    /*@Override
    public void salaryraise(Long idlivreur) {

        Livreur livreur=livreurRepository.findById(idlivreur).orElse(null);
        if (date) {
            livreur.setStatutlivreur(Statut_livreur.Nondisponible);
            System.out.println("non");
            livreurRepository.save(livreur);
        }
        else {
            livreur.setStatutlivreur(Statut_livreur.Disponible);
            System.out.println("dispo");
        }
    }*/


    ///////////////////////////////////////////////////

    @Override
    public double calculateDistance(String origin, String destination) throws ApiException, InterruptedException, IOException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyDvv-7LdYSN_67e1Z6IdG8tojsTpDRE7Co")
                .build();

        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context)
                .origins(new String[]{origin})
                .destinations(new String[]{destination})
                .mode(TravelMode.DRIVING)
                .avoid(DirectionsApi.RouteRestriction.TOLLS);

        DistanceMatrix result = req.await();
        System.out.println(result);
        DistanceMatrixElement[] elements = result.rows[0].elements;
        Distance distance = elements[0].distance;
        return distance.inMeters / 1000.0;
    }

@Override
    public Livreur lemeilleureemployedeumois(){
        String message="";
        List<Livreur> listedeslivreurs= retrieveAllLivreur();
        List<Livraison> listelivraison= livraisonService.retrieveAllLivraison();
        for (Livreur livreur : listedeslivreurs){
            int nbrpoint = 0;
            for (Livraison livraison :listelivraison) {
                LocalDate date = LocalDate.of(2023, 3, 1);
                LocalDate datefindumois = LocalDate.of(2023, 3, 31);
                LocalDate localDateDebut = new java.sql.Date(livraison.getDateLivraison().getTime()).toLocalDate();

                if (livraison.getLivreur().getIdLivreur().equals(livreur.getIdLivreur()) && localDateDebut.isAfter(date) && localDateDebut.isBefore(datefindumois)) {
                    nbrpoint=nbrpoint+1;
                }
            }
            livreur.setNbrelivraison(nbrpoint);
            livreurRepository.save(livreur);

        }


        Livreur l = new Livreur();
        String  messagemeilleur="";
        int max=0;
        for ( Livreur livreur :listedeslivreurs) {
            int nbrpoint=livreur.getNbrelivraison();
            if(nbrpoint>max){
                max=nbrpoint;
                messagemeilleur=livreur.getUser().getFirstName()+"est le meilleur";
            }
            l=livreur;
        }
        return l;
    }
}

