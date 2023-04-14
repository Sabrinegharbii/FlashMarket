package com.example.marketplace.services;

import com.example.marketplace.entities.Market;
import com.example.marketplace.entities.Product;
import com.example.marketplace.enumerations.NutriscoreCategorie;
import com.example.marketplace.repository.IMarketRepository;
import com.example.marketplace.repository.IProductRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServ implements IProductServ {


    IProductRepo iProductRepo;
    IMarketRepository marketRepository;


    @Override
    public Product addProduct(Product product) {
        product.setQtySold(0);
        return iProductRepo.save(product);
    }

    @Override
    public List<Product> findAllProduct() {
        List<Product> prod = new ArrayList<>();
        iProductRepo.findAll().forEach(prod::add);
        return prod;
    }

    @Override
    public Product updateProduct(Product product) {
        return iProductRepo.save(product);
    }

    @Override
    public Product findById(Integer id) {
        return iProductRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteProduct(Integer id) {
        iProductRepo.deleteById(id);

    }

    public String NutriscoreCategorie(int idProduit) {
        Product p = iProductRepo.findById(idProduit).orElse(null);
        System.out.println("je syuis la");
        if (p.getCategorie().toString().equals("ProduitAlimenataire")) {
            int a = p.getNutriscore();
            NutriscoreCategorie category;
            System.out.println("je syuis la 2");

            if (a >= -15 && a <= -2) {
                category = NutriscoreCategorie.A;
            } else if (a >= -1 && a <= 3) {
                category = NutriscoreCategorie.B;
            } else if (a >= 4 && a <= 11) {
                category = NutriscoreCategorie.C;
            } else if (a >= 12 && a <= 16) {
                category = NutriscoreCategorie.D;
            } else if (a >= 17 && a <= 40) {
                category = NutriscoreCategorie.E;
            } else {
                category = NutriscoreCategorie.Erroné;
            }
            return "product category is :" + category;
        }


        return null;
    }


    public List<Product> filterByPrice(Double price) {

        return this.iProductRepo.filterByPrice(price);
    }


    public List<Product> findByQuantityLessThanEqual(int quantity) {
        return this.iProductRepo.findByQuantityLessThanEqual(quantity);
    }

    public String showAlert() {
        List<Product> products = iProductRepo.findByQuantityLessThanEqual(2);
        if (products.size() > 0) {

            return "ALERT: There are " + products.size() + " product(s) with quantity less than or equal to 2";
        } else {
            return "No products found with quantity less than or equal to 2";
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public List<Product> getProductsBeforeOfExpiration() {
        List<Product> p = new ArrayList<>();
        iProductRepo.findAll().forEach(p::add);
        List<Product> pnew = new ArrayList<>();
        LocalDate threeDaysFromNow = LocalDate.now().plusDays(3);
        for (int i = 0; i < p.size(); i++) {

            if (p.get(i).getDateExpiration().minusDays(3).isEqual(LocalDate.now())) {
                p.get(i).setPrice(p.get(i).getPrice() - p.get(i).getPrice() * 0.3);
                iProductRepo.save(p.get(i));
                pnew.add(p.get(i));
            }
        }
        return pnew;
    }

    public Product addAndassignProductToMarket(Product p, Integer idMarket) {
        Market market = marketRepository.findById(idMarket).orElse(null);
        p.setMarket(market);
        return iProductRepo.save(p);
    }
}
