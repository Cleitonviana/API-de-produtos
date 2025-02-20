package com.example.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.products.models.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long>{

}
