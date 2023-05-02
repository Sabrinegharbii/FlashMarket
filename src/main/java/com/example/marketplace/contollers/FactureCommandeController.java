package com.example.marketplace.contollers;

import com.example.marketplace.entities.FactureCommande;
import com.example.marketplace.entities.Panier;
import com.example.marketplace.repository.IFactureCommandeRepo;
import com.example.marketplace.repository.IPanierRepo;
import com.example.marketplace.services.IFactureCommandeServices;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facturePanier")
@Tag(name = "facture panier")
@CrossOrigin(origins = "*")

public class FactureCommandeController {

    private final IFactureCommandeServices factureCommandeServices;
    private final IFactureCommandeRepo factureCommandeRepo;
    private final IPanierRepo panierRepo;


    @Operation(description = "Add facture")
    @PutMapping("/add/{id}")
    FactureCommande addFactureCommande(@RequestBody FactureCommande factureCommande, @PathVariable("id") Integer idcommande, @RequestBody Panier panier) {
        return factureCommandeServices.addFactureCommande(factureCommande, idcommande, panier);
    }

    @Operation(description = "Update facture")
    @PutMapping("/update")
    FactureCommande updateFactureCommande(@RequestBody FactureCommande factureCommande) {
        return factureCommandeServices.updateFactureCommande(factureCommande);
    }

    @Operation(description = "Retrieve FactureCommande")
    @PutMapping("/get/{id}")
    FactureCommande getFactureCommande(@PathVariable("id") Integer id) {

        return factureCommandeServices.retrieveFactureCommande(id);
    }

    @Operation(description = "Retrieve prix FactureCommande")
    @GetMapping("/get")
    float getPrixFactureCommande(@RequestBody Panier panier) {
        //  Panier panier1=panierRepo.findById(IdPanier).orElse(null);
        return factureCommandeServices.calculePrixTotal(panier);
    }
    /////test : !!!generer un pdf

//    @GetMapping(value = "/facture/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
//    public ResponseEntity<InputStreamResource> generateFacturePdf(@PathVariable Integer id) {
//
//        FactureCommande facture = factureCommandeRepo.findById(id).orElse(null); // obtenir l'entité Facture à partir de la base de données en utilisant l'ID
//        Panier panier = panierRepo.findById(id).orElse(null); // obtenir l'entité panier
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        //Document document = new Document(PageSize.A4);
//        PdfWriter writer = new PdfWriter(out);
//        PdfDocument pdf = new PdfDocument(new PdfWriter(writer));
//        Document document = new Document(pdf);
//        document.add(new Paragraph("FACTURE"));
//        document.add(new Paragraph("Date: " + facture.getDateCommande()));
//        document.add(new Paragraph("Nom: " + panier.getUser().getLastName()+panier.getUser().getFirstName()));
//        document.add(new Paragraph("Date de votre commande: " + facture.getDateCommande()));
//        document.add(new Paragraph("Le Prix: " + facture.getPrixFacture()));
//        document.add(new Paragraph("Votre Remise : " + panier.getRemise()));
//        document.close();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("inline", "facture.pdf");
//        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
//
//    }
}