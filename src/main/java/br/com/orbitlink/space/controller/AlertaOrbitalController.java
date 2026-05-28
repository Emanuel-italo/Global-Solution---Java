package br.com.orbitlink.space.controller;

import br.com.orbitlink.space.dto.AlertaOrbitalRequest;
import br.com.orbitlink.space.dto.AlertaOrbitalResponse;
import br.com.orbitlink.space.service.AlertaOrbitalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaOrbitalController {

    private final AlertaOrbitalService alertaOrbitalService;

    public AlertaOrbitalController(AlertaOrbitalService alertaOrbitalService) {
        this.alertaOrbitalService = alertaOrbitalService;
    }

    @PostMapping
    public ResponseEntity<AlertaOrbitalResponse> criar(@RequestBody @Valid AlertaOrbitalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alertaOrbitalService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<AlertaOrbitalResponse>> listarTodos() {
        return ResponseEntity.ok(alertaOrbitalService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaOrbitalResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alertaOrbitalService.buscarPorId(id));
    }

    @GetMapping("/ativo/{ativoId}")
    public ResponseEntity<List<AlertaOrbitalResponse>> listarPorAtivoId(@PathVariable Long ativoId) {
        return ResponseEntity.ok(alertaOrbitalService.listarPorAtivoId(ativoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaOrbitalResponse> atualizar(@PathVariable Long id, @RequestBody @Valid AlertaOrbitalRequest request) {
        return ResponseEntity.ok(alertaOrbitalService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alertaOrbitalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
