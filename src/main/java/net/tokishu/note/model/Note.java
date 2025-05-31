package net.tokishu.note.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
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
    @Column(length = 128, nullable = false)
    private String name;
    @Column(length = 8192, nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", referencedColumnName = "username")
    private User author;

    @Column(length = 6, nullable = true, updatable = false)
    private String publicLink;

    @ColumnDefault("false")
    private Boolean isPublic;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}