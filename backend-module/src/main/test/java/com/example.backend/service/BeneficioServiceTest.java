package com.example.backend.service;

import com.example.backend.model.Beneficio;
import com.example.backend.repository.BeneficioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BeneficioServiceTest {

    @Mock
    private BeneficioRepository repository;

    @InjectMocks
    private BeneficioService service;

    private Beneficio contaOrigem;
    private Beneficio contaDestino;

    @BeforeEach
    void setUp() {
        contaOrigem = new Beneficio();
        contaOrigem.setId(1L);
        contaOrigem.setValor(new BigDecimal("1000.00"));
        contaOrigem.setAtivo(true);

        contaDestino = new Beneficio();
        contaDestino.setId(2L);
        contaDestino.setValor(new BigDecimal("500.00"));
        contaDestino.setAtivo(true);
    }

    @Test
    void deveTransferirValorComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(contaOrigem));
        when(repository.findById(2L)).thenReturn(Optional.of(contaDestino));

        service.transferir(1L, 2L, new BigDecimal("200.00"));

        assertEquals(new BigDecimal("800.00"), contaOrigem.getValor(), "O saldo da origem deve diminuir");
        assertEquals(new BigDecimal("700.00"), contaDestino.getValor(), "O saldo do destino deve aumentar");

        verify(repository, times(1)).save(contaOrigem);
        verify(repository, times(1)).save(contaDestino);
    }

    @Test
    void deveLancarExcecaoQuandoSaldoForInsuficiente() {
        when(repository.findById(1L)).thenReturn(Optional.of(contaOrigem));
        when(repository.findById(2L)).thenReturn(Optional.of(contaDestino));

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            service.transferir(1L, 2L, new BigDecimal("5000.00"));
        });

        assertNotNull(exception.getMessage());
    }
}