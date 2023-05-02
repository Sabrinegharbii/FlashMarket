package com.example.marketplace.contollers;

import com.example.marketplace.services.PDFGeneratorServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class PDFExportController {
    private final PDFGeneratorServices pdfGeneratorServices;
    @GetMapping("/pdf/{id}/{idp}")
    public void generatePDF(HttpServletResponse response, @PathVariable("id")Integer id,@PathVariable("idp") Integer idp) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
        String currentDateTime=dateFormat.format(new Date());
        String headerKey="Content-Disposition";
        String headerValue="attachement; filename=pdf_" + currentDateTime+".pdf";
        response.setHeader(headerKey,headerValue);
        this.pdfGeneratorServices.export(response,id,idp);
    }

}
