package net.tokishu.note.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "Notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String name;
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", referencedColumnName = "username")
    private User author;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}