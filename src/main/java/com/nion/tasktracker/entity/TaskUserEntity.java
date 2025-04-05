package com.nion.tasktracker.entity;

import com.nion.tasktracker.entity.dictionary.UserRole;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
public record TaskUserEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "user_id")
        Long userId,
        @Column(name = "user_name", nullable = false)
        String userName,
        @Column(nullable = false)
        String email,
        @Enumerated(EnumType.STRING) @Column(nullable = false)
        UserRole role,
        @CreatedDate @Column(name = "created_at") @Temporal(TemporalType.TIMESTAMP)
        LocalDateTime createdAt,
        @LastModifiedBy @Column(name = "updated_at") @Temporal(TemporalType.TIMESTAMP)
        LocalDateTime updatedAt,
        @Column(name = "last_activity") @Temporal(TemporalType.TIMESTAMP)
        LocalDateTime lastActivity
) {
}
