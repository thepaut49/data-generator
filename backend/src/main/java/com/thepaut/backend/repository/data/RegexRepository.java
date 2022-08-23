package com.thepaut.backend.repository.data;

import com.thepaut.backend.model.data.Regex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegexRepository extends JpaRepository<Regex, Long> {
}