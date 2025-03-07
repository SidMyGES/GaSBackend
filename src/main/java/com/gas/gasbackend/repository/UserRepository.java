package com.gas.gasbackend.repository;

import java.util.List;

import com.gas.gasbackend.model.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gas.gasbackend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT DISTINCT u FROM User u JOIN u.skills s WHERE s.id IN :skillIds")

    List<User> findBySkills(@Param("skillIds") List<String> skillIds);
}
