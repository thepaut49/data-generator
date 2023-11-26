package com.thepaut.backend.controller;

import com.thepaut.backend.dto.ListResponse;
import com.thepaut.backend.dto.SampleDataDto;
import com.thepaut.backend.service.ISampleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/sample-data-catgeories/{categoryName}/sample-datas")
@RequiredArgsConstructor
public class SampleDataController {

    @Autowired
    private ISampleDataService sampleDataService;

    @PostMapping
    public SampleDataDto create(@PathVariable Long categoryId, @RequestBody @Valid SampleDataDto sampleDataDto) {
        return sampleDataService.createSampleData(categoryId, sampleDataDto);
    }

    @PutMapping("{key}")
    public SampleDataDto update(@PathVariable Long categoryId, @PathVariable String key, @RequestBody @Valid SampleDataDto sampleDataDto) {
        return sampleDataService.updateSampleData(categoryId, key, sampleDataDto);
    }

    @PutMapping("{key}/rollback")
    public SampleDataDto rollbackToPreviousVersion(@PathVariable Long categoryId, @PathVariable String key) {
        return sampleDataService.rollbackToPreviousVersion(categoryId, key);
    }

    @PutMapping("{key}/rollback/{version}")
    public SampleDataDto rollbackToPreviousVersion(@PathVariable Long categoryId, @PathVariable String key, @PathVariable Long version ) {
        return sampleDataService.rollbackToVersion(categoryId, key, version);
    }

    @DeleteMapping("{key}")
    public ResponseEntity<String> delete(@PathVariable Long categoryId, @PathVariable String key) {
        sampleDataService.deleteSampleDataByCategoryIdAndKey(categoryId, key);
        return new ResponseEntity<>("Donnée " + key +  " supprimée !", HttpStatus.OK);
    }

    @GetMapping("{key}")
    public SampleDataDto get(@PathVariable Long categoryId, @PathVariable String key) {
        return sampleDataService.getSampleData(categoryId, key);
    }

    @GetMapping()
    public ListResponse<SampleDataDto> findSampleDatas(@PathVariable Long categoryId, @RequestParam(required = false) String key, @RequestParam(required = false) String value, @RequestParam(required = false) boolean isBlobValue ) {
        return new ListResponse<>(sampleDataService.getSampleDatas(categoryId, key, value, isBlobValue));
    }
}
