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
    public SampleDataDto create(@PathVariable String categoryName, @RequestBody @Valid SampleDataDto sampleDataDto) {
        return sampleDataService.createSampleData(categoryName, sampleDataDto);
    }

    @PutMapping("{key}")
    public SampleDataDto update(@PathVariable String categoryName, @PathVariable String key, @RequestBody @Valid SampleDataDto sampleDataDto) {
        return sampleDataService.updateSampleData(categoryName, key, sampleDataDto);
    }

    @PutMapping("{key}/rollback")
    public SampleDataDto rollbackToPreviousVersion(@PathVariable String categoryName, @PathVariable String key) {
        return sampleDataService.rollbackToPreviousVersion(categoryName, key);
    }

    @PutMapping("{key}/rollback/{version}")
    public SampleDataDto rollbackToPreviousVersion(@PathVariable String categoryName, @PathVariable String key, @PathVariable Long version ) {
        return sampleDataService.rollbackToVersion(categoryName, key, version);
    }

    @DeleteMapping("{key}")
    public ResponseEntity<String> delete(@PathVariable String categoryName, @PathVariable String key) {
        sampleDataService.deleteSampleDataByCategoryNameAndKey(categoryName, key);
        return new ResponseEntity<>("Donnée " + key +  " supprimée !", HttpStatus.OK);
    }

    @GetMapping("{key}")
    public SampleDataDto get(@PathVariable String categoryName, @PathVariable String key) {
        return sampleDataService.getSampleData(categoryName, key);
    }

    @GetMapping()
    public ListResponse<SampleDataDto> findSampleDatas(@PathVariable String categoryName, @RequestParam(required = false) String key, @RequestParam(required = false) String value,  @RequestParam(required = false) boolean isBlobValue ) {
        return new ListResponse<>(sampleDataService.getSampleDatas(categoryName, key, value, isBlobValue));
    }
}
