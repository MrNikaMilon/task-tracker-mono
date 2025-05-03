package com.nion.tasktracker.repository;

import com.nion.tasktracker.entity.TaskUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITaskUserRepository extends JpaRepository<TaskUserEntity, Long> {
    Optional<TaskUserEntity> findByUserId(Long userId);
    Optional<TaskUserEntity> findByEmail(String email);
    Optional<TaskUserEntity> findByUsername(String username);
}
