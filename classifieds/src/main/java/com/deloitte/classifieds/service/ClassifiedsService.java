package com.deloitte.classifieds.service;

import com.deloitte.classifieds.controllers.models.Classified;
import com.deloitte.classifieds.exceptions.ClassifiedNotFoundException;
import com.deloitte.classifieds.repository.ClassifiedsRepository;
import com.deloitte.classifieds.repository.models.ClassifiedDocument;
import com.deloitte.classifieds.service.mappers.ClassifiedsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<ClassifiedDocument> saveAllClassifieds(final List<Classified> classifiedsList){
        final List<ClassifiedDocument> classifiedDocuments = classifiedsList.stream().map(c -> classifiedsMapper.classifiedToClassifiedDocument(c)).toList();
        return classifiedsRepository.saveAll(classifiedDocuments);
    }

    public Classified getClassifiedById(final String id) {
        final Optional<ClassifiedDocument> byId = classifiedsRepository.findById(id);
        if(byId.isEmpty()){
            throw new ClassifiedNotFoundException(format("Classified with id: %s not found",id));
        }
        final ClassifiedDocument classifiedDocument = byId.get();
        return classifiedsMapper.classifiedDocumentToClassified(classifiedDocument);
    }

    public void deleteClassified(final String id) {
        classifiedsRepository.deleteById(id);
    }
}
