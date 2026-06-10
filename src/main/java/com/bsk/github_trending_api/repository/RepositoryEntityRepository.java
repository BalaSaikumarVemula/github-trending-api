package com.bsk.github_trending_api.repository;

import com.bsk.github_trending_api.entity.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryEntityRepository
        extends JpaRepository<RepositoryEntity, Long> {
}