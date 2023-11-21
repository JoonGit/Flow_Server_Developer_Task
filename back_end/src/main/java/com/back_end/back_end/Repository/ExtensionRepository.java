package com.back_end.back_end.Repository;

import com.back_end.back_end.Entity.ExtensionEntity;
import com.example.back_end.Entity.IssueTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExtensionRepository extends JpaRepository<ExtensionEntity, Integer> {}
