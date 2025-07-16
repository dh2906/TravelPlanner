package com.example.TravelPlanner.entity;

import com.example.TravelPlanner.dto.request.PlanRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plan")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanDetail> details = new ArrayList<>();

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "share_path")
    private String sharePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>();

    public enum Visibility {
        PUBLIC,
        PRIVATE
    }

    public void updateInfo(PlanRequest request) {
        this.title = request.title();
        this.description = request.description();
        this.startDate = request.startDate();
        this.endDate = request.endDate();
        this.visibility = request.visibility();

        if (this.visibility.equals(Visibility.PUBLIC)) {
            this.sharePath = null;
        }
    }

    public void assignShareUrl(String sharePath) {
        this.sharePath = sharePath;
    }
}
