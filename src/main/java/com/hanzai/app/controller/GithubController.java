package com.hanzai.app.controller;

import com.hanzai.app.batch.GitRepoCreationBatchJobConfig;
import com.hanzai.app.dto.GitRepoCreationRequest;
import com.hanzai.app.dto.GitRepoCreationResponse;
import com.hanzai.app.dto.GithubOauthResponse;
import com.hanzai.app.entity.GithubOauthEntity;
import com.hanzai.app.github.GithubService;
import com.hanzai.app.model.Result;
import com.hanzai.app.service.IGithubOauthService;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHUser;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/github")
public class GithubController {

    private final GithubService githubService;
    private final JobLauncher jobLauncher;
    private final Job gitRepoCreationJob;
    private final IGithubOauthService githubOauthService;

    public GithubController(GithubService githubService,
                            JobLauncher jobLauncher,
                            @Qualifier(GitRepoCreationBatchJobConfig.GIT_REPO_CREATION_JOB) Job gitRepoCreationJob,
                            IGithubOauthService githubOauthService) {
        this.githubService = githubService;
        this.jobLauncher = jobLauncher;
        this.gitRepoCreationJob = gitRepoCreationJob;
        this.githubOauthService = githubOauthService;
    }


    /**
     * Get gitHub oauth authorization url
     * @return The oauth authorization url
     */
    @GetMapping("/oauth/authorize")
    public Result<String> oauthAuthorize() {
        return Result.success(githubService.getOauthAuthorizationUrl());
    }

    /**
     * Handle gitHub oauth callback
     * @param code The code received from GitHub after user authorization
     * @return The result of the operation
     */
    @GetMapping("/oauth/callback")
    public Result<GithubOauthResponse> oauthCallback(@RequestParam("code") String code) {
        try {
            Optional<GithubOauthResponse> optionalGithubOauthResponse = githubService.getOauthToken(code);
            if (optionalGithubOauthResponse.isEmpty()) {
                throw new RuntimeException("Failed to get access token");
            }

            GithubOauthResponse githubOauthResponse = optionalGithubOauthResponse.get();

            Optional<GHUser> optionalGHUser = githubService.getOauthUser(githubOauthResponse.getAccess_token());
            if (optionalGHUser.isEmpty()) {
                throw new RuntimeException("Failed to get user information");
            }
            GHUser ghUser = optionalGHUser.get();
            GithubOauthEntity githubOauthEntity = new GithubOauthEntity();
            githubOauthEntity.setGithubId(ghUser.getId())
                    .setLogin(ghUser.getLogin())
                    .setLoginType(ghUser.getType())
                    .setTokenType(githubOauthResponse.getToken_type())
                    .setAccessToken(githubOauthResponse.getAccess_token())
                    .setScope(githubOauthResponse.getScope());
            githubOauthService.save(githubOauthEntity);

            return Result.success(githubOauthResponse);
        } catch (Exception e) {
            log.error("Failed to handle github oauth callback", e);
            throw new RuntimeException("Failed to handle github oauth callback", e);
        }
    }

    /**
     * Create gitHub repository
     * @param gitRepoCreationRequest the request of the creation
     * @return the result of the operation
     */
    @PostMapping("/repo/create")
    public Result<GitRepoCreationResponse> createRepo(@RequestBody GitRepoCreationRequest gitRepoCreationRequest) {
        GitRepoCreationResponse gitRepoCreationResponse = new GitRepoCreationResponse();

        try {
            JobParameters jobParameters = gitRepoCreationRequest.toJobParameters();
            JobExecution jobExecution = jobLauncher.run(gitRepoCreationJob, jobParameters);

            gitRepoCreationResponse.setJobInstanceId(jobExecution.getJobInstance().getInstanceId());
        } catch (JobExecutionException e) {
            log.error("Failed to create gitHub repository", e);
            throw new RuntimeException("Failed to create gitHub repository", e);
        }

        return Result.success(gitRepoCreationResponse);
    }

}
