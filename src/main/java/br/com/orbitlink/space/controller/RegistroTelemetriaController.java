package br.com.orbitlink.space.controller;

import br.com.orbitlink.space.dto.RegistroTelemetriaRequest;
import br.com.orbitlink.space.dto.RegistroTelemetriaResponse;
import br.com.orbitlink.space.service.RegistroTelemetriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telemetrias")
public class RegistroTelemetriaController {

    private final RegistroTelemetriaService registroTelemetriaService;

    public RegistroTelemetriaController(RegistroTelemetriaService registroTelemetriaService) {
        this.registroTelemetriaService = registroTelemetriaService;
    }

    @PostMapping
    public ResponseEntity<RegistroTelemetriaResponse> criar(@RequestBody @Valid RegistroTelemetriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registroTelemetriaService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<RegistroTelemetriaResponse>> listarTodos() {
        return ResponseEntity.ok(registroTelemetriaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroTelemetriaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(registroTelemetriaService.buscarPorId(id));
    }

    @GetMapping("/ativo/{ativoId}")
    public ResponseEntity<List<RegistroTelemetriaResponse>> listarPorAtivoId(@PathVariable Long ativoId) {
        return ResponseEntity.ok(registroTelemetriaService.listarPorAtivoId(ativoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroTelemetriaResponse> atualizar(@PathVariable Long id, @RequestBody @Valid RegistroTelemetriaRequest request) {
        return ResponseEntity.ok(registroTelemetriaService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        registroTelemetriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
