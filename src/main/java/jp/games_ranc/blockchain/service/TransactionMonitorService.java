/*
package jp.games_ranc.blockchain.service;

import jp.games_ranc.blockchain.exception.BlockchainServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionMonitorService {
    
    private final Web3j web3j;
    private static final int MAX_ATTEMPTS = 40;
    private static final int SLEEP_DURATION = 2;
    
    public void trackTransaction(String transactionHash) {
        CompletableFuture.runAsync(() -> {
            try {
                Optional<TransactionReceipt> receipt = waitForTransactionReceipt(transactionHash);
                if (receipt.isPresent()) {
                    log.info("Transaction {} completed. Status: {}", 
                        transactionHash, 
                        receipt.get().getStatus());
                }
            } catch (Exception e) {
                log.error("Failed to track transaction: {}", transactionHash, e);
                throw new BlockchainServiceException("Failed to track transaction: " + transactionHash, e);
            }
        });
    }
    
    private Optional<TransactionReceipt> waitForTransactionReceipt(String transactionHash) throws Exception {
        int attempts = 0;
        Optional<TransactionReceipt> receipt;
        
        do {
            receipt = web3j.ethGetTransactionReceipt(transactionHash)
                    .send()
                    .getTransactionReceipt();
            
            attempts++;
            if (attempts > MAX_ATTEMPTS) {
                log.warn("Transaction timeout after {} attempts: {}", MAX_ATTEMPTS, transactionHash);
                throw new BlockchainServiceException("Transaction timeout: " + transactionHash);
            }
            
            if (receipt.isEmpty()) {
                TimeUnit.SECONDS.sleep(SLEEP_DURATION);
            }
        } while (receipt.isEmpty());
        
        return receipt;
    }
} */
