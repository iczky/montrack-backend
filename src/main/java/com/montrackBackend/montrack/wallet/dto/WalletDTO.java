package com.montrackBackend.montrack.wallet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WalletDTO {
    @Size(max = 50)
    @NotNull(message = "Name cannot be empty")
    private String name;

    @NotNull
    private Long userId;

    @NotNull
    private Long balance;
}
