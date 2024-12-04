package jp.games_ranc.blockchain.service;

import jp.games_ranc.blockchain.contract.GamersRanCContract;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class BlockchainService {
    
    @Value("${blockchain.contract.address}")
    private String contractAddress;
    
    @Value("${blockchain.network.url}")
    private String networkUrl;
    
    @Value("${blockchain.wallet.private-key}")
    private String privateKey;
    
    private Web3j web3j;
    private GamersRanCContract contract;
    
    public void initializeBlockchain() {
        web3j = Web3j.build(new HttpService(networkUrl));
        Credentials credentials = Credentials.create(privateKey);
        
        ContractGasProvider gasProvider = new StaticGasProvider(
            BigInteger.valueOf(20000000000L), // gas price
            BigInteger.valueOf(6721975L)      // gas limit
        );
        
        contract = new GamersRanCContract(
            contractAddress,
            web3j,
            credentials,
            gasProvider
        );
    }
    
    public void registerUser(String userAddress) throws Exception {
        contract.registerUser().send();
    }
    
    public void checkIn(String userAddress) throws Exception {
        contract.checkIn().send();
    }
    
    public void createPost(String userAddress, String contentUri, int postType) throws Exception {
        contract.createPost(contentUri, BigInteger.valueOf(postType)).send();
    }
    
    public void voteWithPoints(String userAddress, long postId, long points) throws Exception {
        contract.voteWithPoints(
            BigInteger.valueOf(postId),
            BigInteger.valueOf(points)
        ).send();
    }
} 