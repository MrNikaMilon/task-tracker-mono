package com.nion.tasktracker.repository;

import com.nion.tasktracker.entity.TaskUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskUserRepository extends JpaRepository<TaskUserEntity, Long> {
}
