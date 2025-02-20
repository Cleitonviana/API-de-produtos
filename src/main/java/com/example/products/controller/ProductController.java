package com.example.products.controller;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.products.dtos.ProductDto;
import com.example.products.models.ProductModel;
import com.example.products.repositories.ProductRepository;

import jakarta.validation.Valid;

@RestController
public class ProductController {

	
	@Autowired
	ProductRepository productRepository;
	
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductModel>> getAllProducts(){
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
	}
	@GetMapping("/products/{id}")
	public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") Long id){
		Optional<ProductModel> product0 = productRepository.findById(id);
		if(product0.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(product0.get());
		
	}
	
	@PostMapping("/products")
	public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductDto productDto){
		var productModel = new ProductModel();
		BeanUtils.copyProperties(productDto, productModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
	}
	
	
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable(value="id") Long id, @RequestBody @Valid ProductDto productDto){
		Optional<ProductModel> product0 = productRepository.findById(id);
		if(product0.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found.");
		}
		var productModel = product0.get();
		BeanUtils.copyProperties(productDto, productModel);
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") Long id){
		Optional<ProductModel> product0 = productRepository.findById(id);
		if(product0.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		productRepository.delete(product0.get());
		return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
	}
	
}
