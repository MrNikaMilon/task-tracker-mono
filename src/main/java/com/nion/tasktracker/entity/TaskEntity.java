package com.nion.tasktracker.entity;

import com.nion.tasktracker.entity.dictionary.TaskStatus;
import com.nion.tasktracker.entity.dictionary.TaskType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="tasks")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor @NoArgsConstructor @Data
public class TaskEntity {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "task_id", nullable = false)
        private Long taskId;
        @Column(name = "task_name", nullable = false, scale = 50)
        private String taskName;
        @Column(name = "task_description", scale = 250)
        private String taskDescription;
        @Enumerated(EnumType.STRING) @Column(scale = 50, nullable = false)
        private TaskType type;
        @Enumerated(EnumType.STRING) @Column(scale = 50, nullable = false)
        private TaskStatus status;
        @CreatedDate @Column(name = "created_at") @Temporal(TemporalType.TIMESTAMP)
        private LocalDateTime createdAt;
        @LastModifiedDate @Column(name = "updated_at") @Temporal(TemporalType.TIMESTAMP)
        private LocalDateTime updatedAt;
        @LastModifiedDate @Column(name = "last_edited_at") @Temporal(TemporalType.TIMESTAMP)
        private LocalDateTime lastEditedAt;
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", referencedColumnName = "user_id")
        private TaskUserEntity taskUserEntity;
}