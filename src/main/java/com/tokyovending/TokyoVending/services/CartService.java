package com.tokyovending.TokyoVending.services;

import com.tokyovending.TokyoVending.exceptions.ProductNotFoundException;
import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.models.Cart;
import com.tokyovending.TokyoVending.models.Order;
import com.tokyovending.TokyoVending.models.Product;
import com.tokyovending.TokyoVending.models.VendingMachine;
import com.tokyovending.TokyoVending.repositories.CartRepository;
import com.tokyovending.TokyoVending.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, OrderService orderService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderService = orderService;
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
        existingCart.setUser(cart.getUser());
        return cartRepository.save(existingCart);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    @Transactional
    public Cart addProductToCart(Long cartId, Long productId) {
        Cart cart = getCartById(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));
        productRepository.save(product);

        cart.addProduct(product);
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeProductFromCart(Long cartId, Long productId) {
        Cart cart = getCartById(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));
        productRepository.save(product);

        cart.removeProduct(product);
        return cartRepository.save(cart);
    }

    @Transactional
    public Order createOrderFromCart(Long cartId, VendingMachine vendingMachine) {
        Cart cart = getCartById(cartId);
        if (cart.getProducts().isEmpty()) {
            throw new RuntimeException("Kan geen bestelling maken van een lege winkelwagen");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setProducts(new ArrayList<>(cart.getProducts()));
        order.setOrderDateTime(LocalDateTime.now());
        order.setCompleted(false);
        order.setVendingMachine(vendingMachine);

        Order createdOrder = orderService.createOrder(order);

        cart.getProducts().clear();
        cartRepository.save(cart);

        return createdOrder;
    }
}


