package com.projects.projects.repositories;

import com.projects.projects.domain.stages.Stage;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends JpaRepository<@NonNull Stage,@NonNull Integer>, JpaSpecificationExecutor<@NonNull Stage> {
    public Page<@NonNull Stage> findAllByProjectIdAndName(@NonNull Integer projectId, Pageable pageable);
}
