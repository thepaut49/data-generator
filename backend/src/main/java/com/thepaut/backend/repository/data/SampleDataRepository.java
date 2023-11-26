package com.thepaut.backend.repository.data;

import com.thepaut.backend.model.data.SampleData;
import com.thepaut.backend.model.data.SampleDataCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SampleDataRepository extends JpaRepository<SampleData, Long> {

    // catagory + key + value
    List<SampleData> findByCategoryAndKeyContainingIgnoreCaseAndValueContainingIgnoreCase(SampleDataCategory category, String key, String value, Sort sort);

    // catagory + key + blobvalue
    List<SampleData> findByCategoryAndKeyContainingIgnoreCaseAndBlobValueContainingIgnoreCase(SampleDataCategory category, String key, String blobValue, Sort sort);

    // catagory + key
    List<SampleData> findByCategoryAndKeyContainingIgnoreCase(SampleDataCategory category, String key, Sort sort);

    // catagory + blobValue
    List<SampleData> findByCategoryAndBlobValueContainingIgnoreCase(SampleDataCategory category, String blobValue, Sort sort);

    // catagory + Value
    List<SampleData> findByCategoryAndValueContainingIgnoreCase(SampleDataCategory category, String value, Sort sort);

    // key + Value
    List<SampleData> findByKeyContainingIgnoreCaseAndValueContainingIgnoreCase( String key, String value, Sort sort);

    // key + blobValue
    List<SampleData> findByKeyContainingIgnoreCaseAndBlobValueContainingIgnoreCase( String key, String blobValue, Sort sort);

    //category
    List<SampleData> findByCategory(SampleDataCategory category, Sort sort);

    // key
    List<SampleData> findByKeyContainingIgnoreCase( String key, Sort sort);

    // value
    List<SampleData> findByValueContainingIgnoreCase(String value, Sort sort);
    
    // blobValue
    List<SampleData> findByBlobValueContainingIgnoreCase(String blobValue, Sort sort);

    Optional<SampleData> findByCategoryIdAndKeyAndVersion(Long categoryId, String key, Long version);

    Optional<SampleData> findFirstByCategoryIdAndKeyOrderByVersionDesc(Long categoryId, String key);

    List<SampleData> findByCategoryIdAndKeyOrderByVersionDesc(Long categoryId, String key);

    List<SampleData> findByCategoryIdAndKeyAndVersionLessThanEqualOrderByVersionDesc(Long categoryId, String key, Long version);

    Long deleteByCategoryIdAndKey(Long categoryId, String key);

    Long deleteByCategoryIdAndKeyAndVersionGreaterThan(Long categoryId, String key, Long version);

    Long deleteFirstByCategoryIdAndKeyOrderByVersionDesc(Long categoryId, String key);

}