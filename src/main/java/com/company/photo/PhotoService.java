package com.company.photo;

import com.company.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    @SneakyThrows
    public PhotoResp upload(MultipartFile file, UUID productId) {

        PhotoEntity photo = PhotoEntity.builder()
                .productId(productId)
                .data(file.getBytes())
                .name(file.getOriginalFilename())
                .build();

        photoRepository.save(photo);

        return toDTO(photo);
    }

    private PhotoResp toDTO(PhotoEntity photo) {
        return new PhotoResp(photo.getId(), photo.getName(), photo.getProductId());
    }

    public byte[] getById(UUID id) {
        Optional<PhotoEntity> photoOptional = photoRepository
                .findByIdAndVisibilityTrue(id);

        if (photoOptional.isPresent()) {
           return photoOptional.get()
                    .getData();
        }
            throw new ItemNotFoundException();
    }
}
