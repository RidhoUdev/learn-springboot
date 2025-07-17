package com.example.learnapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name= "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "required")
    @Size(min = 3, message = "product name must be at least 3 character")
    @Column(name = "product_Name")
    private String productName;

    @NotNull(message = "price can not null")
    @Min(value = 0, message = "price cannot be negative")
    @Column(name = "harga")
    private String price;
}
