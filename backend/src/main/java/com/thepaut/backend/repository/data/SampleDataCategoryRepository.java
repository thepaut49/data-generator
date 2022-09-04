package com.thepaut.backend.repository.data;



import com.thepaut.backend.model.data.SampleDataCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SampleDataCategoryRepository extends JpaRepository<SampleDataCategory, Long> {

    Optional<SampleDataCategory> findByName(String name);

    List<SampleDataCategory> findByNameContainingIgnoreCase(String name);

    Optional<SampleDataCategory> findByNameAndVersion(String categoryName, Long version);

    Optional<SampleDataCategory> findFirstByNameOrderByVersionDesc(String categoryName);

    List<SampleDataCategory> findByCategoryNameOrderByVersionDesc(String categoryName);

    List<SampleDataCategory> findByCategoryNameAndVersionLessThanEqualOrderByVersionDesc(String categoryName, Long version);

    Long deleteByName(String categoryName);

    Long deleteByNameAndVersionGreaterThan(String categoryName, Long version);

    Long deleteFirstByNameOrderByVersionDesc(String categoryName);

}