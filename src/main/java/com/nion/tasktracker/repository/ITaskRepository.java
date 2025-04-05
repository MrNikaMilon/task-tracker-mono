package com.nion.tasktracker.repository;

import com.nion.tasktracker.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskRepository extends JpaRepository<TaskEntity, Long> {
}
