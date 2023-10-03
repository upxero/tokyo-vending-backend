package com.tokyovending.TokyoVending.controllers;

import com.tokyovending.TokyoVending.dtos.UserDto;
<<<<<<< HEAD
import com.tokyovending.TokyoVending.exceptions.BadRequestException;
=======
import com.tokyovending.TokyoVending.models.User;
>>>>>>> origin/main
import com.tokyovending.TokyoVending.services.UserService;
import com.tokyovending.TokyoVending.utils.FieldError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
<<<<<<< HEAD
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
=======
import java.util.List;
import java.util.stream.Collectors;
>>>>>>> origin/main

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

<<<<<<< HEAD
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
=======
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers().stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            UserDto userDto = convertToUserDto(user);
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
>>>>>>> origin/main
        }

<<<<<<< HEAD
        String newUsername = userService.createUser(dto);
        userService.addAuthority(newUsername, "ROLE_USER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
=======
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        User user = convertToUserEntity(userDto);
        User createdUser = userService.createUser(user);
        UserDto createdUserDto = convertToUserDto(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        User user = convertToUserEntity(userDto);
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            UserDto updatedUserDto = convertToUserDto(updatedUser);
            return ResponseEntity.ok(updatedUserDto);
        } else {
            return ResponseEntity.notFound().build();
>>>>>>> origin/main
        }

<<<<<<< HEAD

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
=======
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setProfilePicture(user.getProfilePicture());
        return userDto;
    }

    private User convertToUserEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setProfilePicture(userDto.getProfilePicture());
        return user;
    }
>>>>>>> origin/main
}




<<<<<<< HEAD

=======
>>>>>>> origin/main
