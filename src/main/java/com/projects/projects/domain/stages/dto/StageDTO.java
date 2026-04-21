package com.projects.projects.domain.stages.dto;

import com.projects.projects.domain.stages.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StageDTO {
    private Integer id;
    private String name;
    private String description;
    private Date dueDate;
    private Integer defaultOrder;
    private Date createdAt;
    private Date updatedAt;
    private Date startDate;
    private Date completedAt;

    public static StageDTO from(Stage stage){
        StageDTO stageDTO = new StageDTO();
        stageDTO.setId(stage.getId());
        stageDTO.setName(stage.getName());
        stageDTO.setDescription(stage.getDescription());
        stageDTO.setDueDate(stage.getDueDate());
        stageDTO.setDefaultOrder(stage.getDefaultOrder());
        stageDTO.setCreatedAt(stage.getCreatedAt());
        stageDTO.setUpdatedAt(stage.getUpdatedAt());
        stageDTO.setCompletedAt(stage.getCompletedAt());

        return stageDTO;
    }
}
