package com.montrackBackend.montrack.wallet.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WalletSummaryDAO {
    @Id
    @Column(name = "category")
    public String category;
    @Column(name = "sum")
    public Long sum;
}
