package com.gas.gasbackend.repository;

import com.gas.gasbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM users u JOIN u.skills s WHERE s.ID IN :skillIds")
    public List<User> findBySkills(final List<String> skillIds);
}
