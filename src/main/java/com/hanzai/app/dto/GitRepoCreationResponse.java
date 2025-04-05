package com.hanzai.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Git Repository Creation Response")
public class GitRepoCreationResponse {

    @Schema(description = "Git repository creation job instance ID")
    private Long jobInstanceId;

}
