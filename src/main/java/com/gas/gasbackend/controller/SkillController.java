package com.gas.gasbackend.controller;

import com.gas.gasbackend.model.Skill;
import com.gas.gasbackend.repository.SkillRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/skills")
public class SkillController {

    private final SkillRepository skillRepository;

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable String id) {
        Optional<Skill> skillOpt = skillRepository.findById(id);
        return skillOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        String generatedId = UUID.randomUUID().toString();
        skill.setId(generatedId);
        Skill createdSkill = skillRepository.save(skill);
        return new ResponseEntity<>(createdSkill, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable String id, @RequestBody Skill updatedSkill) {
        Optional<Skill> skillOpt = skillRepository.findById(id);
        if (skillOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Skill skill = skillOpt.get();
        skill.setName(updatedSkill.getName());
        skill.setShapeName(updatedSkill.getShapeName());
        Skill savedSkill = skillRepository.save(skill);
        return ResponseEntity.ok(savedSkill);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable String id) {
        if (!skillRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        skillRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
