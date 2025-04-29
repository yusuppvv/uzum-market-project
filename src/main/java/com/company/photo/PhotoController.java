package com.company.photo;


import com.company.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;
    private final ProductRepository productRepository;


    @PostMapping("/upload")
    public ResponseEntity<PhotoResp> upload(@RequestParam MultipartFile file,
                                       @RequestParam UUID productId) {
        return ResponseEntity.ok(
                photoService.upload(file, productId)
        );
    }

    @PostMapping("/multi-upload")
    public ResponseEntity<List<PhotoResp>> multiUpload(@RequestParam("files") MultipartFile [] files,
                                                       @RequestParam UUID productId) {
        return ResponseEntity.ok(
                photoService.multiUpload(files, productId)
        );

    }

    @GetMapping("/metadata/{id}")
    public ResponseEntity<PhotoResp> metadata(@PathVariable UUID id) {
        return ResponseEntity.ok(photoService.metadata(id));
    }


    //Delete by id
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(photoService.deleteById(id));
    }
    @GetMapping(value = "/{id}",
    produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                photoService.getById(id)
        );
    }

    @GetMapping("/by-product-id/{productId}")
    public ResponseEntity<List<PhotoResp>> getPhotoByProductId(@PathVariable UUID productId) {
        return ResponseEntity.ok(photoService.getPhotosByProductId(productId));
    }

    @GetMapping(value = "/search",
    produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> searchPhotoByName(@RequestParam String name) {
        return ResponseEntity.ok(photoService.searchPhotosByName(name));
    }
}
