package com.backend.agendamentotransferencia.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Transferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contaOrigem;
    private String contaDestino;
    private double valor;
    private double taxa;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Sao_Paulo")
    private Date dataTransferencia;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private Date dataAgendamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(String contaOrigem) {
        if (contaOrigem == null || contaOrigem.trim().isEmpty()) {
            throw new RuntimeException("A conta de origem não pode ser nula ou vazia.");
        }
        this.contaOrigem = contaOrigem;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(String contaDestino) {
        if (contaDestino == null || contaDestino.trim().isEmpty()) {
            throw new RuntimeException("A conta de destino não pode ser nula ou vazia.");
        }
        this.contaDestino = contaDestino;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        if (valor < 0) {
            throw new RuntimeException("O valor da transferência tem que ser maior que zero.");
        }
        this.valor = valor;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        if (taxa < 0) {
            throw new RuntimeException("A taxa da transferência tem que ser maior que zero.");
        }
        this.taxa = taxa;
    }

    public Date getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(Date dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    @Override
    public String toString() {
        return "Transferencia{" +
                "id=" + id +
                ", contaOrigem='" + contaOrigem + '\'' +
                ", contaDestino='" + contaDestino + '\'' +
                ", valor=" + valor +
                ", taxa=" + taxa +
                ", dataTransferencia=" + dataTransferencia +
                ", dataAgendamento=" + dataAgendamento +
                '}';
    }
}