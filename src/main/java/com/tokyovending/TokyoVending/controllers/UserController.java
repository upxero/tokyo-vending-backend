package com.tokyovending.TokyoVending.controllers;

import com.tokyovending.TokyoVending.dtos.UserDto;
import com.tokyovending.TokyoVending.exceptions.BadRequestException;
import com.tokyovending.TokyoVending.services.UserService;
import com.tokyovending.TokyoVending.utils.FieldError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final FieldError fieldError = new FieldError();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {

        UserDto optionalUser = userService.getUser(username);


        return ResponseEntity.ok().body(optionalUser);
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto dto, BindingResult br) {
        if (br.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(fieldError.fieldErrorBuilder(br));
        }

        String newUsername = userService.createUser(dto);
        userService.addAuthority(newUsername, "ROLE_USER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
        }


        @PutMapping(value = "/{username}")
        public ResponseEntity<UserDto> updateUser(@PathVariable("username") String username, @RequestBody UserDto dto) {

            userService.updateUser(username, dto);

            return ResponseEntity.noContent().build();
        }

        @DeleteMapping(value = "/{username}")
        public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
            userService.deleteUser(username);
            return ResponseEntity.noContent().build();
        }

        @GetMapping(value = "/{username}/authorities")
        public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
            return ResponseEntity.ok().body(userService.getAuthorities(username));
        }

        @PostMapping(value = "/{username}/authorities")
        public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
            try {
                String authorityName = (String) fields.get("authority");
                userService.addAuthority(username, authorityName);
                return ResponseEntity.noContent().build();
            } catch (Exception ex) {
                throw new BadRequestException(ex.getMessage());
            }
        }

        @DeleteMapping(value = "/{username}/authorities/{authority}")
        public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
            userService.removeAuthority(username, authority);
            return ResponseEntity.noContent().build();
        }
}





