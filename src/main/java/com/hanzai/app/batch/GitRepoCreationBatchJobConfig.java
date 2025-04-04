package com.hanzai.app.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class GitRepoCreationBatchJobConfig {

    public static final String GIT_REPO_CREATION_JOB = "gitRepoCreationJob";
    public static final String GIT_REPO_CREATION_STEP = "gitRepoCreationStep";
    public static final String GIT_REPO_CREATION_TASKLET = "gitRepoCreationTasklet";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    public GitRepoCreationBatchJobConfig(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean(GIT_REPO_CREATION_JOB)
    public Job gitRepoCreationJob(@Qualifier(GIT_REPO_CREATION_STEP) Step gitRepoCreationStep) {
        return new JobBuilder(GIT_REPO_CREATION_JOB, jobRepository)
                .start(gitRepoCreationStep)
                .build();
    }

    @Bean(GIT_REPO_CREATION_STEP)
    public Step gitRepoCreationStep(@Qualifier(GIT_REPO_CREATION_TASKLET) Tasklet gitRepoCreationTasklet) {
        return new StepBuilder(GIT_REPO_CREATION_STEP, jobRepository)
                .tasklet(gitRepoCreationTasklet, platformTransactionManager)
                .build();
    }

}
