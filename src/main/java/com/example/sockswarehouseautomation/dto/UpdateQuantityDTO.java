package com.example.sockswarehouseautomation.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateQuantityDTO {
    @NotNull
    private String color;
    @NotNull
    @Min(value = 0, message = "cotton part must be between 0 and 100")
    @Max(value = 100, message = "cotton part must be between 0 and 100")
    private int cottonPart;
    @NotNull
    @Min(value = 0, message = "quantity must be positive")
    private int quantity;
}