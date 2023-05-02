package com.example.marketplace.services;

import com.example.marketplace.entities.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IimageSer extends Serializable {
    List<Image> list();
    Optional<Image> getOne(int id);
    ResponseEntity<?> AddandAssig(MultipartFile imagen,Integer id) throws IOException;
    ResponseEntity<?> AddandAssign(MultipartFile image,Integer id) throws IOException;
    void delete(int id);
    boolean exists(int id);

}
