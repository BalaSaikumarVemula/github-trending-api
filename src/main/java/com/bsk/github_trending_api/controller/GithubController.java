package com.bsk.github_trending_api.controller;

import com.bsk.github_trending_api.dto.GitHubRepoResponse;
import com.bsk.github_trending_api.entity.RepositoryEntity;
import com.bsk.github_trending_api.service.RepositoryService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    private final RepositoryService repositoryService;

    public GithubController(
            RepositoryService repositoryService) {

        this.repositoryService = repositoryService;
    }

    @GetMapping("/{owner}/{repo}")
    public GitHubRepoResponse getLiveRepository(
            @PathVariable String owner,
            @PathVariable String repo) {

        return repositoryService
                .getLiveRepository(owner, repo);
    }
    
    @PostMapping("/user/{username}/import")
    public List<RepositoryEntity> importUserRepositories(
            @PathVariable String username) {

        return repositoryService
                .importUserRepositories(username);
    }
}