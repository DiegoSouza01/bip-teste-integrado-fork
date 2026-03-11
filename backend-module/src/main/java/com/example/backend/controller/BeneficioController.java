package com.example.backend.controller;

import com.example.backend.dto.TransferenciaDTO;
import com.example.backend.model.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import com.example.backend.service.BeneficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/beneficios")
public class BeneficioController {

    @Autowired
    private BeneficioRepository repository;

    @Autowired
    private BeneficioService beneficioService;

    @GetMapping
    public List<Beneficio> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficio> buscarPorId(@PathVariable Long id) {
        Optional<Beneficio> beneficio = repository.findById(id);
        return beneficio.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Beneficio> criar(@RequestBody Beneficio beneficio) {
        Beneficio salvo = repository.save(beneficio);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beneficio> atualizar(@PathVariable Long id, @RequestBody Beneficio atualizado) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        atualizado.setId(id);
        return ResponseEntity.ok(repository.save(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferir(@RequestBody TransferenciaDTO dto) {
        try {
            beneficioService.transferir(dto.getFromId(), dto.getToId(), dto.getAmount());
            return ResponseEntity.ok("Transferência realizada com sucesso!");

        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno na transferência: " + e.getMessage());
        }
    }
}