package com.montrackBackend.montrack.wallet.service;

import com.montrackBackend.montrack.transactions.entity.TransactionCategory;
import com.montrackBackend.montrack.wallet.dto.WalletDTO;
import com.montrackBackend.montrack.wallet.dto.WalletSummaryResponseDTO;
import com.montrackBackend.montrack.wallet.entity.Wallet;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WalletService {
    public Wallet addWallet(WalletDTO walletDTO);
    public Wallet setMainWallet(Long id);
    public List<Wallet> getAllWallet();
    public Wallet getWalletById(Long id);
    public Wallet updateWallet (Long id, WalletDTO walletDTO);
    public Map<TransactionCategory, Long> getSummaryByYear();
}
