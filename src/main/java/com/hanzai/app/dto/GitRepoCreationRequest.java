package com.hanzai.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

@Data
@Schema(description = "GitHub repository creation request")
public class GitRepoCreationRequest {

    @Schema(description = "GitHub username")
    private String login;

    @Schema(description = "GitHub organization name")
    private String org;

    @Schema(description = "GitHub repository name")
    private String repo;

    @Schema(description = "GitHub repository description")
    private String description;

    public JobParameters toJobParameters() {
        return new JobParametersBuilder()
                .addString("login", getLogin())
                .addString("org", getOrg())
                .addString("repo", getRepo())
                .addString("description", getDescription())
                .toJobParameters();
    }

}
