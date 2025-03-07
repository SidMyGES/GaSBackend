package com.gas.gasbackend.service;

import com.gas.gasbackend.dto.SliceDTO;
import com.gas.gasbackend.dto.user.UserCreateDTO;
import com.gas.gasbackend.dto.user.UserDTO;
import com.gas.gasbackend.model.*;
import com.gas.gasbackend.repository.SliceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer for managing slices.
 */
@Service
public class SliceService {

    private final SliceRepository sliceRepository;

    public SliceService(final SliceRepository sliceRepository) {
        this.sliceRepository = sliceRepository;
    }

    /**
     * Retrieves all slices.
     *
     * @return List of slices
     */
    public List<Slice> getAllSlices() {
        return sliceRepository.findAll();
    }

    /**
     * Retrieves a slice by ID.
     *
     * @param id Slice ID
     * @return Optional Slice
     */
    public Optional<Slice> getSliceById(final String id) {
        return sliceRepository.findById(id);
    }

    /**
     * Creates and saves a new slice.
     *
     * @param slice Slice to save
     * @return Saved slice
     */
    public Slice createSlice(final Slice slice) {
        return sliceRepository.save(slice);
    }

    /**
     * Adds a skill to a slice.
     *
     * @param sliceId Slice ID
     * @param skill   Skill to add
     * @return Optional updated Slice
     */
    public Optional<Slice> addSkillToSlice(final String sliceId, final Skill skill) {
        return sliceRepository.findById(sliceId).map(slice -> {
            slice.getSkills().add(skill);
            return sliceRepository.save(slice);
        });
    }

    /**
     * Adds a comment to a slice.
     *
     * @param sliceId Slice ID
     * @param comment Comment to add
     * @return Optional updated Slice
     */
    public Optional<Slice> addCommentToSlice(final String sliceId, final Comment comment) {
        return sliceRepository.findById(sliceId).map(slice -> {
            slice.getComments().add(comment);
            return sliceRepository.save(slice);
        });
    }

    /**
     * Deletes a slice by ID.
     *
     * @param id Slice ID
     */
    public void deleteSlice(final String id) {
        sliceRepository.deleteById(id);
    }

    /**
     * Retrieves a slice by its unique name.
     *
     * @param name Name of the slice
     * @return Optional slice if found
     */
    public Optional<Slice> getSliceByName(final String name) {
        return sliceRepository.findByName(name);
    }

    /**
     * Retrieves all slices associated with a specific skill.
     *
     * @param skillId ID of the skill
     * @return List of slices having the specified skill
     */
    public List<Slice> getSlicesBySkillId(final String skillId) {
        return sliceRepository.findBySkills_Id(skillId);
    }

    public List<SliceDTO> getSlicesByTargetUserId(final String targetUserId) {
        return sliceRepository.findBytargetUser_Id(targetUserId).stream().map(Slice::mapSliceToDto).toList();
    }

    public List<SliceDTO> getSlicesBySourceUserId(final String sourceUserId) {
        return sliceRepository.findBytargetUser_Id(sourceUserId).stream().map(Slice::mapSliceToDto).toList();
    }





}
