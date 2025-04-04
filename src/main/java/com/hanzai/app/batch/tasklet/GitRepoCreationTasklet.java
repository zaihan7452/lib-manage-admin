package com.hanzai.app.batch.tasklet;

import com.hanzai.app.batch.GitRepoCreationBatchJobConfig;
import com.hanzai.app.github.GithubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@StepScope
@Component(GitRepoCreationBatchJobConfig.GIT_REPO_CREATION_TASKLET)
public class GitRepoCreationTasklet implements Tasklet {

    private final GithubService githubService;

    public GitRepoCreationTasklet(GithubService githubService) {
        this.githubService = githubService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        try {
            JobParameters jobParameters = contribution.getStepExecution().getJobParameters();

            String login = jobParameters.getString("login");
            String org = jobParameters.getString("org");
            String repo = jobParameters.getString("repo");
            String description = jobParameters.getString("description");

            Optional<String> optional = githubService.createRepository(login,org, repo, description, Boolean.FALSE);
            if (optional.isEmpty()) {
                throw new RuntimeException("Failed to create GitHub repository");
            }

            return RepeatStatus.FINISHED;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create GitHub repository", e);
        }
    }

}
