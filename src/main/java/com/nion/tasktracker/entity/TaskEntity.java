package com.nion.tasktracker.entity;

import com.nion.tasktracker.entity.dictionary.TaskStatus;
import com.nion.tasktracker.entity.dictionary.TaskType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="tasks")
@EntityListeners(AuditingEntityListener.class)
public record TaskEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "task_id", nullable = false)
        Long taskId,
        @Column(name = "task_name", nullable = false, scale = 50)
        String taskName,
        @Column(name = "task_description", scale = 250)
        String taskDescription,
        @Enumerated(EnumType.STRING) @Column(scale = 50, nullable = false)
        TaskType type,
        @Enumerated(EnumType.STRING) @Column(scale = 50, nullable = false)
        TaskStatus status,
        @CreatedDate @Column(name = "created_at") @Temporal(TemporalType.TIMESTAMP)
        LocalDateTime createdAt,
        @LastModifiedDate @Column(name = "updated_at") @Temporal(TemporalType.TIMESTAMP)
        LocalDateTime updatedAt,
        @LastModifiedDate @Column(name = "last_edited_at") @Temporal(TemporalType.TIMESTAMP)
        LocalDateTime lastEditedAt,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", referencedColumnName = "user_id")
        TaskUserEntity taskUserEntity
) {
}
