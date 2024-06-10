package com.montrackBackend.montrack.wallet.Controller;

import com.montrackBackend.montrack.response.Response;
import com.montrackBackend.montrack.wallet.dto.WalletDTO;
import com.montrackBackend.montrack.wallet.entity.Wallet;
import com.montrackBackend.montrack.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<Response<Wallet>> addWallet(@RequestBody WalletDTO walletDTO){
        return Response.successResponse("Wallet successfully added", walletService.addWallet(walletDTO));
    }

    @GetMapping
    public ResponseEntity<Response<List<Wallet>>> getAllWallet(){
        return Response.successResponse("Retrieve all wallet!!", walletService.getAllWallet());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Wallet>> getWalletById(@PathVariable Long id){
        return Response.successResponse("Success retrieve wallet", walletService.getWalletById(id));
    }

    @PutMapping("/{id}/set-main")
    public ResponseEntity<Response<Wallet>> setMainWallet(@PathVariable Long id){
        return Response.successResponse("Set wallet with ID: " + id + " Active", walletService.setMainWallet(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Wallet>> updateWallet(@PathVariable Long id, @RequestBody WalletDTO walletDTO){
        return Response.successResponse("Update successfully", walletService.updateWallet(id, walletDTO));
    }
}
