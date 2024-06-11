package com.montrackBackend.montrack.transactions.entity;

import com.montrackBackend.montrack.wallet.entity.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "transactions", schema = "public")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_gen")
    @SequenceGenerator(name = "transaction_id_gen", sequenceName = "transaction_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "wallet_id", nullable = false)
    private Long walletId;

    @JoinColumn(name = "pocket_id")
    private Long pocketId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Category cannot be null")
    @Column(name = "category", nullable = false)
    private TransactionCategory category;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must not be negative")
    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleted_at;
}
