package com.example.TravelPlanner.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "share_url", length = 255)
    private String shareUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum Visibility {
        PUBLIC,
        PRIVATE,
        FRIENDS_ONLY
    }
}
