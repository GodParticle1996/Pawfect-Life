package com.example.pawfectlife.service;

import com.example.pawfectlife.model.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {

    private final Map<Product, Integer> cart = new HashMap<>();

    /**
     * Adds a product to the cart or increments its quantity
     */
    public void addProduct(Product product) {
        cart.merge(product, 1, Integer::sum);
    }

    public void removeProduct(Product product) {
        cart.remove(product);
    }

    public void updateQuantity(Product product, int quantity) {
        if (quantity > 0) {
            cart.put(product, quantity);
        } else {
            removeProduct(product);
        }
    }

    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(cart);
    }

    public BigDecimal getTotal() {
        return cart.entrySet().stream()
                .map(entry -> BigDecimal.valueOf(entry.getKey().getPrice())
                        .multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getItemCount() {
        return cart.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void clearCart() {
        cart.clear();
    }
}