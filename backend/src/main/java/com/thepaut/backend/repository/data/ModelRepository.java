package com.thepaut.backend.repository.data;

import com.thepaut.backend.model.data.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
}