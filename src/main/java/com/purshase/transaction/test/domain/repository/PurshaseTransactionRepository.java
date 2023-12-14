package com.purshase.transaction.test.domain.repository;

import com.purshase.transaction.test.domain.model.entities.PurshaseTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PurshaseTransactionRepository extends JpaRepository<PurshaseTransaction, UUID> {

}
