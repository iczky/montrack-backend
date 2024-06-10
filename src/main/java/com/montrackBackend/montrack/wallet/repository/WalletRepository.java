package com.montrackBackend.montrack.wallet.repository;

import com.montrackBackend.montrack.wallet.entity.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Wallet w SET w.deleted_at = CURRENT_TIMESTAMP WHERE w.id = :id")
    void softDeleteById(Long id);

    Optional<Wallet> findByIsMainTrue();

}
