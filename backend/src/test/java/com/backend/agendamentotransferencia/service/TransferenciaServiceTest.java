package com.backend.agendamentotransferencia.service;

import com.backend.agendamentotransferencia.error.TransferenciaException;
import com.backend.agendamentotransferencia.model.Transferencia;
import com.backend.agendamentotransferencia.repository.TransferenciaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransferenciaServiceTest {

    @Autowired
    private TransferenciaService transferenciaService;

    @MockBean
    private TransferenciaRepository transferenciaRepository;

    @Test
    void testGetAllTransfers() {
        List<Transferencia> transferencias = new ArrayList<>();
        when(transferenciaRepository.findAll()).thenReturn(transferencias);

        List<Transferencia> result = transferenciaService.getAllTransferencias();

        assertEquals(transferencias, result);
    }

    @Test
    void testAgendamentoTransferencia() {
        Transferencia transferencia = new Transferencia();
        transferencia.setValor(100.0);
        transferencia.setDataTransferencia(new Date());

        when(transferenciaRepository.save(any(Transferencia.class))).thenReturn(transferencia);

        Transferencia result = transferenciaService.agendamentoTransferencia(transferencia);

        assertNotNull(result);


        assertEquals(97.5, result.getValor(), 0.01);

        assertNotNull(result.getTaxa());
    }

    @Test
    void testInvalidTransferenciaValor() {
        Transferencia transferencia = new Transferencia();
        transferencia.setValor(0.0);
        transferencia.setDataTransferencia(new Date());

        TransferenciaException exception = assertThrows(TransferenciaException.class,
                () -> transferenciaService.validateTransferencia(transferencia));

        assertEquals("O valor da transferência deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void testInvalidDataTransferencia() {
        Transferencia transferencia = new Transferencia();
        transferencia.setValor(100.0);
        transferencia.setDataTransferencia(new Date(System.currentTimeMillis() - 86400000)); // Data no passado

        TransferenciaException exception = assertThrows(TransferenciaException.class,
                () -> transferenciaService.validateTransferencia(transferencia));

        assertEquals("A data de transferência deve ser a partir de hoje.", exception.getMessage());
    }
}
