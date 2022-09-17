package com.thepaut.backend.repository.data;

import com.thepaut.backend.model.data.SampleData;
import com.thepaut.backend.model.data.SampleDataCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SampleDataRepository extends JpaRepository<SampleData, Long> {

    // catagory + key + value
    List<SampleData> findByCategoryAndKeyContainingIgnoreCaseAndValueContainingIgnoreCase(SampleDataCategory category, String key, String value);

    // catagory + key + blobvalue
    List<SampleData> findByCategoryAndKeyContainingIgnoreCaseAndBlobValueContainingIgnoreCase(SampleDataCategory category, String key, String blobValue);

    // catagory + key
    List<SampleData> findByCategoryAndKeyContainingIgnoreCase(SampleDataCategory category, String key);

    // catagory + blobValue
    List<SampleData> findByCategoryAndBlobValueContainingIgnoreCase(SampleDataCategory category, String blobValue);

    // catagory + Value
    List<SampleData> findByCategoryAndValueContainingIgnoreCase(SampleDataCategory category, String value);

    // key + Value
    List<SampleData> findByKeyContainingIgnoreCaseAndValueContainingIgnoreCase( String key, String value);

    // key + blobValue
    List<SampleData> findByKeyContainingIgnoreCaseAndBlobValueContainingIgnoreCase( String key, String blobValue);

    //category
    List<SampleData> findByCategory(SampleDataCategory category);

    // key
    List<SampleData> findByKeyContainingIgnoreCase( String key);

    // value
    List<SampleData> findByValueContainingIgnoreCase(String value);
    
    // blobValue
    List<SampleData> findByBlobValueContainingIgnoreCase(String blobValue);

    Optional<SampleData> findByCategoryNameAndKeyAndVersion(String categoryName, String key, Long version);

    Optional<SampleData> findFirstByCategoryNameAndKeyOrderByVersionDesc(String categoryName, String key);

    List<SampleData> findByCategoryNameAndKeyOrderByVersionDesc(String categoryName, String key);

    List<SampleData> findByCategoryNameAndKeyAndVersionLessThanEqualOrderByVersionDesc(String categoryName, String key, Long version);


    //mm



    Long deleteByCategoryNameAndKey(String categoryName, String key);

    Long deleteByCategoryNameAndKeyAndVersionGreaterThan(String categoryName,String key, Long version);

    Long deleteFirstByCategoryNameAndKeyOrderByVersionDesc(String categoryName, String key);



}