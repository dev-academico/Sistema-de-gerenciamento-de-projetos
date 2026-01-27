package com.projects.projects.tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, String>, JpaSpecificationExecutor<Tag> {
    Tag deleteById(Integer id);
    Optional<Tag> findByName(String name);

    @Query("""
        select tag
        from Tag tag
        where  lower(tag.name)  like lower(concat('%', :name, '%'))
    """)
    Page<Tag> findByNamePage(
            @Param("name") String name,
            Pageable pageable
    );
}
