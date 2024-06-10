package com.montrackBackend.montrack.wallet.service.Impl;

import com.montrackBackend.montrack.exceptions.NotExistException;
import com.montrackBackend.montrack.users.entity.User;
import com.montrackBackend.montrack.users.repository.UserRepository;
import com.montrackBackend.montrack.wallet.dto.WalletDTO;
import com.montrackBackend.montrack.wallet.entity.Wallet;
import com.montrackBackend.montrack.wallet.repository.WalletRepository;
import com.montrackBackend.montrack.wallet.service.WalletService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public WalletServiceImpl(WalletRepository walletRepository, UserRepository userRepository){
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Wallet addWallet(WalletDTO walletDTO) {
        Wallet wallet = new Wallet();

        wallet.setName(walletDTO.getName());
        wallet.setBalance(walletDTO.getBalance());

        User user = userRepository.findById(walletDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not Found"));

        if (walletRepository.findByIsMainTrue().isEmpty()){
            wallet.setMain(true);
        }

        wallet.setUser(user);

        return walletRepository.save(wallet);
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
}
