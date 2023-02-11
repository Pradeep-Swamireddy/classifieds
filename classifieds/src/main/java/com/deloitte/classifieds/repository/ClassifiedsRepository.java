package com.deloitte.classifieds.repository;

import com.deloitte.classifieds.repository.models.ClassifiedDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClassifiedsRepository extends MongoRepository<ClassifiedDocument, String>, PagingAndSortingRepository<ClassifiedDocument, String> {
    Page<ClassifiedDocument> findAllByCategory(String category, Pageable pageable);
    Page<ClassifiedDocument> findAllByCategoryAndPurchaseDateAfter(String category, LocalDate date, Pageable pageable);
    Page<ClassifiedDocument> findAllBySellerId(String sellerId, Pageable pageable);
    Page<ClassifiedDocument> findAll(Pageable pageable);
}
