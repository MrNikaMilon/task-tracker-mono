package com.nion.tasktracker.entity;

import com.nion.tasktracker.entity.dictionary.TaskStatus;
import com.nion.tasktracker.entity.dictionary.TaskType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="tasks")
@EntityListeners(AuditingEntityListener.class)
public record TaskEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "task_id")
        long taskId,
        String taskName,
        String taskDescription,
        @Enumerated(EnumType.STRING)
        TaskType type,
        @Enumerated(EnumType.STRING)
        TaskStatus status,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
        UserEntity userEntity,
        @CreatedDate
        LocalDateTime createdAt,
        @LastModifiedDate
        LocalDateTime lastEditedAt
) {
}
