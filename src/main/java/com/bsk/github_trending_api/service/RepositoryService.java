package com.bsk.github_trending_api.service;

import com.bsk.github_trending_api.entity.RepositoryEntity;
import com.bsk.github_trending_api.repository.RepositoryEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.bsk.github_trending_api.dto.GitHubRepoResponse;
import com.bsk.github_trending_api.dto.GithubUserRepoResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepositoryService {

	private final RepositoryEntityRepository repository;
	private final RestClient restClient;

	public RepositoryService(RepositoryEntityRepository repository, RestClient restClient) {
		this.repository = repository;
		this.restClient = restClient;
	}

	public RepositoryEntity addRepository(RepositoryEntity repositoryEntity) {

		int trendingScore = repositoryEntity.getStars() * 5 + repositoryEntity.getForks() * 3
				- repositoryEntity.getOpenIssues();

		repositoryEntity.setTrendingScore(trendingScore);

		return repository.save(repositoryEntity);
	}

	public RepositoryEntity getRepositoryById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public List<RepositoryEntity> getAllRepositories() {
		return repository.findAll();
	}

	public RepositoryEntity updateRepository(Long id, RepositoryEntity updatedRepository) {

		RepositoryEntity existingRepository = repository.findById(id).orElse(null);

		if (existingRepository == null) {
			return null;
		}

		existingRepository.setName(updatedRepository.getName());
		existingRepository.setOwner(updatedRepository.getOwner());
		existingRepository.setStars(updatedRepository.getStars());
		existingRepository.setForks(updatedRepository.getForks());
		existingRepository.setOpenIssues(updatedRepository.getOpenIssues());

		int trendingScore = updatedRepository.getStars() * 5 + updatedRepository.getForks() * 3
				- updatedRepository.getOpenIssues();

		existingRepository.setTrendingScore(trendingScore);

		return repository.save(existingRepository);
	}
	
	public void deleteRepository(Long id) {
	    repository.deleteById(id);
	}
	
	public long getTotalRepositories() {
	    return repository.count();
	}
	
	public int getTotalStars() {

	    int totalStars = 0;

	    for (RepositoryEntity repo : repository.findAll()) {
	        totalStars += repo.getStars();
	    }

	    return totalStars;
	}
	
	public int getHighestTrendingScore() {

	    int highest = 0;

	    for (RepositoryEntity repo : repository.findAll()) {

	        if (repo.getTrendingScore() > highest) {
	            highest = repo.getTrendingScore();
	        }
	    }

	    return highest;
	}
	
	public RepositoryEntity importRepository(
	        String owner,
	        String repoName) {

	    String url =
	            "https://api.github.com/repos/"
	                    + owner
	                    + "/"
	                    + repoName;

	    GitHubRepoResponse response =
	            restClient.get()
	                    .uri(url)
	                    .retrieve()
	                    .body(GitHubRepoResponse.class);

	    RepositoryEntity repositoryEntity =
	            new RepositoryEntity();

	    repositoryEntity.setName(response.getName());
	    repositoryEntity.setOwner(owner);
	    repositoryEntity.setStars(response.getStars());
	    repositoryEntity.setForks(response.getForks());
	    repositoryEntity.setOpenIssues(response.getOpenIssues());

	    int score =
	            response.getStars() * 5
	                    + response.getForks() * 3
	                    - response.getOpenIssues();

	    repositoryEntity.setTrendingScore(score);

	    return repository.save(repositoryEntity);
	}
	
	public GitHubRepoResponse getLiveRepository(
	        String owner,
	        String repoName) {

	    String url =
	            "https://api.github.com/repos/"
	                    + owner
	                    + "/"
	                    + repoName;

	    return restClient.get()
	            .uri(url)
	            .retrieve()
	            .body(GitHubRepoResponse.class);
	}
	
	public List<RepositoryEntity> importUserRepositories(
	        String username) {

	    String url =
	            "https://api.github.com/users/"
	                    + username
	                    + "/repos";

	    GithubUserRepoResponse[] repos =
	            restClient.get()
	                    .uri(url)
	                    .retrieve()
	                    .body(GithubUserRepoResponse[].class);

	    List<RepositoryEntity> savedRepositories =
	            new ArrayList<>();

	    for (GithubUserRepoResponse repo : repos) {

	        RepositoryEntity entity =
	                new RepositoryEntity();

	        entity.setName(repo.getName());
	        entity.setOwner(username);
	        entity.setStars(repo.getStars());
	        entity.setForks(repo.getForks());
	        entity.setOpenIssues(repo.getOpenIssues());

	        int score =
	                repo.getStars() * 5
	                        + repo.getForks() * 3
	                        - repo.getOpenIssues();

	        entity.setTrendingScore(score);

	        savedRepositories.add(
	                repository.save(entity));
	    }

	    return savedRepositories;
	}
}