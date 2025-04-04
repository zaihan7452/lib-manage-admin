package com.hanzai.app.github;

import com.hanzai.app.dto.GithubOauthRequest;
import com.hanzai.app.dto.GithubOauthResponse;
import com.hanzai.app.entity.GithubOauthEntity;
import com.hanzai.app.service.IGithubOauthService;
import com.hanzai.app.util.HttpClientUtil;
import com.hanzai.app.util.ObjectMapperUtil;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class GithubService {

    public static final String GITHUB_URL = "https://github.com";
    public static final String OAUTH_AUTHORIZE_URL_FORMAT = GITHUB_URL + "/login/oauth/authorize?client_id=%s&scope=repo";
    public static final String OAUTH_ACCESS_TOKEN_URL = GITHUB_URL + "/login/oauth/access_token";
    public static final String OAUTH_CALLBACK_URL = "http://b255e54.r3.cpolar.cn/api/v1/github/oauth/callback";

    @Value("${github.client-id}")
    private String clientId;

    @Value("${github.client-secret}")
    private String clientSecret;

    private final IGithubOauthService githubOauthService;

    public GithubService(IGithubOauthService githubOauthService) {
        this.githubOauthService = githubOauthService;
    }

    /**
     * Get the GitHub OAuth authorization URL.
     * @return A String containing the OAuth authorization URL.
     */
    public String getOauthAuthorizationUrl() {
        return String.format(OAUTH_AUTHORIZE_URL_FORMAT, clientId);
    }

    /**
     * Exchange the authorization code for a GitHub access token.
     *
     * @param code The authorization code received from GitHub.
     * @return An Optional containing GithubOauthResponse if successful, otherwise empty.
     */
    public Optional<GithubOauthResponse> getOauthToken(String code) {
        GithubOauthRequest githubOauthRequest = createGithubOauthRequest(code);
        String jsonBody = ObjectMapperUtil.objectToJson(githubOauthRequest);

        String response = HttpClientUtil.sendPostRequest(OAUTH_ACCESS_TOKEN_URL, jsonBody);
        GithubOauthResponse githubOauthResponse = ObjectMapperUtil.jsonToObject(response, GithubOauthResponse.class);
        return Optional.ofNullable(githubOauthResponse);
    }

    /**
     * Retrieve the authenticated GitHub user details using an access token.
     *
     * @param accessToken The GitHub access token.
     * @return An Optional containing GHUser if successful, otherwise empty.
     * @throws IOException If GitHub API call fails.
     */
    public Optional<GHUser> getOauthUser(String accessToken) throws IOException {
        GitHub gitHub = GitHub.connectUsingOAuth(accessToken);
        return Optional.ofNullable(gitHub.getMyself());
    }

    /**
     * Create a new GitHub repository.
     *
     * @param login  The login of the repository.
     * @param org  The org of the repository.
     * @param repo  The repo of the repository.
     * @param description A short description of the repository.
     * @param isPrivate   Whether the repository should be private.
     * @return An Optional containing the repository URL if successful, otherwise empty.
     * @throws IOException If GitHub API call fails.
     */
    public Optional<String> createRepository(String login, String org, String repo, String description, boolean isPrivate) throws IOException {
        Optional<GithubOauthEntity> optional = githubOauthService.getGithubOauthByLogin(login);
        if (optional.isEmpty()) {
            return Optional.empty();
        }

        GithubOauthEntity githubOauthEntity = optional.get();
        String accessToken = githubOauthEntity.getAccessToken();
        GitHub gitHub = GitHub.connectUsingOAuth(accessToken);
        GHRepository repository = gitHub.createRepository(repo)
                .description(description)
                .private_(isPrivate)
                .create();
        return Optional.of(repository.getHtmlUrl().toString());
    }

    /**
     * Create a GitHub OAuth request object with necessary parameters.
     *
     * @param code The authorization code received from GitHub.
     * @return A configured GithubOauthRequest object.
     */
    private GithubOauthRequest createGithubOauthRequest(String code) {
        GithubOauthRequest githubOauthRequest = new GithubOauthRequest();
        githubOauthRequest.setClient_id(clientId);
        githubOauthRequest.setClient_secret(clientSecret);
        githubOauthRequest.setCode(code);
        githubOauthRequest.setRedirect_uri(OAUTH_CALLBACK_URL);
        return githubOauthRequest;
    }

}
