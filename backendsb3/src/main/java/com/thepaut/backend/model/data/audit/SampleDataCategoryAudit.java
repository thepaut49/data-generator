package com.thepaut.backend.model.data.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "sample_data_category_audit")
@IdClass(EntityAuditId.class)
public class SampleDataCategoryAudit extends GenericEntityAudit {

    @Column(name = "category_name")
    private String name;

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SampleDataCategoryAudit that = (SampleDataCategoryAudit) object;
        return getId() != null && Objects.equals(getId(), that.getId())
                && getVersion() != null && Objects.equals(getVersion(), that.getVersion());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, version);
    }
}
