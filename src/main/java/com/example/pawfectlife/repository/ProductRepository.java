package com.example.pawfectlife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pawfectlife.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceLessThan(double maxPrice);
}