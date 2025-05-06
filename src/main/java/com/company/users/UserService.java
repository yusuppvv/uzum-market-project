package com.company.users;

import com.company.component.ApiResponse;
import com.company.exception.ItemNotFoundException;
import com.company.users.DTO.UserDto;
import com.company.users.DTO.UserResp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ApiResponse<UserResp> create(UserDto userDto) {
        Optional<UserEntity> byEmail =
                userRepository.findByEmail(userDto.getEmail());
        if (byEmail.isPresent()) {
            return new ApiResponse<>
                    (HttpStatus.BAD_REQUEST.value(), "bu gmail ishlatilgan");
        }

        UserEntity userEntity= UserEntity
                .builder()
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build();

        UserEntity save = userRepository.save(userEntity);

        return new ApiResponse<>(UserResp
                .builder()
                .id(userEntity.getId())
                .email(save.getEmail())
                .fullName(save.getFullName())
                .build());
    }

    public ApiResponse<List<UserResp>> getAll() {
        List<UserResp> list = userRepository.findAll()
                .stream()
                .map(u -> toDto(u))
                .toList();
        if (list.isEmpty()) {
            throw new ItemNotFoundException();
        }
        else {
            return new ApiResponse<>(list);
        }
    }

    public ApiResponse<UserResp> getById(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(ItemNotFoundException::new);

        return new ApiResponse<>(toDto(userEntity));
    }

    public ApiResponse<UserResp> update(UUID id, UserDto userDto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(ItemNotFoundException::new);

        Optional<UserEntity> byEmail = userRepository.findByEmail(userDto.getEmail());
        if (byEmail.isPresent()) {
            return new ApiResponse<>
                    (404, "bu gmail ishlatilgan");
        }

        userEntity.setFullName(userDto.getFullName());
        userEntity.setEmail(userDto.getEmail());

        UserEntity save = userRepository.save(userEntity);

        return new ApiResponse<>(toDto(save));
    }

    public ApiResponse<String> delete(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        userEntity.setVisibility(false);
        userRepository.save(userEntity);
        return new ApiResponse<>("Successfully deleted!");
    }

    private UserResp toDto(UserEntity userEntity) {
        return UserResp.builder()
                .fullName(userEntity.getFullName())
                .email(userEntity.getEmail())
                .id(userEntity.getId())
                .build();
    }
}
