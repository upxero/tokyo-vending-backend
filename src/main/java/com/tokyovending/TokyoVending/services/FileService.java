package com.tokyovending.TokyoVending.services;

import com.tokyovending.TokyoVending.exceptions.EntityNotFoundException;
import com.tokyovending.TokyoVending.models.User;
import com.tokyovending.TokyoVending.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Date;

@Service
public class FileService {

    private final Path fileStoragePath;
    private final String fileStorageLocation;
    private final UserRepository userRepository;

    public FileService(@Value("${my.upload_location}") String fileStorageLocation, UserRepository userRepository) {
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.userRepository = userRepository;

        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Error creating the file directory.");
        }
    }

    public String uploadProfilePicture(MultipartFile file, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User with username " + username + " does not exist."));

        if (user.getProfilePicture() != null) {
            Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(user.getProfilePicture());
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                throw new RuntimeException("Error occurred while deleting the file: " + user.getProfilePicture());
            }
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename() + Date.from(Instant.now()).getTime());
        storeFile(file, fileName);
        user.setProfilePicture(fileName);
        userRepository.save(user);

        return fileName;
    }

    public boolean deleteProfilePicture(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User with username " + username + " does not exist."));

        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(user.getProfilePicture());
        user.setProfilePicture(null);
        userRepository.save(user);

        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while deleting the file: " + user.getProfilePicture());
        }
    }

    private void storeFile(MultipartFile file, String fileName) {
        try {
            Path targetLocation = fileStoragePath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error storing the file " + fileName);
        }
    }
}





