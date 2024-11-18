package com.sparta.aibusinessproject.domain.store.repository;

import com.sparta.aibusinessproject.domain.store.dto.request.StoreSearchListRequest;
import com.sparta.aibusinessproject.domain.store.dto.response.StoreSearchListResponse;
import com.sparta.aibusinessproject.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID>{
    boolean existsByName(String storeName);

    Optional<Store> findByName(String storeName);


    Store findByUserId(UUID id);
}