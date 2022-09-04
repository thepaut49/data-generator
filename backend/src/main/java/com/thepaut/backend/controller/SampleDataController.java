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
    public SampleDataDto create(@RequestBody @Valid SampleDataDto sampleDataDto) {
        return sampleDataService.createSampleData(sampleDataDto);
    }

    @PutMapping("{id}")
    public SampleDataDto update(@RequestBody @Valid SampleDataDto sampleDataDto) {
        return sampleDataService.updateSampleData(sampleDataDto);
    }

    @PutMapping("{id}/rollback")
    public SampleDataDto rollbackToPreviousVersion() {
        return sampleDataService.rollbackToPreviousVersion();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable String sampleDataCategoryName, @PathVariable Long sampleDataId) {
        sampleDataService.deleteSampleData(sampleDataId);
        return new ResponseEntity<>("Donnée supprimée !", HttpStatus.OK);
    }

    @GetMapping("{id}")
    public SampleDataDto get(@PathVariable Long categoryId) {
        return sampleDataService.getSampleData(categoryId);
    }

    @GetMapping()
    public ListResponse<SampleDataDto> findSampleDatas(@RequestParam(required = false) String categoryName, @RequestParam(required = false) String key, @RequestParam(required = false) String value,  @RequestParam(required = false) boolean isLobValue ) {
        return new ListResponse<>(sampleDataService.getSampleDatas(categoryName, key, value, isLobValue));
    }
}
