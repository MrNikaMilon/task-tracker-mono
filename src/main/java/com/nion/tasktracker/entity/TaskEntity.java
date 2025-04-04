package com.nion.tasktracker.entity;

import com.nion.tasktracker.entity.dictionary.TaskStatus;
import com.nion.tasktracker.entity.dictionary.TaskType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
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
        @Column(name = "task_name")
        String taskName,
        @Column(name = "task_description")
        String taskDescription,
        @Enumerated(EnumType.STRING)
        TaskType type,
        @Enumerated(EnumType.STRING)
        TaskStatus status,
        @CreatedDate @Column(name = "created_at") @Temporal(TemporalType.TIMESTAMP)
        LocalDateTime createdAt,
        @LastModifiedDate @Column(name = "updated_at") @Temporal(TemporalType.TIMESTAMP)
        LocalDateTime updatedAt,
        @LastModifiedDate @Column(name = "last_edited_at") @Temporal(TemporalType.TIMESTAMP)
        LocalDateTime lastEditedAt,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", referencedColumnName = "user_id")
        UserEntity userEntity
) {
}
