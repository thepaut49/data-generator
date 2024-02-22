package com.thepaut.backend.service;

import com.thepaut.backend.dto.SampleDataCategoryDto;

import java.util.List;

public interface ISampleDataCategoryService {

    /**
     * Récupère la liste des catégories et filtre par nom si categoryName != null
     * @param categoryName critère de recherche
     * @return
     */
    List<SampleDataCategoryDto> getSampleDataCategories(String categoryName) ;

}
