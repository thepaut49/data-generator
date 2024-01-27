package com.thepaut.backend.repository.data;

import com.thepaut.backend.model.data.SampleData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class SampleDataRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public List<SampleData> findByCriteria(Long categoryId, String key, Boolean isBlobTypeValue, String value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SampleData> criteriaQuery = criteriaBuilder.createQuery(SampleData.class);
        Root<SampleData> root = criteriaQuery.from(SampleData.class);

        Predicate predicate = criteriaBuilder.conjunction();

        if (categoryId != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("categoryId"), categoryId));
        }

        if (key != null && !key.isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("key"), key));
        }

        if (isBlobTypeValue != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("isBlobTypeValue"), isBlobTypeValue));
        }

        if (value != null && !value.isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("value"), value));
        }

        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("key")),
                criteriaBuilder.desc(root.get("version")));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}