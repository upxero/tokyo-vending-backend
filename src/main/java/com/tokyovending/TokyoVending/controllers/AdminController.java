package com.tokyovending.TokyoVending.controllers;

import com.tokyovending.TokyoVending.dtos.AdminDto;
import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<AdminDto>> getAllAdmins() {
        List<AdminDto> adminDtos = adminService.getAllAdmins();
        return ResponseEntity.ok(adminDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Long id) {
        AdminDto adminDto = adminService.getAdminById(id);
        if (adminDto != null) {
            return ResponseEntity.ok(adminDto);
        } else {
            throw new RecordNotFoundException("Admin with ID " + id + " not found.");
        }
    }

    @PostMapping
    public ResponseEntity<AdminDto> createAdmin(@Valid @RequestBody AdminDto adminDto) {
        AdminDto createdAdminDto = adminService.createAdmin(adminDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdminDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDto> updateAdmin(@PathVariable Long id, @Valid @RequestBody AdminDto adminDto) {
        AdminDto updatedAdminDto = adminService.updateAdmin(id, adminDto);
        if (updatedAdminDto != null) {
            return ResponseEntity.ok(updatedAdminDto);
        } else {
            throw new RecordNotFoundException("Admin with ID " + id + " not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}



