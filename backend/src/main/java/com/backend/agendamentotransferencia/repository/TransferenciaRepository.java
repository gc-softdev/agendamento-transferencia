package com.backend.agendamentotransferencia.repository;

import com.backend.agendamentotransferencia.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
}