package com.tokyovending.TokyoVending.controllers;

import com.tokyovending.TokyoVending.exceptions.BadRequestException;
import com.tokyovending.TokyoVending.services.FileService;
import com.tokyovending.TokyoVending.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@CrossOrigin
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload-profile-pic/{username}")
    public ResponseEntity<Object> uploadProfilePic(@PathVariable String username, @RequestParam("file") MultipartFile file) {
        String url = fileService.uploadProfilePicture(file, username);
        userService.updateProfilePicture(username, url);
        return ResponseEntity.ok(url);
    }

    @DeleteMapping("/delete-profile-pic/{username}")
    public ResponseEntity<Object> deleteProfilePic(@PathVariable String username) {
        if (fileService.deleteProfilePicture(username)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new BadRequestException("The file does not exist in the system.");
        }
    }
}

