package com.projects.projects.repositories;

import com.projects.projects.domain.userproject.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Integer>, JpaSpecificationExecutor<UserProject> {
    List<UserProject> findAllByProject_Id(Integer projectId);
}
