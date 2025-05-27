package net.tokishu.note.model;

import jakarta.persistence.*;
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
    private String username;
    private String password;

    @ColumnDefault("'USER'")
    private String role;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}