package com.nion.tasktracker.repository;

import com.nion.tasktracker.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITokenRepository extends JpaRepository<TokenEntity, Long> {
    @Query("""
           select t from TokenEntity as t
           join fetch TaskUserEntity as u on u.userId = t.taskUserEntity.userId
           where u.userId = :userId
           """)
    List<TokenEntity> findAllTokensByUser(long userId);
    Optional<TokenEntity> findByAccessToken(String accessToken);
    Optional<TokenEntity> findByRefreshToken(String accessToken);

}
