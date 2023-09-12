package com.tokyovending.TokyoVending.exceptions;

public class UsernameNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException(String username) {
        super("User with username '" + username + "' not found.");
    }
}

