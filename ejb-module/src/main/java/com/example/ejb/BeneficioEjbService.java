package com.example.ejb;

import com.example.backend.model.Beneficio;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }
        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("As contas de origem e destino não podem ser a mesma.");
        }

        Long firstLockId = fromId < toId ? fromId : toId;
        Long secondLockId = fromId < toId ? toId : fromId;

        Beneficio firstLock = em.find(Beneficio.class, firstLockId, LockModeType.PESSIMISTIC_WRITE);
        Beneficio secondLock = em.find(Beneficio.class, secondLockId, LockModeType.PESSIMISTIC_WRITE);

        Beneficio from = fromId.equals(firstLockId) ? firstLock : secondLock;
        Beneficio to = toId.equals(firstLockId) ? firstLock : secondLock;

        if (from == null || to == null) {
            throw new IllegalArgumentException("Conta de origem ou destino não encontrada.");
        }

        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente para realizar a transferência.");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));
    }
}