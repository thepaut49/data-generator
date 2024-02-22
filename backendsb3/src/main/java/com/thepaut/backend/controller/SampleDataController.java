package com.thepaut.backend.controller;

import com.thepaut.backend.dto.SampleDataCreationDto;
import com.thepaut.backend.dto.SampleDataDto;
import com.thepaut.backend.service.SampleDataService;
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
@RequestMapping(path = Constants.BASE_PATH_SAMPLE_DATA)
@RequiredArgsConstructor
public class SampleDataController {

    private final SampleDataService sampleDataService;

    @PostMapping
    public ResponseEntity<SampleDataDto> create(HttpServletRequest request, @RequestBody @Valid SampleDataCreationDto sampleDataDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sampleDataService.createEntity(request.getRequestURI(), sampleDataDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<SampleDataDto> update(HttpServletRequest request, @PathVariable Long id, @RequestBody @Valid SampleDataDto sampleDataDto) {
        return ResponseEntity.status(HttpStatus.OK).body(sampleDataService.updateEntity(request.getRequestURI(), id, sampleDataDto));
    }

    @PutMapping("{id}/rollback")
    public ResponseEntity<SampleDataDto> rollbackToPreviousVersion(HttpServletRequest request, @PathVariable Long id) {
        return  ResponseEntity.status(HttpStatus.OK).body(sampleDataService.rollbackToPreviousVersion(request.getRequestURI(), id));
    }

    @PutMapping("{id}/rollback/{version}")
    public ResponseEntity<SampleDataDto> rollbackToVersion(HttpServletRequest request, @PathVariable Long id, @PathVariable Long version ) {
        return ResponseEntity.status(HttpStatus.OK).body(sampleDataService.rollbackToVersion(request.getRequestURI(), id, version));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        sampleDataService.deleteById(id);
        return new ResponseEntity<>("Donnée " + id +  " supprimée !", HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<SampleDataDto> get(HttpServletRequest request, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sampleDataService.getEntityById(request.getRequestURI(), id));
    }

    @GetMapping()
    public  ResponseEntity<List<SampleDataDto>> findSampleDatas( @RequestParam(required = false) Long categoryId, @RequestParam(required = false) String key, @RequestParam(required = false) String value, @RequestParam(required = false) Boolean isBlobValue ) {
        return ResponseEntity.status(HttpStatus.OK).body(sampleDataService.getSampleDatas(categoryId, key, value, isBlobValue));
    }
}
