package com.nion.tasktracker.repository;

import com.nion.tasktracker.entity.TaskEntity;
import com.nion.tasktracker.entity.TaskUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query("""
    select t from TaskEntity as t
    inner join fetch TaskUserEntity as u on u.userId =  t.taskUserEntity.userId
    where 1=1 and u.userId = :user
    """)
    List<TaskEntity> getTaskByUser(long user);
}
