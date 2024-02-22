package com.thepaut.backend.controller;

import com.thepaut.backend.dto.SampleDataCategoryCreationDto;
import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.service.SampleDataCategoryService;
import com.thepaut.backend.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://127.0.0.1:3000"}, maxAge = 3600)
@RestController
@RequestMapping(path = Constants.BASE_PATH_SAMPLE_DATA_CATEGORY)
@RequiredArgsConstructor
public class SampleDataCategoryController {

    private final SampleDataCategoryService sampleDataCategoryService;


    @PostMapping
    public ResponseEntity<SampleDataCategoryDto> create(HttpServletRequest request, @RequestBody @Valid SampleDataCategoryCreationDto sampleDataCategoryDto) {
        SampleDataCategoryDto createdDto = sampleDataCategoryService.createEntity(request.getRequestURI(), sampleDataCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<SampleDataCategoryDto> update(HttpServletRequest request, @PathVariable Long categoryId,  @RequestBody @Valid SampleDataCategoryDto sampleDataCategoryDto) {
        SampleDataCategoryDto updatedDto = sampleDataCategoryService.updateEntity(request.getRequestURI(), categoryId, sampleDataCategoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @PutMapping("{categoryId}/rollback-to-previous-version")
    public ResponseEntity<SampleDataCategoryDto> rollbackToPreviousVersion(HttpServletRequest request, @PathVariable Long categoryId) {
        SampleDataCategoryDto updatedDto = sampleDataCategoryService.rollbackToPreviousVersion(request.getRequestURI(), categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @PutMapping("{categoryId}/rollback-to-version/{version}")
    public ResponseEntity<SampleDataCategoryDto> rollbackToPreviousVersion(HttpServletRequest request, @PathVariable Long categoryId, @PathVariable Long version ) {
        SampleDataCategoryDto updatedDto = sampleDataCategoryService.rollbackToVersion(request.getRequestURI(), categoryId, version);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<String> delete(@PathVariable Long categoryId) {
        sampleDataCategoryService.deleteById(categoryId);
        return new ResponseEntity<>("Catégorie " + categoryId + " supprimée !", HttpStatus.OK);
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<SampleDataCategoryDto> get(HttpServletRequest request, @PathVariable Long categoryId) {
        SampleDataCategoryDto updatedDto = sampleDataCategoryService.getEntityById(request.getRequestURI(), categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @GetMapping()
    public  ResponseEntity<List<SampleDataCategoryDto>> findSampleDataCategories(@RequestParam(required = false) String categoryName) {
        List<SampleDataCategoryDto> sampleDataCategoryDtos = sampleDataCategoryService.getSampleDataCategories( categoryName);
        return ResponseEntity.status(HttpStatus.OK).body(sampleDataCategoryDtos);
    }
}
