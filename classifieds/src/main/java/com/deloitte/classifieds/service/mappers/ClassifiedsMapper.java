package com.deloitte.classifieds.service.mappers;

import com.deloitte.classifieds.controllers.models.Classified;
import com.deloitte.classifieds.repository.models.ClassifiedDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassifiedsMapper {
    ClassifiedDocument classifiedToClassifiedDocument (Classified classified);

    Classified classifiedDocumentToClassified(ClassifiedDocument classified);
}
