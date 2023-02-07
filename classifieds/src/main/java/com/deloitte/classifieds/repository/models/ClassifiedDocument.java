package com.deloitte.classifieds.repository.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Document("classifieds")
public class ClassifiedDocument {
    @Id
    private String itemId;
    private String sellerId;
    private String name;
    private String category;
    private LocalDate purchaseDate;
    private double sellingPrice;
}
