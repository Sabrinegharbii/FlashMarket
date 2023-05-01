package com.example.marketplace.repository;

import com.example.marketplace.entities.Market;
import com.example.marketplace.entities.Product;

import com.example.marketplace.enumerations.Sujetrec;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductRepo extends JpaRepository<Product,Integer> {


    @Query("Select count(p)from Reclamation  p inner join p.lgcommande lg inner join lg.products pr where p.description=:description and pr.id=:id ")
    Integer nombredereclamationdunproduit(@Param("description") Sujetrec description, @Param("id") Integer id);


    @Query("SELECT p FROM Product p WHERE p.price <= :price")
    List<Product> filterByPrice(Double price);

    List<Product> findByQuantityLessThanEqual(int quantity);

    List<Product> findProductsByMarket(Market market);
}



