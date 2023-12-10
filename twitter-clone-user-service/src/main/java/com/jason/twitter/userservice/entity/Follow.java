package com.jason.twitter.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "follows",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"followers_id", "following_id"})},
        indexes = {
                @Index(name = "idx_followers", columnList = "followers_id"),
                @Index(name = "idx_following", columnList = "following_id")
        })
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "followers_id", nullable = false)
    private Long followersId;

    @Column(name = "following_id", nullable = false)
    private Long followingId;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}