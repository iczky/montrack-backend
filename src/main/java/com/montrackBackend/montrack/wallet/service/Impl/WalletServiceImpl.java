package com.montrackBackend.montrack.wallet.service.Impl;

import com.montrackBackend.montrack.exceptions.NotExistException;
import com.montrackBackend.montrack.transactions.entity.TransactionCategory;
import com.montrackBackend.montrack.users.entity.User;
import com.montrackBackend.montrack.users.repository.UserRepository;
import com.montrackBackend.montrack.wallet.dao.WalletSummaryDAO;
import com.montrackBackend.montrack.wallet.dto.WalletDTO;
import com.montrackBackend.montrack.wallet.dto.WalletSummaryResponseDTO;
import com.montrackBackend.montrack.wallet.entity.Wallet;
import com.montrackBackend.montrack.wallet.repository.WalletRepository;
import com.montrackBackend.montrack.wallet.service.WalletService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public WalletServiceImpl(WalletRepository walletRepository, UserRepository userRepository){
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Wallet addWallet(WalletDTO walletDTO) {
        Wallet wallet;
        User user;
        try {
            wallet = new Wallet();

            wallet.setName(walletDTO.getName());
            wallet.setBalance(walletDTO.getBalance());

            user = userRepository.findById(walletDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not Found"));

            if (walletRepository.findByIsMainTrue().isEmpty()) {
                wallet.setMain(true);
            }

            wallet.setUser(user);

            return walletRepository.save(wallet);
        } catch (ConstraintViolationException ex){
            String errorMessage = ex.getConstraintViolations().stream()
                    .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                    .collect(Collectors.joining(", "));
            throw new RuntimeException("Validation failed: " + errorMessage, ex);
        }
    }

    @Override
    public Wallet setMainWallet(Long id) {
        Optional<Wallet> currentWalletOpt = walletRepository.findById(id);
        if (currentWalletOpt.isEmpty()){
            throw new NotExistException("Wallet does not exist");
        }

        Wallet currentWallet = currentWalletOpt.get();

        walletRepository.findByIsMainTrue().ifPresent(activeWallet -> {
            activeWallet.setMain(false);
            walletRepository.save(activeWallet);
        });

        currentWallet.setMain(true);
        walletRepository.save(currentWallet);

        return currentWallet;
    }

    @Override
    public List<Wallet> getAllWallet() {
        return walletRepository.findAll();
    }

    @Override
    public Wallet getWalletById(Long id) {
        Optional<Wallet> currentWalletOpt = walletRepository.findById(id);
        if (currentWalletOpt.isEmpty()){
            throw new NotExistException("Wallet with ID: " + id + " is not exist");
        }
        return currentWalletOpt.get();
    }

    @Override
    public Wallet updateWallet(Long id, WalletDTO walletDTO) {
        Optional<Wallet> walletOpt = walletRepository.findById(id);
        if (walletOpt.isEmpty()){
            throw new NotExistException("Wallet Id is not exist");
        }

        Wallet wallet = walletOpt.get();

        wallet.setName(walletDTO.getName());
        wallet.setBalance(walletDTO.getBalance());

        return walletRepository.save(wallet);

    }

    @Override
    public Map<TransactionCategory, Long> getSummaryByYear() {
        Optional<Wallet> currentWalletOpt = walletRepository.findByIsMainTrue();
        if (currentWalletOpt.isEmpty()){
            throw new NotExistException("Wallet is not exist, please create new wallet");
        }
        Wallet currentWallet = currentWalletOpt.get();
        List<Object[]> resultQuery = walletRepository.getSummaryByYear(currentWallet.getId());
        log.info(resultQuery.toString());
        Map<TransactionCategory, Long> response = new HashMap<>();

        for (Object[] item : resultQuery){
            response.put((TransactionCategory) item[0], (Long) item[1]);
        }

        return response;
    }
}
