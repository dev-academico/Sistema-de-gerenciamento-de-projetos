package com.projects.projects.domain.stages.dto;

import com.projects.projects.domain.stages.StageTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StageDTO {
    private Integer id;
    private String name;
    private String description;
    private Boolean isDefault;
    private String timeToFinish;
    private Date createAt;

    static public StageDTO from(StageTemplate stage) {
        StageDTO stageDTO = new StageDTO(stage.getId(), stage.getName(), stage.getDescription(), stage.getIsDefault(), stage.getTimeToFinish().toString(), stage.getCreatedAt());

        return stageDTO;
    }
}
