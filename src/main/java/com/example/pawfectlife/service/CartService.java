package com.example.pawfectlife.service;

import com.example.pawfectlife.model.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {

    private Map<Product, Integer> cart = new HashMap<>();

    public void addProduct(Product product) {
        if (cart.containsKey(product)) {
            cart.put(product, cart.get(product) + 1);
        } else {
            cart.put(product, 1);
        }
    }

    public void removeProduct(Product product) {
        cart.remove(product);
    }

    public Map<Product, Integer> getProductsInCart() {
        return cart;
    }

    public double getTotal() {
        return cart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public int getItemCount() {
        return cart.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void clearCart() {
        cart.clear();
    }

    public void updateProductQuantity(Product product, int quantity) {
        cart.put(product, quantity);
    }
}