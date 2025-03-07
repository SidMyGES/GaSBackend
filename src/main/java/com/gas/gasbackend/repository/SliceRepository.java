package com.gas.gasbackend.repository;

import com.gas.gasbackend.model.Slice;
import com.gas.gasbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * Repository interface for Slice entities.
 */
@Repository
public interface SliceRepository extends JpaRepository<Slice, String> {

    /**
     * Finds a slice by its name.
     *
     * @param name Name of the slice
     * @return Optional containing the slice if found
     */
    Optional<Slice> findByName(String name);

    /**
     * Finds all slices containing a specific skill.
     *
     * @param skillId Skill ID
     * @return List of slices
     */
    List<Slice> findBySkills_Id(String skillId);


    List<Slice> findBytargetUser_Id(String targetUserId);

    List<Slice> findBySourceUser_Id(String sourceUserId);
}
