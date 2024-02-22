package com.thepaut.backend.model.data;

import com.thepaut.backend.model.data.audit.SampleDataCategoryAudit;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sample_data_category")
public class SampleDataCategory extends GenericEntity<SampleDataCategoryAudit> {

    @Column(name = "category_name", unique = true)
    private String name;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SampleData> sampleDatas = new ArrayList<>();

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

}
