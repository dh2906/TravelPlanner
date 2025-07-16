package com.example.TravelPlanner.entity;

import com.example.TravelPlanner.dto.request.PlanDetailRequest;
import com.example.TravelPlanner.dto.request.PlanDetailBulkUpdateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "plan_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class PlanDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_plan_id", nullable = false)
    private Plan plan;

    @Column(nullable = false)
    private int dayNumber;

    @Column(length = 100, nullable = false)
    private String placeName;

    @Column(nullable = false)
    private String address;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateInfo(PlanDetailRequest request) {
        this.dayNumber = request.dayNumber();
        this.placeName = request.placeName();
        this.address = request.address();
        this.memo = request.memo();
        this.startTime = request.startTime();
        this.endTime = request.endTime();
    }

    public void updateInfo(PlanDetailBulkUpdateRequest request) {
        this.dayNumber = request.dayNumber();
        this.placeName = request.placeName();
        this.address = request.address();
        this.memo = request.memo();
        this.startTime = request.startTime();
        this.endTime = request.endTime();
    }
}
