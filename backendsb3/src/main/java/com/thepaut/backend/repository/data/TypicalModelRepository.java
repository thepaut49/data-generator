package com.thepaut.backend.repository.data;

import com.thepaut.backend.model.data.TypicalModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypicalModelRepository extends JpaRepository<TypicalModel, Long> {
}