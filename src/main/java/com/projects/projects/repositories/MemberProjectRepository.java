package com.projects.projects.repositories;

import com.projects.projects.domain.memberProject.MemberProject;
import com.projects.projects.domain.memberProject.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberProjectRepository extends JpaRepository<MemberProject, Integer>, JpaSpecificationExecutor<MemberProject> {
    List<MemberProject> findAllByProject_Id(Integer projectId);
    List<MemberProject> findAllByUser_Id(Integer userId);
    Optional<MemberProject> findByProject_IdAndUser_id(Integer projectId, Integer userId);
    Integer countByRole(MemberRole role);
}
