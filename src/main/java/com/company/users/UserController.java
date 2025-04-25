package com.company.users;

import com.company.users.dto.UserCreationDto;
import com.company.users.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreationDto userCreationDto) {
        return ResponseEntity.ok(userService.create(userCreationDto).getBody());
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserResponseDto>> get() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PutMapping("/update-by-id/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable UUID id, @RequestBody UserCreationDto userCreationDto) {
        return ResponseEntity.ok(userService.update(id, userCreationDto));
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.delete(id));
    }





}
