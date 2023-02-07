package com.deloitte.classifieds.repository;

import com.deloitte.classifieds.repository.models.ClassifiedDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClassifiedsRepository extends MongoRepository<ClassifiedDocument, String> {
}
