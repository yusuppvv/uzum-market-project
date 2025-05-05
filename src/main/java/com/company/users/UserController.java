package com.company.users;

import com.company.users.DTO.UserDto;
import com.company.users.DTO.UserResp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserResp>> getAll() {
        return userService.getAll();
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<UserResp> getById(@PathVariable UUID id) {
        return userService.getById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id,@RequestBody UserDto userDto) {
        return userService.update(id, userDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return userService.delete(id);
    }
}
