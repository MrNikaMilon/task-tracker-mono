package com.nion.tasktracker.entity;

import com.nion.tasktracker.entity.dictionary.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_name_email", columnNames = {"user_name", "email"})
})
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor @NoArgsConstructor @Data
public class TaskUserEntity {
        @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_user_id")
        @Column(name = "user_id") @SequenceGenerator(name = "task_user_id", sequenceName = "task_user_id_seq", allocationSize = 1)
        private Long userId;
        @Column(name = "user_name", nullable = false)
        private String userName;
        @Column(nullable = false)
        private String email;
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private UserRole role;
        @CreatedDate @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;
        @LastModifiedDate @Column(name = "updated_at")
        private LocalDateTime updatedAt;
        @Column(name = "last_activity")
        private LocalDateTime lastActivity;
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskUserEntity")
        private Set<TaskEntity> tasks;
}
