package com.tokyovending.TokyoVending.dtos;

import java.util.List;

public class AdminDto {
    private Long id;
    private String username;
    private String email;
    private List<Long> vendingMachineIds;
    private List<Long> managedUserIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getVendingMachineIds() {
        return vendingMachineIds;
    }

    public void setVendingMachineIds(List<Long> vendingMachineIds) {
        this.vendingMachineIds = vendingMachineIds;
    }

    public List<Long> getManagedUserIds() {
        return managedUserIds;
    }

    public void setManagedUserIds(List<Long> managedUserIds) {
        this.managedUserIds = managedUserIds;
    }
}



