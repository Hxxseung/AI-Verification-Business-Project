package com.sparta.aibusinessproject.domain.member.entity;

import com.sparta.aibusinessproject.domain.member.dto.request.SignupRequest;
import com.sparta.aibusinessproject.domain.member.dto.request.ModifyMemberInfoRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "member_id")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Size(min = 2, max = 20, message = "이름은 2자 이상 20자미만이어야 합니다.")
    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String address;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    public static Member create(SignupRequest request, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .username(request.username())
                .nickname(request.nickname())
                .password(passwordEncoder.encode(request.password()))
                .phone(request.phone())
                .email(request.email())
                .address(request.address())
                .role(request.role())
                .build();
    }

    public void update(
            ModifyMemberInfoRequest request,
            PasswordEncoder passwordEncoder) {
        Optional.ofNullable(request.nickname()).ifPresent(value -> this.nickname = value);
        Optional.ofNullable(request.password()).ifPresent(value -> this.password = passwordEncoder.encode(value));
        Optional.ofNullable(request.phone()).ifPresent(value -> this.phone = value);
        Optional.ofNullable(request.email()).ifPresent(value -> this.email = value);
        Optional.ofNullable(request.address()).ifPresent(value -> this.address = value);
    }

    public void delete() {
        // 회원 탈퇴 -> 개인정보 삭제
        this.nickname = "deleted";
        this.address = "-";
        this.phone = "-";
        this.email = "abc@sparta.com";
        this.deletedAt = LocalDateTime.now();
    }

}