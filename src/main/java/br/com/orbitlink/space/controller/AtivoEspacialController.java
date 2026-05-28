package br.com.orbitlink.space.controller;

import br.com.orbitlink.space.dto.AtivoEspacialRequest;
import br.com.orbitlink.space.dto.AtivoEspacialResponse;
import br.com.orbitlink.space.service.AtivoEspacialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ativos")
public class AtivoEspacialController {

    private final AtivoEspacialService ativoEspacialService;

    public AtivoEspacialController(AtivoEspacialService ativoEspacialService) {
        this.ativoEspacialService = ativoEspacialService;
    }

    @PostMapping
    public ResponseEntity<AtivoEspacialResponse> criar(@RequestBody @Valid AtivoEspacialRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ativoEspacialService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<AtivoEspacialResponse>> listarTodos() {
        return ResponseEntity.ok(ativoEspacialService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtivoEspacialResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ativoEspacialService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtivoEspacialResponse> atualizar(@PathVariable Long id, @RequestBody @Valid AtivoEspacialRequest request) {
        return ResponseEntity.ok(ativoEspacialService.atualizar(id, request));
    }

    @PatchMapping("/{id}/descomissionar")
    public ResponseEntity<Void> descomissionar(@PathVariable Long id) {
        ativoEspacialService.descomissionar(id);
        return ResponseEntity.noContent().build();
    }
}
