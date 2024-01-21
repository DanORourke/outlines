package com.example.outlines.repository;

import com.example.outlines.model.Outline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutlineRepository extends JpaRepository<Outline, UUID> {
    List<Outline> findByParentIdOrderByBirthOrder(UUID parentUUID);
    Outline findByTitle(String title);
    List<Outline> findByTopParentId(UUID uuid);
    List<Outline> findByIsTopParent(Boolean isTopParent);
}
