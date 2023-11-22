package com.back_end.back_end.Repository;

import com.back_end.back_end.Entity.CustomExtensionEntity;
import com.back_end.back_end.Entity.FixedExtensionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomExtensionRepository extends JpaRepository<CustomExtensionEntity, Integer> {
    Optional<CustomExtensionEntity> findByName(String name);
}
