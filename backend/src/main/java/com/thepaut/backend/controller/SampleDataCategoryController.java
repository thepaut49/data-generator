package com.thepaut.backend.controller;

import com.thepaut.backend.dto.ListResponse;
import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.service.ISampleDataCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/sample-data-categories")
@RequiredArgsConstructor
public class SampleDataCategoryController {

    @Autowired
    private ISampleDataCategoryService sampleDataCategoryService;

    @PostMapping
    public SampleDataCategoryDto create(@RequestBody @Valid SampleDataCategoryDto sampleDataCategoryDto) {
        return sampleDataCategoryService.createSampleDataCategory(sampleDataCategoryDto);
    }

    @PutMapping("{categoryName}")
    public SampleDataCategoryDto update(@PathVariable String categoryName,  @RequestBody @Valid SampleDataCategoryDto sampleDataCategoryDto) {
        return sampleDataCategoryService.updateSampleDataCategory(categoryName, sampleDataCategoryDto);
    }

    @PutMapping("{categoryName}/rollback-to-previous-version")
    public SampleDataCategoryDto rollbackToPreviousVersion(@PathVariable String categoryName) {
        return sampleDataCategoryService.rollbackToPreviousVersion(categoryName);
    }

    @PutMapping("{categoryName}/rollback-to-version/{version}")
    public SampleDataCategoryDto rollbackToPreviousVersion(@PathVariable String categoryName, @PathVariable Long version ) {
        return sampleDataCategoryService.rollbackToVersion(categoryName, version);
    }

    @DeleteMapping("{categoryName}")
    public ResponseEntity<String> delete(@PathVariable String categoryName) {
        sampleDataCategoryService.deleteSampleDataCategoryByName(categoryName);
        return new ResponseEntity<>("Catégorie " + categoryName + " supprimée !", HttpStatus.OK);
    }

    @GetMapping("{categoryName}")
    public SampleDataCategoryDto get(@PathVariable String categoryName) {
        return sampleDataCategoryService.getSampleDataCategory(categoryName);
    }

    @GetMapping()
    public ListResponse<SampleDataCategoryDto> findSampleDataCategories(@RequestParam(required = false) String categoryName) {
        return new ListResponse<>(sampleDataCategoryService.getSampleDataCategories(categoryName));
    }
}