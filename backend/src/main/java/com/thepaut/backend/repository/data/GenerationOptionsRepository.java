package com.thepaut.backend.repository.data;

import com.thepaut.backend.model.data.GenerationOptions;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenerationOptionsRepository extends JpaRepository<GenerationOptions, Long> {
}