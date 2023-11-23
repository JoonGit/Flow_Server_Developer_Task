package com.back_end.back_end.Repository;

import com.back_end.back_end.Entity.FixedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FixedRepository extends JpaRepository<FixedEntity, Integer> {
    Optional<FixedEntity> findByName(String name);
}
