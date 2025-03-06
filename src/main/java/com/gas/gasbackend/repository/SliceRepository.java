package com.gas.gasbackend.repository;

import com.gas.gasbackend.model.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SliceRepository extends JpaRepository<Slice, String> {
}
