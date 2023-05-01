package com.example.marketplace.contollers;

import com.example.marketplace.entities.Product;
import com.example.marketplace.entities.Rating;
import com.example.marketplace.services.IRatingServ;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Rating")
public class RatingController {

    final IRatingServ iRatingServ;

    @GetMapping("/allRating")
    List<Rating> findAllRatingt() {
        return iRatingServ.findAllRating();
    }

    @PostMapping("/addRating")
    Rating addRating(@RequestBody Rating r) {
        return iRatingServ.addRating(r);
    }

    @PatchMapping("/updateRating")
    Rating updateRating(@RequestBody Rating r) {
        return iRatingServ.updateRating(r);
    }

    @GetMapping("/getRating/{id}")
    Rating findById(@PathVariable("id") Integer id) {
        return iRatingServ.findById(id);
    }

    @DeleteMapping("/deleteRating/{id}")
    void deleteRating(@PathVariable("id") Integer id) {
        iRatingServ.deleteRating(id);
    }

    @GetMapping("/addAndassignRatingToProductAndUser/{idProd}/{idUser}/{r}")
    Rating addAndassignRatingToProductAndUser(@PathVariable("idProd") Integer idProd,@PathVariable("idUser") Integer idUser,@PathVariable Integer r){
       Rating rr=new Rating();
       rr.setNote(r);
        return iRatingServ.addAndassignRatingToProductAndUser(rr,idProd,idUser);
    }
    @GetMapping("RatingCalcul/{idProduit}")
    public float RatingCalcul(@PathVariable("id") Integer id){
        return iRatingServ.RatingCalcul(id);
    }
}

