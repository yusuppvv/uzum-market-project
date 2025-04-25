package com.company.users;

import com.company.exception.AppBadRequestException;
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

    public ResponseEntity<UserResponseDto> create(UserCreationDto userCreationDto) {
        Optional<UserEntity> optionalUser = userRepository.findByFullNameOrEmail(userCreationDto.getFullName(), userCreationDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new AppBadRequestException("User already exists");
        }
        else {
            UserEntity save = userRepository.save(new UserEntity(userCreationDto.getFullName(), userCreationDto.getEmail()));
            return ResponseEntity.ok(new UserResponseDto(save.getId(), save.getFullName(), save.getEmail()));
        }
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAllByVisibilityIsTrue()
                .stream().map(e -> new UserResponseDto(e.getId(), e.getFullName(), e.getEmail())).toList();
    }

    public UserResponseDto update(UUID id, UserCreationDto userCreationDto) {
        Optional<UserEntity> byId = userRepository.findByIdAndVisibilityIsTrue(id);
        if (byId.isPresent()) {
            UserEntity userEntity = byId.get();
            userEntity.setFullName(userCreationDto.getFullName());
            userEntity.setEmail(userCreationDto.getEmail());
            userRepository.save(userEntity);
            return new UserResponseDto(userEntity.getId(), userEntity.getFullName(), userEntity.getEmail());
        }
        else throw new AppBadRequestException("User not found by this id.");
    }

    public String delete(UUID id) {
        Optional<UserEntity> byId = userRepository.findByIdAndVisibilityIsTrue(id);
        if (byId.isPresent()) {
            byId.get().setVisibility(false);
            return "User deleted successfully";
        }
        else return "User not found by this id.";
    }
}
