package com.bsk.github_trending_api.controller;

import com.bsk.github_trending_api.entity.RepositoryEntity;
import com.bsk.github_trending_api.service.RepositoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/repositories")
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }
    
    @GetMapping
    public List<RepositoryEntity> getAllRepositories() {
        return repositoryService.getAllRepositories();
    }
    
    @GetMapping("/{id}")
    public RepositoryEntity getRepositoryById(
            @PathVariable Long id) {

        return repositoryService.getRepositoryById(id);
    }
    
    @PutMapping("/{id}")
    public RepositoryEntity updateRepository(
            @PathVariable Long id,
            @RequestBody RepositoryEntity repositoryEntity) {

        return repositoryService.updateRepository(id, repositoryEntity);
    }

    @PostMapping
    public RepositoryEntity addRepository(
            @RequestBody RepositoryEntity repositoryEntity) {

        return repositoryService.addRepository(repositoryEntity);
    }
    
    @DeleteMapping("/{id}")
    public String deleteRepository(@PathVariable Long id) {

        repositoryService.deleteRepository(id);

        return "Repository deleted successfully";
    }
    
    @GetMapping("/stats/total")
    public long getTotalRepositories() {
        return repositoryService.getTotalRepositories();
    }
    
    @GetMapping("/stats/stars")
    public int getTotalStars() {
        return repositoryService.getTotalStars();
    }
    
    @GetMapping("/stats/highest-score")
    public int getHighestTrendingScore() {
        return repositoryService.getHighestTrendingScore();
    }
    
    @PostMapping("/github/{owner}/{repo}")
    public RepositoryEntity importRepository(
            @PathVariable String owner,
            @PathVariable String repo) {

        return repositoryService.importRepository(owner, repo);
    }
}