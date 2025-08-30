package com.resume.restcontrollers;

import com.resume.model.PromoCod;
import com.resume.redisRepository.PromoCodRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promo-cod")
@AllArgsConstructor
public class PromoCodController {

    private final PromoCodRepository repository;

    @PostMapping
    public ResponseEntity<PromoCod> save (@RequestBody PromoCod promoCod) {
        PromoCod saved = repository.save(promoCod);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromoCod> getById (@PathVariable Long id) {
        var promoCod = repository.findById(id);
        return promoCod.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PromoCod>> getAll () {
        var promoCod = repository.findAll();
        return ResponseEntity.ok(promoCod);
    }

}
