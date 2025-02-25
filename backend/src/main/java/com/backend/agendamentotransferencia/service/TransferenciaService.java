package com.backend.agendamentotransferencia.service;

import com.backend.agendamentotransferencia.error.TransferenciaException;
import com.backend.agendamentotransferencia.model.Transferencia;
import com.backend.agendamentotransferencia.repository.TransferenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class TransferenciaService {
    private final TransferenciaRepository transferenciaRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransferenciaService.class);

    private static final double[] VALOR_TAXA = {2.5, 0.0, 8.2, 6.9, 4.7, 1.7};
    private static final int[] PERIODO_DIAS = {0, 10, 20, 30, 40, 50};

    @Autowired
    public TransferenciaService(TransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    public List<Transferencia> getAllTransferencias() {
        return transferenciaRepository.findAll();
    }

    public Transferencia agendamentoTransferencia(Transferencia transferencia) {
        validateTransferencia(transferencia);

        double taxa = calcularTaxa(transferencia.getDataTransferencia(), transferencia.getValor());
        double valorComTaxa = transferencia.getValor() - (transferencia.getValor() * taxa / 100.0);

        transferencia.setTaxa(taxa);

        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        transferencia.setValor(Double.parseDouble(df.format(valorComTaxa)));

        transferencia.setDataTransferencia(new Date());

        Transferencia saveTransferencia = transferenciaRepository.save(transferencia);

        logger.info("Transferencia agendada com sucesso!: {}", saveTransferencia);

        return saveTransferencia;
    }

    void validateTransferencia(Transferencia transferencia) {
        if (transferencia.getValor() <= 0) {
            throw new TransferenciaException("O valor da transferência deve ser maior que zero.", HttpStatus.BAD_REQUEST);
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate transferenciaLocalDate = transferencia.getDataTransferencia().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (transferenciaLocalDate.isBefore(currentDate)) {
            throw new TransferenciaException("A data de transferência deve ser a partir de hoje.", HttpStatus.BAD_REQUEST);
        }
    }

    private double calcularTaxa(Date transferenciaDate, double valorTransferencia) {
        LocalDate currentDate = LocalDate.now();
        LocalDate transferenciaLocalDate = transferenciaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long days = Math.abs(ChronoUnit.DAYS.between(currentDate, transferenciaLocalDate));

        int index = 0;
        while (index < PERIODO_DIAS.length && days > PERIODO_DIAS[index]) {
            index++;
        }

        if (index < VALOR_TAXA.length) {
            double taxa = valorTransferencia > 0 ? VALOR_TAXA[index] : 0.0;
            logger.info("Taxa calculada: {}", taxa);
            return taxa;
        } else {
            throw new TransferenciaException("Não há taxa aplicável para esta transferência.", HttpStatus.BAD_REQUEST);
        }
    }
}