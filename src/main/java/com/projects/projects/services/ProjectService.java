package com.projects.projects.services;

import com.projects.projects.domain.memberProject.dto.MembersDTO;
import com.projects.projects.domain.memberProject.dto.PatchMembersDTO;
import com.projects.projects.domain.memberProject.dto.RemoveMembersDTO;
import com.projects.projects.domain.project.Project;
import com.projects.projects.domain.project.dto.*;
import com.projects.projects.domain.user.User;
import com.projects.projects.domain.memberProject.MemberRole;
import com.projects.projects.domain.memberProject.MemberProject;
import com.projects.projects.exception.BusinessException;
import com.projects.projects.exception.ResourceNotFoundException;
import com.projects.projects.domain.tag.Tag;
import com.projects.projects.repositories.ProjectRepository;
import com.projects.projects.repositories.TagRepository;
import com.projects.projects.repositories.MemberProjectRepository;
import com.projects.projects.repositories.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final MemberProjectRepository memberProjectRepository;

    @Autowired
    private ProjectService(ProjectRepository projectRepository, TagRepository tagRepository, UserRepository userRepository,  MemberProjectRepository memberProjectRepository) {
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.memberProjectRepository = memberProjectRepository;
    }

    public Page<@NonNull ProjectWithoutMembersDTO> query(@NonNull QueryProjectDTO request, User user) {
        PageRequest pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by("name").ascending()
        );

        return projectRepository.findByUserMembershipAndSearch(
                user.getId(),
                request.getName(),
                request.getDescription(),
                pageable).map(ProjectWithoutMembersDTO::from);
    }

    public ProjectDTO create(@NonNull CreateProjectDTO request, User owner) {
        Project newProject = new Project();

        newProject.setName(request.getName());
        newProject.setDescription(request.getDescription());

        projectRepository.save(newProject);

        List<Tag> tags = tagRepository.findAllById(request.getTagIds());

        if (tags.size() != request.getTagIds().size()) {
            throw new ResourceNotFoundException("Uma ou mais tags não existem!");
        }

        newProject.getTags().addAll(tags);

        initializeMembers(newProject, request.getMembers(), owner);

        List<MemberProject> members = memberProjectRepository.findAllByProject_Id(newProject.getId());

        return ProjectDTO.from(projectRepository.save(newProject), MembersDTO.fromList(members));
    }


    public ProjectDTO get(Integer projectId, User user){
        memberProjectRepository
                .findByProject_IdAndUser_id(projectId, user.getId()).orElseThrow(() -> new BusinessException("Usuário não é membro deste projeto!"));

        List<MemberProject> members = memberProjectRepository.findAllByProject_Id(projectId);
        return ProjectDTO
                .from(projectRepository
                        .findById(projectId)
                        .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado")), MembersDTO.fromList(members));
    }

    public void delete(Integer projectId, User user) {
        MemberProject member = memberProjectRepository
                .findByProject_IdAndUser_id(projectId, user.getId()).orElseThrow(() -> new BusinessException("Usuário não é membro deste projeto!"));

        if(member.getRole() != MemberRole.OWNER) {
            throw new BusinessException("Apenas o dono pode excluir o projeto!");
        }

        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Projeto não foi encontrado!");
        }

        projectRepository.deleteById(projectId);
    }

    public ProjectWithoutMembersDTO patch(Integer projectId, PatchProjectDTO request, User user) {
        verifyOwnerOrManager(projectId, user);

        if(!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("Projeto não foi encontrado!");
        }
        Project project = projectRepository.findById(projectId).get();

        if (request.getName().trim() != null && request.getName().trim().length() > 3) {
            project.setName(request.getName().trim());
        }

        if (request.getDescription() != null) {
            project.setDescription(request.getDescription().trim());
        }

        if (request.getTagIds() != null) {
            List<Tag> tags = tagRepository.findAllById(request.getTagIds());

            if (tags.size() != request.getTagIds().size()) {
                throw new ResourceNotFoundException("Uma ou mais tags não existem!");
            }

            project.getTags().clear();
            project.getTags().addAll(tags);
        }

        return ProjectWithoutMembersDTO.from(projectRepository.save(project));
    }

    public MemberProject verifyOwnerOrManager(Integer projectId, User user) {
        MemberProject memberProject = memberProjectRepository
                .findByProject_IdAndUser_id(projectId, user.getId()).orElseThrow(() -> new BusinessException("Usuário não é membro deste projeto!"));

        if (memberProject.getRole() != MemberRole.OWNER &&  memberProject.getRole() != MemberRole.MANAGER) {
            throw new BusinessException("Apenas donos e gerentes podem realizar essa ação!");
        }

        return memberProject;
    }

    public void patchMembers(Integer projectId, PatchMembersDTO request, User user) {
        MemberProject validatedUser = verifyOwnerOrManager(projectId, user);

        for (PatchMembersDTO.PatchMemberDTO member :  request.getMembers()) {
            Optional<MemberProject> dataMember = memberProjectRepository.findByProject_IdAndUser_id(projectId, member.getUserId());
            if (dataMember.isPresent()) {
                MemberProject memberProject = dataMember.get();

                // A manager can not change others managers or owners
                if (validatedUser.getRole() == MemberRole.MANAGER
                        && (memberProject.getRole() == MemberRole.OWNER || memberProject.getRole() == MemberRole.MANAGER)) {
                    throw new BusinessException("Gerentes não podem modificar outros gerentes ou o dono.");
                }

                // Should exist one or more owners
                if(memberProjectRepository.countByRole(MemberRole.OWNER) == 1
                        && (validatedUser.getRole() == MemberRole.OWNER
                        && (memberProject.getRole() == MemberRole.OWNER))) {
                    throw new BusinessException("Deve existir pelo menos 1 dono!");
                }

                memberProject.setRole(member.getRole());
                memberProjectRepository.save(memberProject);
            } else {
                Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado!"));
                User newMember = userRepository.findById(member.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));;

                createMember(project, newMember, member.getRole());
            }
        }
    }

    public void removeMembers(Integer projectId, RemoveMembersDTO request, User user) {
        MemberProject validatedUser = verifyOwnerOrManager(projectId, user);

        for(Integer userId : request.getUserIds()) {
            MemberProject member = memberProjectRepository.findByProject_IdAndUser_id(projectId, userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Membro não encontrado!"));

            // A manager can not delete another manager
            if(validatedUser.getRole() == MemberRole.MANAGER
                    && (member.getRole() == MemberRole.MANAGER)) {
                throw new BusinessException("Um gerente não pode excluir outro gerente!");
            }

            // Owners can not be deleted
            if(member.getRole() == MemberRole.OWNER) {
                throw new BusinessException("Donos não podem ser removidos!");
            }

            memberProjectRepository.delete(member);
        }
    }

    // utils

    public void createMember(Project project, User user, MemberRole role) {
        MemberProject newMember = new MemberProject();
        newMember.setProject(project);
        newMember.setUser(user);
        newMember.setRole(role);

        memberProjectRepository.save(newMember);
    }

    public void initializeMembers(Project project, List<CreateProjectDTO.ProjectMemberCreateDTO> projectMembersDTO, User owner) {

        MemberProject memberProject = new MemberProject();
        memberProject.setProject(project);
        memberProject.setUser(owner);
        memberProject.setRole(MemberRole.OWNER);

        memberProjectRepository.save(memberProject);

        for (CreateProjectDTO.ProjectMemberCreateDTO projectMemberDTO : projectMembersDTO) {
            User member = userRepository.findById(projectMemberDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            createMember(project, member, projectMemberDTO.getRole());
        }
    }


}
