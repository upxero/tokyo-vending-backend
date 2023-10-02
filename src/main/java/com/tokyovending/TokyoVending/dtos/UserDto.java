package com.tokyovending.TokyoVending.dtos;

import com.tokyovending.TokyoVending.models.Authority;
import com.tokyovending.TokyoVending.models.Order;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;

public class UserDto {

    @NotBlank
    public String username;
    @NotBlank
    public String password;
    @NotBlank
    @Email
    public String email;
    @NotNull
    public Boolean enabled;
    public String apikey;
    public String profilePicture;
    public Set<Authority> authorities;
    public List<Order> orders;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}

