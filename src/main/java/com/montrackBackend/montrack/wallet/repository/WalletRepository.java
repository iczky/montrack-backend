package com.montrackBackend.montrack.wallet.repository;

import com.montrackBackend.montrack.wallet.dao.WalletSummaryDAO;
import com.montrackBackend.montrack.wallet.dto.WalletSummaryResponseDTO;
import com.montrackBackend.montrack.wallet.entity.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Wallet w SET w.deleted_at = CURRENT_TIMESTAMP WHERE w.id = :id")
    void softDeleteById(Long id);

    Optional<Wallet> findByIsMainTrue();

    @Transactional
    @Query("SELECT category, SUM(amount) as total_amount_this_year from Transactions WHERE walletId = 5 and created_at >= DATE_TRUNC('year', CURRENT_DATE) GROUP BY category")
    List<Object[]> getSummaryByYear(@Param("id") Long id);
}
