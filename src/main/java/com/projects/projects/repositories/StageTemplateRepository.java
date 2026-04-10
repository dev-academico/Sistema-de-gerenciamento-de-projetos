package com.projects.projects.repositories;

import com.projects.projects.domain.stages.StageTemplate;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StageTemplateRepository extends JpaRepository<StageTemplate,Integer>, JpaSpecificationExecutor<StageTemplate> {
    Page<StageTemplate> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
