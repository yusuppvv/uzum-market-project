package com.company.photo;

import com.company.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoResp upload(MultipartFile file, UUID productId) {

        PhotoEntity photo = null;
        try {
            photo = PhotoEntity.builder()
                    .productId(productId)
                    .data(file.getBytes())
                    .name(file.getOriginalFilename())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        photoRepository.save(photo);

        return toDTO(photo);
    }

    public String deleteById(UUID id) {
        photoRepository.deleteById(id);
        return "Photo with id: " + id + " deleted successfully";
    }

    public PhotoResp metadata(UUID id) {
        Optional<PhotoEntity> byIdAndVisibilityTrue = photoRepository.findByIdAndVisibilityTrue(id);
        if (byIdAndVisibilityTrue.isPresent()) {
            return toDTO(byIdAndVisibilityTrue.get());
        }
        else return null;
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

    public List<PhotoResp> getPhotosByProductId(UUID productId) {
        Optional<List<PhotoEntity>> byProductIdAndVisibilityTrue = photoRepository.findByProductIdAndVisibilityTrue(productId);
        if (byProductIdAndVisibilityTrue.isPresent()) {
            List<PhotoEntity> photoEntities = byProductIdAndVisibilityTrue.get();
            return photoEntities.stream()
                    .map(this::toDTO)
                    .collect(toList());
        }
        else throw new ItemNotFoundException();
    }

    public List<PhotoResp> multiUpload(MultipartFile[] files, UUID productId) {
        return Arrays.stream(files)
                .map(file -> upload(file, productId))
                .collect(toList());
    }

    public byte[] searchPhotosByName(String name) {
        Optional<PhotoEntity> byNameAndVisibilityTrue = photoRepository.findByNameAndVisibilityTrue(name);
        if (byNameAndVisibilityTrue.isPresent()) {
            return byNameAndVisibilityTrue.get().getData();
        }
        else throw new ItemNotFoundException();
    }
}
