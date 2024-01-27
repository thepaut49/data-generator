package com.thepaut.backend.service;

import com.thepaut.backend.dto.SampleDataDto;

import java.util.List;

public interface ISampleDataService {

    /**
     *      * Récupère la liste des données et filtre par categoryId, key, value, isBlobTypeValue
     * @param categoryId
     * @param key
     * @param value
     * @param isBlobValue
     * @return
     */
    List<SampleDataDto> getSampleDatas(Long categoryId, String key, String value, Boolean isBlobValue) ;

}
