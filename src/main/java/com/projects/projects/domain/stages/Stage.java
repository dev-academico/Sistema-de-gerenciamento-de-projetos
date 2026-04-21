package com.projects.projects.domain.stages;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity()
@Setter
@Getter
@Table(name = "stages")
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private StageStatus stageStatus;

    @Column(name = "default_order")
    int defaultOrder;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "last_updated_by")
    private Integer lastUpdatedBy;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "completed_at")
    private Date completedAt;

    @Column(name = "started_at")
    private Date startedAt;
}