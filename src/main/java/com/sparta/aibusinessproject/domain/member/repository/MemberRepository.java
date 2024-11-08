package com.sparta.aibusinessproject.domain.member.repository;

import com.sparta.aibusinessproject.domain.member.entity.Member;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByEmail(String email);

    default void checkDuplicatedId(String loginId) {
        findByLoginId(loginId).ifPresent(member -> {
            throw new IllegalArgumentException(loginId + "는 이미 사용 중인 아이디입니다.");
        });
    }

    default void checkDuplicatedEmail(String email) {
        findByEmail(email).ifPresent(member -> {
            throw new IllegalArgumentException(email + "은 이미 사용 중인 이메일입니다.");
        });
    }

}
