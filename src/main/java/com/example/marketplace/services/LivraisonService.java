package com.example.marketplace.services;

import com.example.marketplace.entities.FactureCommande;
import com.example.marketplace.entities.Livraison;
import com.example.marketplace.entities.Livreur;
import com.example.marketplace.enumerations.Type_livraison;
import com.example.marketplace.enumerations.description;
import com.example.marketplace.repository.IFactureCommandeRepo;
import com.example.marketplace.repository.ILivraisonRepository;
import com.example.marketplace.repository.ILivreurRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class LivraisonService implements LivraisonInt{

    @Autowired
    IFactureCommandeRepo factureCommandeRepo;
    @Autowired
    ILivraisonRepository livraisonRepository;
    ILivreurRepository livreurRepository;

    //Affichage
    @Override
    public List<Livraison> retrieveAllLivraison() {
        List<Livraison> livraisons = livraisonRepository.findAll();
        return livraisons;
    }
    //AjoutLivraison

    @Override
    public Livraison ajouterlivraison(Livraison livraison ) {
        return livraisonRepository.save(livraison);
    }

    @Override
    public byte[] generateQRCode(Long idLivraison) throws WriterException, IOException {
        Livraison livraison = livraisonRepository.findById(idLivraison).get();
        String qrCodeData="";
        if (livraison.getTypelivraison().equals(Type_livraison.Livraisonexpress)){

               livraison.setDescpriton(description.Livraisonrapidequiprend24h);
               livraisonRepository.save(livraison);
        qrCodeData = "Nom Destinataire: " + livraison.getNomdestinataire().toString() + " Typelivraison: " + livraison.getTypelivraison() + " Adresse: " + livraison.getAdresse() + " Description: " +livraison.getDescpriton();}
        else if (livraison.getTypelivraison().equals(Type_livraison.Livraisonstandard)){

            livraison.setDescpriton(description.Livraisonnormalequipeutprendrejusquaunesemaine);
            livraisonRepository.save(livraison);

         qrCodeData = "Nom Destinataire: " + livraison.getNomdestinataire().toString() + " Typelivraison: " + livraison.getTypelivraison() + " Adresse: " + livraison.getAdresse()
                + " Description: " + livraison.getDescpriton() ;}
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(new String(qrCodeData.getBytes(charset), charset), BarcodeFormat.QR_CODE, 200, 200, hintMap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
        return baos.toByteArray();
    }

    @Override
    public void saveandaffectLivreurtoLivraison(Livraison livraison, Long idLivreur) {
        Livreur livreur=livreurRepository.findById(idLivreur).get();
         livraison.setLivreur(livreur);
        livraisonRepository.save(livraison);
    }

    //Afficher grace a l id
    @Override
    public Livraison getLivraisonById(Long idlivraison) {
        return livraisonRepository.findById(idlivraison)
                .orElse(null) ;
    }

    //ModifierLivraison
    @Override
    public Livraison updateLivraison(Livraison livraisonDetails) {
        return livraisonRepository.save( livraisonDetails);
    }

    //Supprimerlivraison
    @Override
    public void deleteLivraison(Long idlivraison) {
        Livraison livraison = getLivraisonById(idlivraison);
        livraisonRepository.delete(livraison);
    }


    @Override
    public Livraison tst(Long id, Livraison lv) {
        Livreur lvr = livreurRepository.findById(id).orElse(null);
       lv.setLivreur(lvr);
        livraisonRepository.save(lv);
        return lv;
    }

    @Override
    public double calculerPrixTotal(Integer idFacture) {
        FactureCommande factureCommande = factureCommandeRepo.findById(idFacture).orElse(null);

         double PRIX_LIVRAISON_STANDARD = 5.0;
         double PRIX_LIVRAISON_EXPRESS = 10.0;
         double prixTotal = factureCommande.getPrixFacture();

        if (factureCommande.getLivraison().getTypelivraison().equals(Type_livraison.Livraisonstandard)) {
            prixTotal += PRIX_LIVRAISON_STANDARD;
        } else if (factureCommande.getLivraison().getTypelivraison().equals(Type_livraison.Livraisonexpress)) {
            prixTotal += PRIX_LIVRAISON_EXPRESS;
        }

        return prixTotal;
    }



}

