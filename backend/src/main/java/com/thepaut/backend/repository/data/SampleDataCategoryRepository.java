package com.thepaut.backend.repository.data;

import com.thepaut.backend.model.data.SampleDataCategory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SampleDataCategoryRepository extends GenericEntityRepository<SampleDataCategory> {


    /**
     * Rercheche toutes les catégorie par nom qui contiennent name et en ignorant la casse
     * @param name
     * @return
     */
    List<SampleDataCategory> findByNameContainingIgnoreCase(String name, Sort sort);

    /**
     * Retourne la dernière version de la catégorie dont l'id est categoryId
     * @param categoryId
     * @return
     */
    Optional<SampleDataCategory> findFirstByIdOrderByVersionDesc(Long categoryId);

}