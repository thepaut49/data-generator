package com.thepaut.backend.model.data;

import com.thepaut.backend.model.data.audit.SampleDataAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "sample_data", uniqueConstraints = {
        @UniqueConstraint(name = "SampleDataUniqueKeyAndCategory", columnNames = {"sample_data_key", "sample_data_category_id"})
})
public class SampleData extends GenericEntity<SampleDataAudit>{

    @Column(name = "sample_data_key")
    private String key;

    @ManyToOne
    @JoinColumn(name = "sample_data_category_id", referencedColumnName = "id")
    private SampleDataCategory category;

    @Column(name = "sample_data_value")
    private String value;

    @Lob
    @Column(name = "sample_data_blob_value")
    private String blobValue;

    @Column(name = "is_blob_value")
    private boolean isBlobTypeValue;


    @Transient
    private List<SampleDataAudit> sampleDataVersions = new ArrayList<>();

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SampleData that = (SampleData) object;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}