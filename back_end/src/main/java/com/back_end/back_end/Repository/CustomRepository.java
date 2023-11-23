package com.back_end.back_end.Repository;

import com.back_end.back_end.Entity.CustomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomRepository extends JpaRepository<CustomEntity, Integer> {
    Optional<CustomEntity> findByNameOrderByName(String name);
}
