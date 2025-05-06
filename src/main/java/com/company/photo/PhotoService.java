package com.company.photo;

import com.company.component.ApiResponse;
import com.company.component.Companents;
import com.company.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    public ApiResponse<PhotoResp> upload(MultipartFile file, UUID productId) {
        return new ApiResponse<>(uploadFile(file, productId));
    }

    private PhotoResp uploadFile(MultipartFile file, UUID productId) {
        try {
            PhotoEntity photo = PhotoEntity.builder()
                    .productId(productId)
                    .data(file.getBytes())
                    .name(file.getOriginalFilename())
                    .build();
            photoRepository.save(photo);
            return toDTO(photo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ApiResponse<String> deleteById(UUID id) {
        photoRepository.deleteById(id);
        return new ApiResponse<>("Photo with id: " + id + Companents.DELETED);
    }

    public ApiResponse<PhotoResp> metadata(UUID id) {
        Optional<PhotoEntity> byIdAndVisibilityTrue = photoRepository.findByIdAndVisibilityTrue(id);
        if (byIdAndVisibilityTrue.isPresent()) {
            return new ApiResponse<>(toDTO(byIdAndVisibilityTrue.get()));
        }
        else return new ApiResponse<>("Photo not found");
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

    public ApiResponse<List<PhotoResp>> getPhotosByProductId(UUID productId) {
        Optional<List<PhotoEntity>> byProductIdAndVisibilityTrue = photoRepository.findByProductIdAndVisibilityTrue(productId);
        if (byProductIdAndVisibilityTrue.isPresent()) {
            List<PhotoEntity> photoEntities = byProductIdAndVisibilityTrue.get();
            List<PhotoResp> list = photoEntities.stream().map(this::toDTO).toList();
            return new ApiResponse<>(list);
        }
        else throw new ItemNotFoundException();
    }

    public ApiResponse<List<PhotoResp>> multiUpload(MultipartFile[] files, UUID productId) {
        return new ApiResponse<>(Arrays.stream(files)
                .map(file -> uploadFile(file, productId))
                .toList());
//
//        List<PhotoResp> photoResps = new ArrayList<>();
//
//        for (MultipartFile file : files) {
//
//            PhotoResp uploaded = upload(file, productId);
//            photoResps.add(uploaded);
//        }
//
//        return photoResps;
    }

    public byte[] searchPhotosByName(String name) {
        Optional<PhotoEntity> byNameAndVisibilityTrue = photoRepository.findByNameAndVisibilityTrue(name);
        if (byNameAndVisibilityTrue.isPresent()) {
            return byNameAndVisibilityTrue.get().getData();
        }
        else throw new ItemNotFoundException();
    }
}
