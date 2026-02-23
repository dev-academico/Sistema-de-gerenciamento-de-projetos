package com.projects.projects.services;

import com.projects.projects.domain.project.Project;
import com.projects.projects.domain.project.dto.*;
import com.projects.projects.domain.user.User;
import com.projects.projects.domain.userproject.ProjectUserRole;
import com.projects.projects.domain.userproject.UserProject;
import com.projects.projects.exception.ResourceNotFoundException;
import com.projects.projects.domain.tag.Tag;
import com.projects.projects.repositories.ProjectRepository;
import com.projects.projects.repositories.TagRepository;
import com.projects.projects.repositories.UserProjectRepository;
import com.projects.projects.repositories.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final UserProjectRepository userProjectRepository;

    @Autowired
    private ProjectService(ProjectRepository projectRepository, TagRepository tagRepository, UserRepository userRepository,  UserProjectRepository userProjectRepository) {
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.userProjectRepository = userProjectRepository;
    }

    public Page<@NonNull ProjectWithoutMembersDTO> query(QueryProjectDTO request) {
        PageRequest pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by("name").ascending()
        );

        Page<@NonNull Project> projects = projectRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(request.getName(), request.getDescription(), pageable);

        return projects.map(ProjectWithoutMembersDTO::from);
    }

    public ProjectDTO create(CreateProjectDTO request, User owner) {
        Project newProject = new Project();

        newProject.setName(request.getName());
        newProject.setDescription(request.getDescription());

        projectRepository.save(newProject);

        List<Tag> tags = tagRepository.findAllById(request.getTagIds());

        if (tags.size() != request.getTagIds().size()) {
            throw new ResourceNotFoundException("Uma ou mais tags não existem!");
        }

        newProject.getTags().addAll(tags);

        AddMembers(newProject, request.getMembers(), owner);

        List<UserProject> members = userProjectRepository.findAllByProject_Id(newProject.getId());

        return ProjectDTO.from(projectRepository.save(newProject), MembersDTO.fromList(members));
    }

    public void delete(Integer id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Projeto não foi encontrado!");
        }
        projectRepository.deleteById(id);
    }

    public ProjectWithoutMembersDTO patch(Integer id, PatchProjectDTO request) {
        if(!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Projeto não foi encontrado!");
        }
        Project project = projectRepository.findById(id).get();

        if (request.getName() != null) {
            project.setName(request.getName());
        }

        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }

        if (request.getTagIds() != null) {
            List<Tag> tags = tagRepository.findAllById(request.getTagIds());

            if (tags.size() != request.getTagIds().size()) {
                throw new ResourceNotFoundException("Uma ou mais tags não existem!");
            }

            project.getTags().clear();      // remove as antigas
            project.getTags().addAll(tags); // adiciona as novas
        }

        return ProjectWithoutMembersDTO.from(projectRepository.save(project));
    }

    public void AddMembers(Project project, List<ProjectMemberCreateDTO> projectMembersDTO, User owner) {

        UserProject userProject = new UserProject();
        userProject.setProject(project);
        userProject.setUser(owner);
        userProject.setRole(ProjectUserRole.OWNER);
        userProjectRepository.save(userProject);

        for (ProjectMemberCreateDTO projectMemberDTO : projectMembersDTO) {
            UserProject memberUserProject = new UserProject();
            User member = userRepository.findById(projectMemberDTO.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

            memberUserProject.setProject(project);
            memberUserProject.setUser(member);
            memberUserProject.setRole(projectMemberDTO.getRole());

            userProjectRepository.save(memberUserProject);
        }
    }

    public ProjectDTO get(Integer id){
        List<UserProject> members = userProjectRepository.findAllByProject_Id(id);
        return ProjectDTO
                .from(projectRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado")), MembersDTO.fromList(members));
    }
}
