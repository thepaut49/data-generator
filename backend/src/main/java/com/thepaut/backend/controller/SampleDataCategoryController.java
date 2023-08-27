package com.thepaut.backend.controller;

import com.thepaut.backend.dto.SampleDataCategoryCreationDto;
import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.dto.SampleDataDto;
import com.thepaut.backend.service.ISampleDataCategoryService;
import com.thepaut.backend.service.ISampleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://127.0.0.1:3000", "http://localhost:9090"}, maxAge = 3600)
@RestController
@RequestMapping(path = "api/sample-data-categories")
@RequiredArgsConstructor
public class SampleDataCategoryController {

    @Autowired
    private ISampleDataCategoryService sampleDataCategoryService;

    @Autowired
    private ISampleDataService sampleDataService;

    @PostMapping
    public ResponseEntity<SampleDataCategoryDto> create(@RequestBody @Valid SampleDataCategoryCreationDto sampleDataCategoryDto) {
        return new ResponseEntity<>(sampleDataCategoryService.createSampleDataCategory(sampleDataCategoryDto), HttpStatus.CREATED);
    }

    @PutMapping("{categoryName}")
    public ResponseEntity<SampleDataCategoryDto> update(@PathVariable String categoryName,  @RequestBody @Valid SampleDataCategoryCreationDto sampleDataCategoryDto) {
        return new ResponseEntity<>(sampleDataCategoryService.updateSampleDataCategory(categoryName, sampleDataCategoryDto), HttpStatus.OK);
    }

    @PutMapping("{categoryName}/rollback-to-previous-version")
    public ResponseEntity<SampleDataCategoryDto> rollbackToPreviousVersion(@PathVariable String categoryName) {
        return new ResponseEntity<>(sampleDataCategoryService.rollbackToPreviousVersion(categoryName), HttpStatus.OK);
    }

    @PutMapping("{categoryName}/rollback-to-version/{version}")
    public ResponseEntity<SampleDataCategoryDto> rollbackToPreviousVersion(@PathVariable String categoryName, @PathVariable Long version ) {
        return new ResponseEntity<>(sampleDataCategoryService.rollbackToVersion(categoryName, version), HttpStatus.OK);
    }

    @DeleteMapping("{categoryName}")
    public ResponseEntity<String> delete(@PathVariable String categoryName) {
        sampleDataCategoryService.deleteSampleDataCategoryByName(categoryName);
        return new ResponseEntity<>("Catégorie " + categoryName + " supprimée !", HttpStatus.OK);
    }

    @GetMapping("{categoryName}")
    public ResponseEntity<SampleDataCategoryDto> get(@PathVariable String categoryName) {
        return new ResponseEntity<>(sampleDataCategoryService.getSampleDataCategory(categoryName), HttpStatus.OK);
    }

    @GetMapping()
    public  ResponseEntity<List<SampleDataCategoryDto>> findSampleDataCategories(@RequestParam(required = false) String categoryName) {
        return new ResponseEntity<List<SampleDataCategoryDto>>(sampleDataCategoryService.getSampleDataCategories(categoryName), HttpStatus.OK);
    }

    @GetMapping("/search-sample-datas?categoryName={categoryName}&key={key}&value={value}&isBlobValue={isBlobValue}")
    public  ResponseEntity<List<SampleDataDto>> findSampleDatas(@RequestParam String categoryName, @RequestParam(required = false) String key, @RequestParam(required = false) String value, @RequestParam(required = false) boolean isBlobValue ) {
        return new ResponseEntity<List<SampleDataDto>>(sampleDataService.getSampleDatas(categoryName, key, value, isBlobValue), HttpStatus.OK);

    }
}
