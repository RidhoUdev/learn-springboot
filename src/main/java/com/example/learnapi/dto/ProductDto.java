package com.example.learnapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class ProductDto {

    @Size(min=3, message = "product name must be at least 3 character")
    @NotEmpty(message = "product name is required")
    private String productName;

    @NotNull(message = "price can not be empty")
    @Min(value = 0, message = "price cannot be contain negative")
    private String price;
}
