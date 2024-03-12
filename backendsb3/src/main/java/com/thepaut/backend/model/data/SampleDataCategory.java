package com.thepaut.backend.model.data;

import com.thepaut.backend.model.data.audit.SampleDataCategoryAudit;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SampleDataCategory that = (SampleDataCategory) object;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
