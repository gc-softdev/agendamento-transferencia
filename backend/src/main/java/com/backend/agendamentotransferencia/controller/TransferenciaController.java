package com.backend.agendamentotransferencia.controller;

import com.backend.agendamentotransferencia.error.TransferenciaException;
import com.backend.agendamentotransferencia.model.Transferencia;
import com.backend.agendamentotransferencia.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/transferencias")
public class TransferenciaController {
    private final TransferenciaService transferenciaService;

    @Autowired
    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @GetMapping
    public List<Transferencia> getAllTransferencias() {
        return transferenciaService.getAllTransferencias();
    }

    @PostMapping
    public ResponseEntity<?> agendamentoTransferencia(@RequestBody Transferencia transferencia) {
        try {
            Transferencia savedTransferencia = transferenciaService.agendamentoTransferencia(transferencia);
            return ResponseEntity.ok(savedTransferencia);
        } catch (TransferenciaException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}