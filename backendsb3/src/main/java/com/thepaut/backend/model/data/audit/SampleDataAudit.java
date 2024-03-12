package com.thepaut.backend.model.data.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "sample_data_audit")
@IdClass(EntityAuditId.class)
public class SampleDataAudit extends GenericEntityAudit {

    @Column(name = "sample_data_key")
    private String key;

    private Long categoryId;

    @Column(name = "sample_data_value")
    private String value;

    @Lob
    @Column(name = "sample_data_blob_value")
    private String blobValue;

    @Column(name = "is_blob_value")
    private boolean isBlobTypeValue;

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SampleDataAudit that = (SampleDataAudit) object;
        return getId() != null && Objects.equals(getId(), that.getId())
                && getVersion() != null && Objects.equals(getVersion(), that.getVersion());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, version);
    }
}