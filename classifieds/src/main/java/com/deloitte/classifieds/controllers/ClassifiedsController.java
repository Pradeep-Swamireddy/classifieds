package com.deloitte.classifieds.controllers;

import com.deloitte.classifieds.controllers.models.Classified;
import com.deloitte.classifieds.service.ClassifiedsService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;

@RestController
@Log4j2
public class ClassifiedsController {

    private final ClassifiedsService classifiedService;

    public ClassifiedsController(final ClassifiedsService classifiedService) {
        this.classifiedService = classifiedService;
    }

    @PostMapping("/classifieds")
    public ResponseEntity<Object> postClassified(@Valid @RequestBody Classified classified) {
        log.info("Creating classified: {}", classified);
        final Classified savedClassified = classifiedService.saveClassified(classified);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedClassified.getItemId()).toUri()).build();
    }

    @PutMapping("/classifieds/{id}")
    public ResponseEntity<Object> putClassified(@PathVariable String id, @Valid @RequestBody Classified classified) {
        log.info("Updating classified: {}", classified);
        classified.setItemId(id);
        final Classified savedClassified = classifiedService.saveClassified(classified);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(savedClassified);
    }

    @DeleteMapping("/classifieds/{id}")
    public ResponseEntity<Object> deleteClassified(@PathVariable String id) {
        log.info("Deleting classified with id: {}", id);
        classifiedService.deleteClassified(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/classifieds/{id}")
    public Classified getClassifiedById(@PathVariable String id) {
        log.info("Getting classified with id: {}", id);
        return classifiedService.getClassifiedById(id);
    }

    @GetMapping("/classifieds")
    public Map<String, Object> getClassifiedsByFilters(@RequestParam(defaultValue = "0", required = false) int page,
                                                       @RequestParam(defaultValue = "3", required = false) int size,
                                                       @RequestParam(required = false) String sellerId,
                                                       @RequestParam(required = false) String category,
                                                       @RequestParam(required = false) String from) {
        log.info("Getting classified with seller: {},category: {}, from: {}, page: {}, size: {}", sellerId, category, from, page, size);
        Pageable paging = PageRequest.of(page, size);
        if (sellerId != null) {
            return classifiedService.findAllBySellerId(sellerId, paging);
        } else if (category != null && from != null) {
            return classifiedService.findAllByCategoryAndFrom(category, from, paging);
        } else if (category != null) {
            return classifiedService.findAllByCategory(category, paging);
        } else {
            return classifiedService.getClassifiedsByPage(paging);
        }
    }
}
