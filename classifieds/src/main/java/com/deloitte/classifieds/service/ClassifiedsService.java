package com.deloitte.classifieds.service;

import com.deloitte.classifieds.controllers.models.Classified;
import com.deloitte.classifieds.exceptions.ClassifiedNotFoundException;
import com.deloitte.classifieds.repository.ClassifiedsRepository;
import com.deloitte.classifieds.repository.models.ClassifiedDocument;
import com.deloitte.classifieds.service.mappers.ClassifiedsMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class ClassifiedsService {
    private final ClassifiedsRepository classifiedsRepository;
    private final ClassifiedsMapper classifiedsMapper;

    public ClassifiedsService(final ClassifiedsRepository classifiedsRepository, final ClassifiedsMapper classifiedsMapper) {
        this.classifiedsRepository = classifiedsRepository;
        this.classifiedsMapper = classifiedsMapper;
    }

    public Classified saveClassified(final Classified classified) {
        final ClassifiedDocument classifiedDocument = classifiedsMapper.classifiedToClassifiedDocument(classified);
        final ClassifiedDocument savedClassifiedDocument = classifiedsRepository.save(classifiedDocument);
        return classifiedsMapper.classifiedDocumentToClassified(savedClassifiedDocument);
    }

    public List<ClassifiedDocument> saveAllClassifieds(final List<Classified> classifiedsList) {
        final List<ClassifiedDocument> classifiedDocuments = classifiedsList.stream().map(c -> classifiedsMapper.classifiedToClassifiedDocument(c)).toList();
        return classifiedsRepository.saveAll(classifiedDocuments);
    }

    public Classified getClassifiedById(final String id) {
        final Optional<ClassifiedDocument> byId = classifiedsRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ClassifiedNotFoundException(format("Classified with id: %s not found", id));
        }
        final ClassifiedDocument classifiedDocument = byId.get();
        return classifiedsMapper.classifiedDocumentToClassified(classifiedDocument);
    }

    public void deleteClassified(final String id) {
        classifiedsRepository.deleteById(id);
    }

    public List<Classified> findAllBySellerId(final String sellerId) {
        final List<ClassifiedDocument> allBySellerId = classifiedsRepository.findAllBySellerId(sellerId);
        return convertDocumentsToDTOs(allBySellerId);
    }

    public List<Classified> findAllByCategory(final String category) {
        final List<ClassifiedDocument> results = classifiedsRepository.findAllByCategory(category);
        return convertDocumentsToDTOs(results);
    }

    public List<Classified> findAllByCategoryAndFrom(final String category, final String from) {
        LocalDate date = LocalDate.now().minusYears(Long.valueOf(from));
        final List<ClassifiedDocument> allByCategoryAndPurchaseDateAfter = classifiedsRepository.findAllByCategoryAndPurchaseDateAfter(category, date);
        return convertDocumentsToDTOs(allByCategoryAndPurchaseDateAfter);
    }

    public Map<String, Object> getClassifiedsByPage(final Pageable paging) {
        final Page<ClassifiedDocument> classifiedDocuments = classifiedsRepository.findAll(paging);
        Map<String,Object> map = new HashMap<>();
        map.put("classifieds", convertDocumentsToDTOs(classifiedDocuments.getContent()));
        map.put( "currentPage",classifiedDocuments.getNumber());
        map.put( "totalItems",classifiedDocuments.getTotalElements());
        map.put( "totalPages",classifiedDocuments.getTotalPages());
        return map;
    }

    private List<Classified> convertDocumentsToDTOs(final List<ClassifiedDocument> allBySellerId) {
        return allBySellerId.stream().map(d -> classifiedsMapper.classifiedDocumentToClassified(d)).toList();
    }
}
