package com.company.photo;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;


    @PostMapping("/upload")
    public ResponseEntity<PhotoResp> upload(@RequestParam MultipartFile file,
                                       @RequestParam UUID productId) {
        return ResponseEntity.ok(
                photoService.upload(file, productId)
        );
    }

    @GetMapping(value = "/{id}",
    produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                photoService.getById(id)
        );
    }
}
