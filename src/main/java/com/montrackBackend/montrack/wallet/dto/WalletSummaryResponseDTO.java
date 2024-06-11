package com.montrackBackend.montrack.wallet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WalletSummaryResponseDTO {

    public Long income;

    public Long expense;

    public Long goal;
}
