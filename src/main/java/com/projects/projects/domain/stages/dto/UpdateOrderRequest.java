package com.projects.projects.domain.stages.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateOrderRequest {
    private Integer stageId;
    private int default_order;
}
