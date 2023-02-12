package com.deloitte.classifieds.service;

import com.deloitte.classifieds.controllers.ClassifiedsController;
import com.deloitte.classifieds.controllers.models.Classified;
import com.deloitte.classifieds.exceptions.ClassifiedNotFoundException;
import com.deloitte.classifieds.external.RatingsClient;
import com.deloitte.classifieds.repository.ClassifiedsRepository;
import com.deloitte.classifieds.repository.models.ClassifiedDocument;
import com.deloitte.classifieds.service.mappers.ClassifiedsMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ClassifiedsService {
    private final ClassifiedsRepository classifiedsRepository;
    private final ClassifiedsMapper classifiedsMapper;
    private final RatingsClient ratingsClient;

    public ClassifiedsService(final ClassifiedsRepository classifiedsRepository, final ClassifiedsMapper classifiedsMapper, final RatingsClient ratingsClient) {
        this.classifiedsRepository = classifiedsRepository;
        this.classifiedsMapper = classifiedsMapper;
        this.ratingsClient = ratingsClient;
    }

    public Classified saveClassified(final Classified classified) {
        final ClassifiedDocument classifiedDocument = classifiedsMapper.classifiedToClassifiedDocument(classified);
        final ClassifiedDocument savedClassifiedDocument = classifiedsRepository.save(classifiedDocument);
        return classifiedsMapper.classifiedDocumentToClassified(savedClassifiedDocument);
    }

    public List<ClassifiedDocument> saveAllClassifieds(final List<Classified> classifiedsList) {
        final List<ClassifiedDocument> classifiedDocuments = classifiedsList.stream().map(classifiedsMapper::classifiedToClassifiedDocument).toList();
        return classifiedsRepository.saveAll(classifiedDocuments);
    }

    public Classified getClassifiedById(final String id) {
        final Optional<ClassifiedDocument> byId = classifiedsRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ClassifiedNotFoundException(format("Classified with id: %s not found", id));
        }
        final ClassifiedDocument classifiedDocument = byId.get();
        Map<String, String> aggregateRatingBySellerId = ratingsClient.getAggregateRatingBySellerId(classifiedDocument.getSellerId());

        final Classified classified = classifiedsMapper.classifiedDocumentToClassified(classifiedDocument);
        classified.setSellerRating(aggregateRatingBySellerId.get(aggregateRatingBySellerId.keySet().stream().findFirst().get()));
        classified.add(linkTo(methodOn(ClassifiedsController.class).getClassifiedById(classified.getItemId())).withSelfRel());
        classified.add(linkTo(methodOn(ClassifiedsController.class).getClassifiedsByFilters(0, 3, classified.getSellerId(), classified.getCategory(), "5")).withRel(IanaLinkRelations.COLLECTION));
        return classified;
    }

    public void deleteClassified(final String id) {
        classifiedsRepository.deleteById(id);
    }

    public Map<String, Object> findAllBySellerId(final String sellerId, final Pageable paging) {
        final Page<ClassifiedDocument> allBySellerId = classifiedsRepository.findAllBySellerId(sellerId, paging);
        return getPageMapFromDocuments(allBySellerId);
    }

    public Map<String, Object> findAllByCategory(final String category, final Pageable paging) {
        final Page<ClassifiedDocument> results = classifiedsRepository.findAllByCategory(category, paging);
        return getPageMapFromDocuments(results);
    }

    public Map<String, Object> findAllByCategoryAndFrom(final String category, final String from, final Pageable paging) {
        LocalDate date = LocalDate.now().minusYears(Long.parseLong(from));
        final Page<ClassifiedDocument> results = classifiedsRepository.findAllByCategoryAndPurchaseDateAfter(category, date, paging);
        return getPageMapFromDocuments(results);
    }

    public Map<String, Object> findAllBySellerIdAndCategoryAndFrom(final String sellerId, final String category, final String from, final Pageable paging) {
        LocalDate date = LocalDate.now().minusYears(Long.parseLong(from));
        final Page<ClassifiedDocument> results = classifiedsRepository.findAllBySellerIdAndCategoryAndPurchaseDateAfter(sellerId,category, date, paging);
        return getPageMapFromDocuments(results);
    }

    public Map<String, Object> getClassifiedsByPage(final Pageable paging) {
        final Page<ClassifiedDocument> classifiedDocuments = classifiedsRepository.findAll(paging);
        return getPageMapFromDocuments(classifiedDocuments);
    }

    private Map<String, Object> getPageMapFromDocuments(final Page<ClassifiedDocument> classifiedDocuments) {
        Map<String, Object> map = new HashMap<>();
        map.put("classifieds", convertDocumentsToDTOs(classifiedDocuments.getContent()));
        map.put("currentPage", classifiedDocuments.getNumber());
        map.put("totalItems", classifiedDocuments.getTotalElements());
        map.put("totalPages", classifiedDocuments.getTotalPages());
        return map;
    }

    private List<Classified> convertDocumentsToDTOs(final List<ClassifiedDocument> allBySellerId) {
        return allBySellerId.stream().map(classifiedsMapper::classifiedDocumentToClassified)
                .map(c -> c.add(linkTo(methodOn(ClassifiedsController.class).getClassifiedById(c.getItemId())).withSelfRel())).toList();
    }
}
