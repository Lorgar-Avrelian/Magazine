package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.service.impl.UsersServiceImpl;

@RestController
@RequestMapping(path = "/users")
public class UsersController {
    private final UsersServiceImpl usersService;

    public UsersController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @PostMapping(path = "/set_password")
    public ResponseEntity setPassword(@RequestBody NewPassword newPassword) {
        if (newPassword.getCurrentPassword() != null && newPassword.getNewPassword() != null && !newPassword.getNewPassword().equals(newPassword.getCurrentPassword())) {
            usersService.setPassword(newPassword);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping(path = "/me")
    public ResponseEntity me() {
        Register user = new Register();
        user.setFirstName("test name");
        user.setLastName("test lastname");
        user.setPhone("test phone");
        user.setRole(Role.USER);
        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PatchMapping(path = "/me")
    public ResponseEntity meUpdate() {
        return ResponseEntity.ok().build();
    }
    @PatchMapping(path = "/me/image")
    public ResponseEntity image() {
        return ResponseEntity.ok().build();
    }
}
