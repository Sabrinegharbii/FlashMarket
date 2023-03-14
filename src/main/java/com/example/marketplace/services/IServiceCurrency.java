package com.example.marketplace.services;

import com.example.marketplace.entities.Currency;

import java.util.List;

public interface IServiceCurrency {
     void ajouterCurrency(Currency currency);
    List<Currency> getAllCurrency();

     Currency updateCurrency(Long idCurrency,Currency currency);


}
