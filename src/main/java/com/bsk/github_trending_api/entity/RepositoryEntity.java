package com.bsk.github_trending_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "repositories")
public class RepositoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String owner;

    private Integer stars;

    private Integer forks;

    private Integer openIssues;

    private Integer trendingScore;

    public RepositoryEntity() {
    }

    public RepositoryEntity(Long id,
                            String name,
                            String owner,
                            Integer stars,
                            Integer forks,
                            Integer openIssues,
                            Integer trendingScore) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.stars = stars;
        this.forks = forks;
        this.openIssues = openIssues;
        this.trendingScore = trendingScore;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public Integer getForks() {
		return forks;
	}

	public void setForks(Integer forks) {
		this.forks = forks;
	}

	public Integer getOpenIssues() {
		return openIssues;
	}

	public void setOpenIssues(Integer openIssues) {
		this.openIssues = openIssues;
	}

	public Integer getTrendingScore() {
		return trendingScore;
	}

	public void setTrendingScore(Integer trendingScore) {
		this.trendingScore = trendingScore;
	}

}