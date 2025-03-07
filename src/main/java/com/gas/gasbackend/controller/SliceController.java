package com.gas.gasbackend.controller;

import com.gas.gasbackend.model.Comment;
import com.gas.gasbackend.model.Skill;
import com.gas.gasbackend.model.Slice;
import com.gas.gasbackend.service.SliceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/slices")
public class SliceController {

    private final SliceService sliceService;

    public SliceController(final SliceService sliceService) {
        this.sliceService = sliceService;
    }

    @Operation(summary = "Get all slices")
    @GetMapping
    public ResponseEntity<List<Slice>> getAllSlices() {
        return ResponseEntity.ok(sliceService.getAllSlices());
    }

    @Operation(summary = "Get slice by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Slice> getSliceById(
            @Parameter(description = "ID of the slice") @PathVariable final String id) {
        return sliceService.getSliceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new slice")
    @PostMapping
    public ResponseEntity<Slice> createSlice(@RequestBody final Slice slice) {
        return ResponseEntity.ok(sliceService.createSlice(slice));
    }

    @Operation(summary = "Add skill to slice")
    @PostMapping("/{id}/skills")
    public ResponseEntity<Slice> addSkillToSlice(
            @Parameter(description = "Slice ID") @PathVariable final String id,
            @RequestBody final Skill skill) {
        return sliceService.addSkillToSlice(id, skill)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add comment to slice")
    @PostMapping("/{id}/comments")
    public ResponseEntity<Slice> addCommentToSlice(
            @Parameter(description = "Slice ID") @PathVariable final String id,
            @RequestBody final Comment comment) {
        return sliceService.addCommentToSlice(id, comment)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete slice by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlice(@PathVariable final String id) {
        sliceService.deleteSlice(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Retrieve slice by name")
    @GetMapping("/name/{name}")
    public ResponseEntity<Slice> getSliceByName(@PathVariable final String name) {
        return sliceService.getSliceByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Retrieve slices by skill ID")
    @GetMapping("/skills/{skillId}")
    public ResponseEntity<List<Slice>> getSlicesBySkillId(@PathVariable final String skillId) {
        return ResponseEntity.ok(sliceService.getSlicesBySkillId(skillId));
    }
}
