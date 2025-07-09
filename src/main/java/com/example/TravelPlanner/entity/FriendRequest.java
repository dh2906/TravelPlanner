package com.example.TravelPlanner.entity;

import com.example.TravelPlanner.global.exception.CustomException;
import com.example.TravelPlanner.global.exception.ExceptionCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "friend_request",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"sender_id", "receiver_id"}
        )
)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    public void acceptOrThrow() {
        if (this.status != Status.PENDING) {
            throw new CustomException(ExceptionCode.FRIEND_REQUEST_ALREADY_PROCESSED);
        }

        this.status = Status.ACCEPTED;
    }

    public void rejectOrThrow() {
        if (this.status != Status.PENDING) {
            throw new CustomException(ExceptionCode.FRIEND_REQUEST_ALREADY_PROCESSED);
        }

        this.status = Status.REJECTED;
    }
}
