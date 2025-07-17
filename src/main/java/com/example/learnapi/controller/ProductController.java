package com.example.learnapi.controller;

import com.example.learnapi.dto.ProductDto;
import com.example.learnapi.dto.ResponseMessage;
import com.example.learnapi.model.Product;
import com.example.learnapi.service.FileStorageService;
import com.example.learnapi.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    ResponseMessage<Product> responseMessage = new ResponseMessage<Product>(null, "");

    @Autowired
    private ProductService productService;

    @Autowired
    private FileStorageService fileStorageService;

    private final List<String> aLLOWED_IMAGE_TYPES = Arrays.asList("image/jpg", "image/jpeg", "image/png");

    @PostMapping("/{id}/upload")
    public ResponseEntity<ResponseMessage> uploadImage(
            @PathVariable("id") Long id,
            @RequestParam("image") MultipartFile file
    ) {
        if(file.isEmpty()) {
            responseMessage.setMessage("Image file is required");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        String contentType = file.getContentType();
        if( contentType == null || !aLLOWED_IMAGE_TYPES.contains(contentType) ) {
            responseMessage.setMessage("Invalid image file type. Only JPG, PNG, GIF are allowed.");
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }

        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " fot found"));

        String fileName = fileStorageService.storeFile(file);

        product.setImage(fileName);
        productService.save(product);

        responseMessage.setData(product);
        responseMessage.setMessage("Product has been uploaded successfully");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> createProduct(@Valid @RequestBody ProductDto productDto) {

        Product newProduct = productService.createProduct(productDto);
        ResponseMessage<Product> responseMessage = new ResponseMessage<>(newProduct, "Product created successfully");
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
    public ResponseEntity<ResponseMessage> updateProduct(@PathVariable(value = "id") Long id, @Valid @RequestBody ProductDto productDto) {
        Product updatedProduct = productService.updateProduct(id, productDto);
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