package com.example.marketplace.services;

import com.example.marketplace.entities.Image;
import com.example.marketplace.entities.Post;
import com.example.marketplace.entities.Product;
import com.example.marketplace.repository.IPostRepo;
import com.example.marketplace.repository.IProductRepo;
import com.example.marketplace.repository.ImageRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ImageSer implements IimageSer{
    final CloudinaryService cloudinaryService;
    final ImageRepo imageRepo;
    final IPostRepo iPostRepo;
    final IProductRepo  iProductRepo;

    @Override
    public List<Image> list() {
        List<Image> i = new ArrayList<>();
        imageRepo.findAll().forEach(i::add);
        return i;
    }

    @Override
    public Optional<Image> getOne(int id) {
        return imageRepo.findById((long) id);

    }

    @Override
    public ResponseEntity<?> AddandAssig(MultipartFile image,Integer id) throws IOException {
        Post p=iPostRepo.findById(id).orElse(null);
        Map result = cloudinaryService.upload(image);
        BufferedImage bi = ImageIO.read(image.getInputStream());
        Image media = new Image((String)
                result.get("original_filename")
                , (String) result.get("url"),
                (String) result.get("public_id"));
        media.setPost(p);
        imageRepo.save(media);
        return null;
    }
    @Override
    public ResponseEntity<?> AddandAssign(MultipartFile image,Integer id) throws IOException {
        Product p=iProductRepo.findById(id).orElse(null);
        Map result = cloudinaryService.upload(image);
        BufferedImage bi = ImageIO.read(image.getInputStream());
        Image media = new Image((String)
                result.get("original_filename")
                , (String) result.get("url"),
                (String) result.get("public_id"));
        media.setProduct(p);
        imageRepo.save(media);
        return null;
    }

    @Override
    public void delete(int id) {
        imageRepo.deleteById((long) id);
    }

    @Override
    public boolean exists(int id) {
        return imageRepo.existsById((long) id);
    }
}
