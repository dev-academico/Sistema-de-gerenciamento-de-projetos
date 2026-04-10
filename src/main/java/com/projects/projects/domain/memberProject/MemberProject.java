package com.projects.projects.domain.memberProject;

import com.projects.projects.domain.project.Project;
import com.projects.projects.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Getter
@Setter
public class MemberProject {

    @EmbeddedId
    private MemberProjectId id = new MemberProjectId();

    @ManyToOne
    @MapsId("projectId")
    private Project project;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_in_project")
    private MemberRole role;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "joined_at")
    private Instant joinedAt;
}
