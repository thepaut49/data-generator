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

    /**
     * Récupère une catégorie par son nom
     * @param categoryName
     * @return
     */
    SampleDataCategoryDto getSampleDataCategory(String categoryName) ;

    /**
     * On supprime la dernière version de la catégorie et on récupère la version précédente
     * @param categoryName
     * @return
     */
    SampleDataCategoryDto rollbackToPreviousVersion(String categoryName);

    /**
     * On supprime toutes les versions de la catégorie qui sont suppérieur au paramètre version et on récupère la version
     * numéro version
     * @param categoryName
     * @param version à récupérer
     * @return
     */
    SampleDataCategoryDto rollbackToVersion(String categoryName, Long version);

    /**
     * Sauvegarde une nouvelle catégorie
     * @param sampleDataCategoryDto
     * @return
     */
    SampleDataCategoryDto createSampleDataCategory(SampleDataCategoryDto sampleDataCategoryDto);

    /**
     * Mise à jour de la catégorie, on crée un enregistrement avec un numéro de version + 1 sans modifier l'ancienne version
     * pour pouvoir faire un rollback si besoin et avoir un historique
     * @param sampleDataCategoryDto
     * @return
     */
    SampleDataCategoryDto updateSampleDataCategory(String categoryName, SampleDataCategoryDto sampleDataCategoryDto);

    /**
     * Suppression de la catégorie
     * @param categoryName
     * @return
     */
    boolean deleteSampleDataCategoryByName(String categoryName);
}
