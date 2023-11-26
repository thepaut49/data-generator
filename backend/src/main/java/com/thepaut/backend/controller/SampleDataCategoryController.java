package com.thepaut.backend.controller;

import com.thepaut.backend.dto.SampleDataCategoryCreationDto;
import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.dto.SampleDataDto;
import com.thepaut.backend.service.ISampleDataService;
import com.thepaut.backend.service.SampleDataCategoryService;
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

    private final SampleDataCategoryService sampleDataCategoryService;

    private final ISampleDataService sampleDataService;


    @PostMapping
    public ResponseEntity<SampleDataCategoryDto> create(@RequestBody @Valid SampleDataCategoryCreationDto sampleDataCategoryDto) {
        SampleDataCategoryDto createdDto = (SampleDataCategoryDto) sampleDataCategoryService.createEntity(sampleDataCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDto);
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<SampleDataCategoryDto> update(@PathVariable Long categoryId,  @RequestBody @Valid SampleDataCategoryDto sampleDataCategoryDto) {
        SampleDataCategoryDto updatedDto = (SampleDataCategoryDto) sampleDataCategoryService.updateEntity(categoryId, sampleDataCategoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @PutMapping("{categoryId}/rollback-to-previous-version")
    public ResponseEntity<SampleDataCategoryDto> rollbackToPreviousVersion(@PathVariable Long categoryId) {
        SampleDataCategoryDto updatedDto = (SampleDataCategoryDto) sampleDataCategoryService.rollbackToPreviousVersion(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @PutMapping("{categoryId}/rollback-to-version/{version}")
    public ResponseEntity<SampleDataCategoryDto> rollbackToPreviousVersion(@PathVariable Long categoryId, @PathVariable Long version ) {
        SampleDataCategoryDto updatedDto = (SampleDataCategoryDto) sampleDataCategoryService.rollbackToVersion(categoryId, version);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<String> delete(@PathVariable Long categoryId) {
        sampleDataCategoryService.deleteById(categoryId);
        return new ResponseEntity<>("Catégorie " + categoryId + " supprimée !", HttpStatus.OK);
    }

    @GetMapping("{categoryId}")
    public ResponseEntity<SampleDataCategoryDto> get(@PathVariable Long categoryId) {
        SampleDataCategoryDto updatedDto = (SampleDataCategoryDto) sampleDataCategoryService.getEntityById(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    @GetMapping()
    public  ResponseEntity<List<SampleDataCategoryDto>> findSampleDataCategories(@RequestParam(required = false) String categoryName) {
        List<SampleDataCategoryDto> sampleDataCategoryDtos = sampleDataCategoryService.getSampleDataCategories(categoryName);
        return ResponseEntity.status(HttpStatus.OK).body(sampleDataCategoryDtos);
    }

    @GetMapping("/search-sample-datas?categoryId={categoryId}&key={key}&value={value}&isBlobValue={isBlobValue}")
    public  ResponseEntity<List<SampleDataDto>> findSampleDatas(@RequestParam Long categoryId, @RequestParam(required = false) String key, @RequestParam(required = false) String value, @RequestParam(required = false) boolean isBlobValue ) {
        return new ResponseEntity<>(sampleDataService.getSampleDatas(categoryId, key, value, isBlobValue), HttpStatus.OK);

    }
}
