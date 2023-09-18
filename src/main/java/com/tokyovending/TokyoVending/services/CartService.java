package com.tokyovending.TokyoVending.services;

import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.models.Cart;
import com.tokyovending.TokyoVending.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Cart with ID " + id + " not found."));
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart updateCart(Long id, Cart cart) {
        Cart existingCart = getCartById(id);
        if (existingCart != null) {
            existingCart.setUser(cart.getUser());
            // Voeg andere attributen toe zoals producten in de winkelwagen, afhankelijk van je vereisten.
            return cartRepository.save(existingCart);
        }
        return null;
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
}

