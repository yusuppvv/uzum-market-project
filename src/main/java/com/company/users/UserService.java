package com.company.users;

import com.company.users.dto.UserCreationDto;
import com.company.users.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<?> create(UserCreationDto userCreationDto) {
        Optional<UserEntity> optionalUser = userRepository.findByFullNameOrEmail(userCreationDto.getFullName(), userCreationDto.getEmail());
        if (optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        else {
            return ResponseEntity.ok(userRepository.save(new UserEntity
                     (userCreationDto.getFullName(), userCreationDto.getEmail()))
            );
        }
    }

    public ResponseEntity<?> getAll() {
        Optional<List<UserEntity>> userList = Optional.of(userRepository.findAll());
        if (userList.isPresent()) {
            return ResponseEntity.ok(userList);
        }
        else return ResponseEntity.badRequest().body("No users found");
    }

    public ResponseEntity<?> update(UUID id, UserCreationDto userCreationDto) {
        Optional<UserEntity> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            UserEntity userEntity = byId.get();
            userEntity.setFullName(userCreationDto.getFullName());
            userEntity.setEmail(userCreationDto.getEmail());
            userRepository.save(userEntity);
            return ResponseEntity.ok(userEntity);
        }
        else return ResponseEntity.badRequest().body("User not found by this id.");
    }

    public ResponseEntity<?> delete(UUID id) {
        Optional<UserEntity> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            userRepository.deleteById(byId.get().getId());
            return ResponseEntity.ok("User deleted successfully");
        }
        else return ResponseEntity.badRequest().body("User not found by this id.");
    }
}
