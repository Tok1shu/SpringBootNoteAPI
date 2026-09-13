package net.tokishu.note.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "Users")
public class User {
    @Id
    @Column(length = 24)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private UserRole role;

    @Email
    @Column(nullable = true)
    private String gravatarEmail;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}