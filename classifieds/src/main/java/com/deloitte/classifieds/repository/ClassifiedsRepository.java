package com.deloitte.classifieds.repository;

import com.deloitte.classifieds.repository.models.ClassifiedDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClassifiedsRepository extends MongoRepository<ClassifiedDocument, String>, PagingAndSortingRepository<ClassifiedDocument, String> {
    List<ClassifiedDocument> findAllByCategory(String category);
    List<ClassifiedDocument> findAllByCategoryAndPurchaseDateAfter(String category, LocalDate date);
    List<ClassifiedDocument> findAllBySellerId(String sellerId);
    Page<ClassifiedDocument> findAll(Pageable pageable);
}
