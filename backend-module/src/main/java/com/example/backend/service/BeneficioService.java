package com.example.backend.service;

import com.example.backend.model.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BeneficioService {

    @Autowired
    private BeneficioRepository repository;

    @Transactional
    public void transferir(Long fromId, Long toId, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }
        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("As contas de origem e destino não podem ser a mesma.");
        }

        Beneficio from = repository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada."));
        Beneficio to = repository.findById(toId)
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada."));

        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente para realizar a transferência.");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        repository.save(from);
        repository.save(to);
    }
}