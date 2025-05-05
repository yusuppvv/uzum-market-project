package com.company.users;

import com.company.exception.BadRequestException;
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

    public ResponseEntity<?> create(UserDto userDto) {
        Optional<UserEntity> byEmail =
                userRepository.findByEmail(userDto.getEmail());
        if (byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }

        UserEntity userEntity= UserEntity
                .builder()
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build();

        UserEntity save = userRepository.save(userEntity);

        return ResponseEntity.ok(UserResp
                .builder()
                        .id(userEntity.getId())
                        .email(save.getEmail())
                        .fullName(save.getFullName())
                .build());
    }

    public ResponseEntity<List<UserResp>> getAll() {
        return ResponseEntity.ok(
                userRepository.findAll()
                        .stream()
                        .map(u -> changeUserResp(u))
                        .toList()
        );
    }



    public ResponseEntity<UserResp> getById(UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(ItemNotFoundException::new);

        return ResponseEntity.ok(changeUserResp(userEntity));
    }

    public ResponseEntity<?> update(UUID id, UserDto userDto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(ItemNotFoundException::new);

        Optional<UserEntity> byEmail = userRepository.findByEmail(userDto.getEmail());
        if (byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bu gmail ishlatilgan");
        }

        userEntity.setFullName(userDto.getFullName());
        userEntity.setEmail(userDto.getEmail());

        UserEntity save = userRepository.save(userEntity);

        return ResponseEntity.ok(changeUserResp(save));
    }

    public ResponseEntity<?> delete(UUID id) {
        userRepository.findById(id).orElseThrow(ItemNotFoundException::new);

        userRepository.deleteById(id);

        return ResponseEntity.ok("Sucsess");
    }

    private UserResp changeUserResp(UserEntity userEntity) {
        return UserResp.builder()
                .fullName(userEntity.getFullName())
                .email(userEntity.getEmail())
                .id(userEntity.getId())
                .build();
    }
}
