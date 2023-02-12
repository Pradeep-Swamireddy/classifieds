package com.deloitte.classifieds.controllers.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Classified extends RepresentationModel<Classified> {
    private String itemId;
    @NotEmpty
    private String sellerId;
    @NotEmpty
    private String name;
    private String category;
    private LocalDate purchaseDate;
    @NotNull
    private Double sellingPrice;
    private String sellerRating;
}
