package com.resume.restcontrollers;

import com.resume.model.PromoCod;
import com.resume.redisRepository.PromoCodRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promo-cod")
@AllArgsConstructor
public class PromoCodController {

    private final PromoCodRepository repository;

  //  @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(
            summary = "Get all promo codes",
            security = @SecurityRequirement(name = "bearerAuth")  // ← ЭТО ОБЯЗАТЕЛЬНО
    )
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
   // @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get all promo codes",
            security = @SecurityRequirement(name = "bearerAuth")  // ← ЭТО ОБЯЗАТЕЛЬНО
    )
    public ResponseEntity<List<PromoCod>> getAll () {
        var promoCod = repository.findAll();
        return ResponseEntity.ok(promoCod);
    }


}
