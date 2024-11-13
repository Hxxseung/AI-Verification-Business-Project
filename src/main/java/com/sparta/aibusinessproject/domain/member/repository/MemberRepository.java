package com.sparta.aibusinessproject.domain.member.repository;

import com.sparta.aibusinessproject.domain.member.entity.Member;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByUsername(String username);

    Optional<Member> findByEmail(String email);

    default void checkDuplicatedUsername(String username) {
        findByUsername(username).ifPresent(member -> {
            throw new IllegalArgumentException(username + "는 이미 사용 중인 아이디입니다.");
        });
    }

    default void checkDuplicatedEmail(String email) {
        findByEmail(email).ifPresent(member -> {
            throw new IllegalArgumentException(email + "은 이미 사용 중인 이메일입니다.");
        });
    }

    default Member getByUsername(String username) {
        return findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException(username + "에 해당하는 사용자가 없습니다.")
        );
    }
}
