package com.gas.gasbackend.controller;

import com.gas.gasbackend.model.Slice;
import com.gas.gasbackend.repository.SliceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/slices")
public class SliceController {

    private final SliceRepository sliceRepository;

    public SliceController(SliceRepository sliceRepository) {
        this.sliceRepository = sliceRepository;
    }

    // Récupère toutes les slices
    @GetMapping
    public ResponseEntity<List<Slice>> getAllSlices() {
        return ResponseEntity.ok(sliceRepository.findAll());
    }

    // Récupère une slice par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Slice> getSliceById(@PathVariable String id) {
        Optional<Slice> sliceOpt = sliceRepository.findById(id);
        return sliceOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crée une nouvelle slice
    @PostMapping
    public ResponseEntity<Slice> createSlice(@RequestBody Slice slice) {
        // Génère un ID unique et l'affecte directement via le setter généré par Lombok
        String generatedId = UUID.randomUUID().toString();
        slice.setID(generatedId);
        sliceRepository.save(slice);
        return new ResponseEntity<>(slice, HttpStatus.CREATED);
    }

    // Met à jour une slice existante
    @PutMapping("/{id}")
    public ResponseEntity<Slice> updateSlice(@PathVariable String id, @RequestBody Slice updatedSlice) {
        Optional<Slice> sliceOpt = sliceRepository.findById(id);
        if (sliceOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Slice slice = sliceOpt.get();
        // Mise à jour des champs modifiables
        slice.setName(updatedSlice.getName());
        slice.setLikes(updatedSlice.getLikes());
        slice.getSkills().clear();
        slice.getSkills().addAll(updatedSlice.getSkills());
        slice.getComments().clear();
        slice.getComments().addAll(updatedSlice.getComments());

        sliceRepository.save(slice);
        return ResponseEntity.ok(slice);
    }

    // Supprime une slice par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlice(@PathVariable String id) {
        if (!sliceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        sliceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
