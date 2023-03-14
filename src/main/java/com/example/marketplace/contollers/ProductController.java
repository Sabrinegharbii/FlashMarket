package com.example.marketplace.contollers;

import com.example.marketplace.entities.Product;
import com.example.marketplace.services.IProductServ;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Product")

public class ProductController {
    final IProductServ iProductServ;


    @GetMapping("/allproducts")
    List<Product> findAllProduct() {
        return iProductServ.findAllProduct();
    }

    @PostMapping("/addproduct")
    Product addProduct(@RequestBody Product p) {
        return iProductServ.addProduct(p);
    }

    @PatchMapping("/updateProduct")
    Product updateProduct(@RequestBody Product p) {
        return iProductServ.updateProduct(p);
    }

    @GetMapping("/getProduct/{id}")
    Product findById(@PathVariable("id") Integer id) {
        return iProductServ.findById(id);
    }

    @DeleteMapping("/deleteProduct/{id}")
    void deleteProduct(@PathVariable("id") Integer id) {
        iProductServ.deleteProduct(id);
    }
    @GetMapping("/testt/{p}")
    String GetScore(@PathVariable("p")int p) {
        return iProductServ.NutriscoreCategorie(p);
    }
    @GetMapping("/FiltrePrix/{Price}")
    List<Product> FiltrePrix(@Param("price") Double px){
        return iProductServ.filterByPrice(px);
    }

    @GetMapping("/alert")
    public String showAlert() {
        return iProductServ.showAlert();
    }
    @GetMapping("/remise")
    public List<Product> getProductsBefore3DaysOfExpiration() {
        return iProductServ.getProductsBeforeOfExpiration();
    }
    @PutMapping("/addAndassignProductToCataloque/{idCataloque}")
    Product addAndassignProductTCatalogue(@RequestBody Product P,@PathVariable("idCataloque") Integer idCatalogue){
        return iProductServ.addAndassignProductTCatalogue(P,idCatalogue);
    }


}