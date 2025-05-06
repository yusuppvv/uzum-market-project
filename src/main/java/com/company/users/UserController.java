package com.company.users;

import com.company.component.ApiResponse;
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
    public ResponseEntity<ApiResponse<UserResp>> create(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.create(userDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<UserResp>>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/get-by/{id}")
    public ResponseEntity<ApiResponse<UserResp>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UserResp>> update(@PathVariable UUID id,@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.update(id, userDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.delete(id));
    }
}
