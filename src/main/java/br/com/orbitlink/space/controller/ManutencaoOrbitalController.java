package br.com.orbitlink.space.controller;

import br.com.orbitlink.space.dto.ManutencaoOrbitalRequest;
import br.com.orbitlink.space.dto.ManutencaoOrbitalResponse;
import br.com.orbitlink.space.service.ManutencaoOrbitalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manutencoes")
public class ManutencaoOrbitalController {

    private final ManutencaoOrbitalService manutencaoOrbitalService;

    public ManutencaoOrbitalController(ManutencaoOrbitalService manutencaoOrbitalService) {
        this.manutencaoOrbitalService = manutencaoOrbitalService;
    }

    @PostMapping
    public ResponseEntity<ManutencaoOrbitalResponse> criar(@RequestBody @Valid ManutencaoOrbitalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(manutencaoOrbitalService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<ManutencaoOrbitalResponse>> listarTodos() {
        return ResponseEntity.ok(manutencaoOrbitalService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManutencaoOrbitalResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(manutencaoOrbitalService.buscarPorId(id));
    }

    @GetMapping("/ativo/{ativoId}")
    public ResponseEntity<List<ManutencaoOrbitalResponse>> listarPorAtivoId(@PathVariable Long ativoId) {
        return ResponseEntity.ok(manutencaoOrbitalService.listarPorAtivoId(ativoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManutencaoOrbitalResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ManutencaoOrbitalRequest request) {
        return ResponseEntity.ok(manutencaoOrbitalService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        manutencaoOrbitalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
