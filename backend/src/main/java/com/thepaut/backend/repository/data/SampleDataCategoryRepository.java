package com.thepaut.backend.repository.data;

import com.thepaut.backend.model.data.SampleDataCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SampleDataCategoryRepository extends JpaRepository<SampleDataCategory, Long> {

    /**
     * Rercheche toutes les catégorie par nom qui contiennent name et en ignorant la casse
     * @param name
     * @return
     */
    List<SampleDataCategory> findByNameContainingIgnoreCase(String name);

    /**
     * Retourne la version de la catégorie avec le name categoryName si elle existe
     * @param categoryName
     * @param version
     * @return
     */
    Optional<SampleDataCategory> findByNameAndVersion(String categoryName, Long version);

    /**
     * Retourne la dernière version de la catégorie dont le nom est categoryName
     * @param categoryName
     * @return
     */
    Optional<SampleDataCategory> findFirstByNameOrderByVersionDesc(String categoryName);

    /**
     * Récupère l'ensemble des version de la catégorie categoryName
     * @param categoryName
     * @return
     */
    List<SampleDataCategory> findByNameOrderByVersionDesc(String categoryName);

    /**
     * Récupère toutes les catégories avec le nom categoryName et une version inférieure ou égale à version
     * @param categoryName
     * @param version
     * @return
     */
    List<SampleDataCategory> findByNameAndVersionLessThanEqualOrderByVersionDesc(String categoryName, Long version);

    /**
     * Suppression d'une catégorie et de toutes ses versions par son nom
     * @param categoryName
     * @return
     */
    Long deleteByName(String categoryName);

    /**
     * Supprime toutes les catégorie dont le nom est categoryName et la version est supérieur strictement à version
     * @param categoryName
     * @param version
     * @return
     */
    Long deleteByNameAndVersionGreaterThan(String categoryName, Long version);

    /**
     * Supprime la dernière version de la catégorie categoryName
     * @param categoryName
     * @return
     */
    Long deleteFirstByNameOrderByVersionDesc(String categoryName);

}