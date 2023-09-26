package com.tokyovending.TokyoVending.services;

import com.tokyovending.TokyoVending.dtos.AdminDto;
import com.tokyovending.TokyoVending.models.Admin;
import com.tokyovending.TokyoVending.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<AdminDto> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(this::convertToAdminDto).collect(Collectors.toList());
    }

    public AdminDto getAdminById(Long id) {
        Admin admin = adminRepository.findById(id).orElse(null);
        if (admin != null) {
            return convertToAdminDto(admin);
        }
        return null;
    }

    public AdminDto createAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setUsername(adminDto.getUsername());
        admin.setEmail(adminDto.getEmail());
        Admin savedAdmin = adminRepository.save(admin);
        return convertToAdminDto(savedAdmin);
    }

    public AdminDto updateAdmin(Long id, AdminDto adminDto) {
        Admin existingAdmin = adminRepository.findById(id).orElse(null);
        if (existingAdmin != null) {
            existingAdmin.setUsername(adminDto.getUsername());
            existingAdmin.setEmail(adminDto.getEmail());
            Admin updatedAdmin = adminRepository.save(existingAdmin);
            return convertToAdminDto(updatedAdmin);
        }
        return null;
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    private AdminDto convertToAdminDto(Admin admin) {
        AdminDto adminDto = new AdminDto();
        adminDto.setId(admin.getId());
        adminDto.setUsername(admin.getUsername());
        adminDto.setEmail(admin.getEmail());
        return adminDto;
    }
}



