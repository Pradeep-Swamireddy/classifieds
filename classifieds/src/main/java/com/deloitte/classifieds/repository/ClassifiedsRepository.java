package com.deloitte.classifieds.repository;

import com.deloitte.classifieds.repository.models.ClassifiedDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClassifiedsRepository extends MongoRepository<ClassifiedDocument, String>, PagingAndSortingRepository<ClassifiedDocument, String> {
}
