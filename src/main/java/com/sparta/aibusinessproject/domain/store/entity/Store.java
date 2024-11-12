package com.sparta.aibusinessproject.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_stores")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID storeId;

    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String address;

    @Column
    private String description;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String status;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private LocalDateTime deletedAt;
    private String deletedBy;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.createdBy = UUID.randomUUID().toString();
        this.modifiedAt = LocalDateTime.now();
        this.modifiedBy = UUID.randomUUID().toString();
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
        this.modifiedBy = UUID.randomUUID().toString();
    }

    public void updateStoreDetails(String name, String description, String address) {
        this.name = name;
        this.description = description;
        this.address = address;
        onUpdate();
    }


}
