package com.tokyovending.TokyoVending.controllers;

import com.tokyovending.TokyoVending.dtos.CartDto;
import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.models.Cart;
import com.tokyovending.TokyoVending.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartDto>> getAllCarts() {
        List<Cart> carts = cartService.getAllCarts();
        List<CartDto> cartDtos = carts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cartDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartById(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        return ResponseEntity.ok(convertToDto(cart));
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(@Valid @RequestBody CartDto cartDto) {
        Cart cart = convertToEntity(cartDto);
        Cart createdCart = cartService.createCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(createdCart));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartDto> updateCart(@PathVariable Long id, @Valid @RequestBody CartDto cartDto) {
        Cart cart = convertToEntity(cartDto);
        Cart updatedCart = cartService.updateCart(id, cart);
        if (updatedCart != null) {
            return ResponseEntity.ok(convertToDto(updatedCart));
        } else {
            throw new RecordNotFoundException("Cart with ID " + id + " not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

    private CartDto convertToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUser(cart.getUser());
        cartDto.setProducts(cart.getProducts());
        return cartDto;
    }

    private Cart convertToEntity(CartDto cartDto) {
        Cart cart = new Cart();
        cart.setId(cartDto.getId());
        cart.setUser(cartDto.getUser());
        cart.setProducts(cartDto.getProducts());
        return cart;
    }

    @PostMapping("/{id}/add-product/{productId}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable Long id, @PathVariable Long productId) {
        Cart updatedCart = cartService.addProductToCart(id, productId);
        return ResponseEntity.ok(convertToDto(updatedCart));
    }

    @DeleteMapping("/{id}/remove-product/{productId}")
    public ResponseEntity<CartDto> removeProductFromCart(@PathVariable Long id, @PathVariable Long productId) {
        Cart updatedCart = cartService.removeProductFromCart(id, productId);
        return ResponseEntity.ok(convertToDto(updatedCart));
    }
}


