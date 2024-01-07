package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.service.impl.UsersServiceImpl;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping(path = "/users")
public class UsersController {
    private final UsersServiceImpl usersService;

    public UsersController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @PostMapping(path = "/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDTO newPassword) {
            usersService.setPassword(newPassword);
            return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserDTO> me() {
        return ResponseEntity.ok().body(usersService.getUser());
    }

    @PatchMapping(path = "/me")
    public ResponseEntity<UpdateUserDTO> meUpdate(@RequestBody UpdateUserDTO updateUser) {
        return ResponseEntity.ok().body(usersService.updateUser(updateUser));
    }

    @PatchMapping(path = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> meImage(@RequestBody MultipartFile image) {
        usersService.setImage(image);
        return ResponseEntity.ok().build();
    }
}
