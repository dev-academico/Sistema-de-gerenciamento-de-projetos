package com.projects.projects.repositories;

import com.projects.projects.domain.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {
    @Query("""
        select p from Project p
            join MemberProject mp on mp.project = p
            where mp.user.id = :userId
            and(
                lower(p.name) like lower(concat('%', :name, '%'))
                or
                lower(p.description) like lower(concat('%', :description, '%')) 
            )
    """)
    Page<Project> findByUserMembershipAndSearch(
            @Param("userId") Integer userId,
            @Param("name") String name,
            @Param("description") String description,
            Pageable pageable);
}
