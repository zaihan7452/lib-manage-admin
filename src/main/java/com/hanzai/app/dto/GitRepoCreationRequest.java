package com.hanzai.app.dto;

import lombok.Data;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

@Data
public class GitRepoCreationRequest {

    private String login;
    private String org;
    private String repo;
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
