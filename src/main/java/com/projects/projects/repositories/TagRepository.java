package com.projects.projects.repositories;

import com.projects.projects.domain.tag.Tag;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<@NonNull Tag, @NonNull Integer>, JpaSpecificationExecutor<Tag> {
    Page<@NonNull Tag> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
