package com.back_end.back_end.Repository;

import com.back_end.back_end.Entity.FixedExtensionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FixedExtensionRepository extends JpaRepository<FixedExtensionEntity, Integer> {
    Optional<FixedExtensionEntity> findByName(String name);
}
