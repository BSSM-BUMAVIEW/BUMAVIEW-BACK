package com.bssm.bumaview.domain.like.domain.repository;

import com.bssm.bumaview.domain.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndAnswerId(Long userId, Long answerId);

    Optional<Like> findByUserIdAndAnswerId(Long userId, Long answerId);
}
