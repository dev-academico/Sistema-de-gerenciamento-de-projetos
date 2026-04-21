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
public class StageTemplateDTO {
    private Integer id;
    private String name;
    private String description;
    private Boolean isDefault;
    private String timeToFinish;
    private Date createAt;

    static public StageTemplateDTO from(StageTemplate stage) {
        return new StageTemplateDTO(stage.getId(), stage.getName(), stage.getDescription(), stage.getIsDefault(), stage.getTimeToFinish().toString(), stage.getCreatedAt());
    }
}
