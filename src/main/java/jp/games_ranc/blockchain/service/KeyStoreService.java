package jp.games_ranc.blockchain.service;

import jp.games_ranc.blockchain.exception.BlockchainServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;

@Service
public class KeyStoreService {
    
    @Value("${blockchain.keystore.path}")
    private String keystorePath;
    
    @Value("${blockchain.keystore.password}")
    private String keystorePassword;
    
    public Credentials loadCredentials() {
        try {
            File keystoreFile = new File(keystorePath);
            if (!keystoreFile.exists()) {
                throw new BlockchainServiceException("Keystore file not found: " + keystorePath);
            }
            return WalletUtils.loadCredentials(keystorePassword, keystoreFile);
        } catch (Exception e) {
            throw new BlockchainServiceException("Failed to load credentials from keystore", e);
        }
    }
} 