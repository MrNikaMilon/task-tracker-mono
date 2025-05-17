package com.nion.tasktracker.entity;

import com.nion.tasktracker.entity.dictionary.TaskUserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_name_email", columnNames = {"user_name", "email"})
})
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class TaskUserEntity implements UserDetails {
        @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_user_id")
        @Column(name = "user_id") @SequenceGenerator(name = "task_user_id", sequenceName = "task_user_id_seq", allocationSize = 1)
        private Long userId;

        @Column(name = "user_name", nullable = false)
        private String username;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String password;

        @Enumerated(EnumType.STRING) @Column(nullable = false)
        private TaskUserRole role;

        @CreatedDate @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;

        @LastModifiedDate @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @Column(name = "last_activity")
        private LocalDateTime lastActivity;

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "taskUserEntity")
        private Set<TaskEntity> tasks;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(role);
        }

        @Override
        public String getPassword() {
                return password;
        }

        @Override
        public String getUsername() {
                return username;
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }
}
