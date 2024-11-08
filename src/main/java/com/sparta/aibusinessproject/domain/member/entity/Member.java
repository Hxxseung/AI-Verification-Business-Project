package com.sparta.aibusinessproject.domain.member.entity;

import com.sparta.aibusinessproject.domain.member.dto.request.SignupRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "member_id")
    private UUID id;

    @Pattern(regexp = "^[a-z0-9]{4,10}$",
            message = "아이디는 영문 소문자 및 숫자 4~10자리여야 합니다.")
    @Column(nullable = false, unique = true)
    private String loginId;

    @Size(min = 2, max = 20, message = "이름은 2자 이상 20자미만이어야 합니다.")
    @Column(nullable = false)
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,15}$",
            message = "비밀번호는 영문 대소문자, 숫자, 특수문자(!@#$%^&*)를 허용하고 8~15자리여야 합니다.")
    @Column(nullable = false)
    private String password;

    @Email
    @Column(nullable = false, unique = true)
    private String email;


    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    public static Member create(SignupRequest request) {
        return Member.builder()
                .loginId(request.loginId())
                .name(request.name())
                .password(request.password()) // 암호화 됐는지 확인 필요
                .email(request.email())
                .role(request.role())
                .build();
    }

}