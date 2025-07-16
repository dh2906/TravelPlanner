package com.example.TravelPlanner.entity;

import com.example.TravelPlanner.dto.request.MemberUpdateRequest;
import com.example.TravelPlanner.global.util.PasswordEncoder;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true, updatable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plan> plans = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChecklistItem> checklistItems = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRequest> sentFriendRequests = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendRequest> receivedFriendRequests = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> friendsTo = new ArrayList<>();

    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> friendsFrom = new ArrayList<>();

    public enum Role {
        USER, ADMIN
    }

    public void updateInfo(MemberUpdateRequest request) {
        this.password = PasswordEncoder.encode(request.password());
        this.name = request.name();
    }
}
