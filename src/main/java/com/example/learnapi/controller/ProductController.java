package com.example.learnapi.controller;

import com.example.learnapi.dto.ResponseMessage;
import com.example.learnapi.model.Model;
import com.example.learnapi.model.Product;
import com.example.learnapi.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    ResponseMessage<Product> responseMessage = new ResponseMessage<Product>(null, "");

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseMessage> createProduct(@Valid @RequestBody Product product) {
        productService.createProduct(product);
        responseMessage.setData(product);
        responseMessage.setMessage("Product successfully Created");
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateProduct(@PathVariable(value = "id") Long id, @Valid @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        responseMessage.setData(updatedProduct);
        responseMessage.setMessage("Product updated successfully");
        return ResponseEntity.ok(responseMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteProduct(@PathVariable(value = "id") Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isEmpty()) {
            responseMessage.setMessage("Product not found");
            return ResponseEntity.notFound().build();
        }
        productService.deleteProduct(id);
        responseMessage.setData(product.get());
        responseMessage.setMessage("Product deleted successfully");
        return ResponseEntity.ok(responseMessage);
    }
}