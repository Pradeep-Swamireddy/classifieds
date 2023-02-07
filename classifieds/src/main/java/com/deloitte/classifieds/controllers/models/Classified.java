package com.deloitte.classifieds.controllers.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class Classified {
    private String itemId;
    @NotEmpty
    private String sellerId;
    @NotEmpty
    private String name;
    private String category;
    private LocalDate purchaseDate;
    @NotNull
    private Double sellingPrice;
}
