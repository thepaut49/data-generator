package com.thepaut.backend.model.data;

import com.thepaut.backend.dto.SampleDataCategoryDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sample_data_category" , uniqueConstraints = {
        @UniqueConstraint(name = "SampleDataCategoryUniqueCategoryNameAndVersion", columnNames = {"category_name", "version"})
})
public class SampleDataCategory {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "version")
    private Long version;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SampleData> sampleDatas = new ArrayList<>();

    @Transient
    private List<SampleDataCategory> sampleDataCategoryVersions = new ArrayList<>();

    public SampleDataCategory addData(SampleData sampleData) {
        sampleDatas.add(sampleData);
        sampleData.setCategory(this);
        return this;
    }

    public SampleDataCategory removeData(SampleData sampleData) {
        sampleDatas.remove(sampleData);
        sampleData.setCategory(null);
        return this;
    }

    public void addVersion(SampleDataCategory sampleDataCategory) {
        sampleDataCategoryVersions.add(sampleDataCategory);
    }

    public void removeVersion(SampleDataCategory sampleDataCategory) {
        sampleDataCategoryVersions.remove(sampleDataCategory);
    }

}
