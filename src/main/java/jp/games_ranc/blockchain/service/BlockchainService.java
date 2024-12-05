/*
package jp.games_ranc.blockchain.service;

import jp.games_ranc.blockchain.contract.GamersRanCContract;
import jp.games_ranc.blockchain.exception.BlockchainServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import jakarta.annotation.PostConstruct;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockchainService {
    
    @Value("${blockchain.contract.address}")
    private String contractAddress;
    
    private final Web3j web3j;
    private final KeyStoreService keyStoreService;
    private final TransactionMonitorService monitorService;
    
    private GamersRanCContract contract;
    
    @PostConstruct
    public void initializeBlockchain() {
        try {
            Credentials credentials = keyStoreService.loadCredentials();
            ContractGasProvider gasProvider = new StaticGasProvider(
                web3j.ethGasPrice().send().getGasPrice(),
                BigInteger.valueOf(6721975L)  // gas limit
            );
            
            contract = GamersRanCContract.load(
                contractAddress,
                web3j,
                credentials,
                gasProvider
            );
            
            log.info("Blockchain service initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize blockchain service", e);
            throw new BlockchainServiceException("Failed to initialize blockchain", e);
        }
    }
    
    public CompletableFuture<TransactionReceipt> registerUser(String userAddress) {
        try {
            return contract.registerUser()
                .sendAsync()
                .thenApply(receipt -> {
                    log.info("사용자 등록 완료: {}, Transaction Hash: {}", userAddress, receipt.getTransactionHash());
                    monitorService.trackTransaction(receipt.getTransactionHash());
                    return receipt;
                })
                .exceptionally(e -> {
                    log.error("사용자 등록 실패: {}", userAddress, e);
                    throw new BlockchainServiceException("블록체인 사용자 등록 실패", e);
                });
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            log.error("사용자 등록 처리 실패: {}", userAddress, e);
            throw new BlockchainServiceException("블록체인 사용자 등록 처리 실패", e);
        }
    }
    
    public CompletableFuture<TransactionReceipt> checkIn(String userAddress) {
        try {
            return contract.checkIn()
                .sendAsync()
                .thenApply(receipt -> {
                    log.info("출석 체크 완료: {}, Transaction Hash: {}", userAddress, receipt.getTransactionHash());
                    monitorService.trackTransaction(receipt.getTransactionHash());
                    return receipt;
                })
                .exceptionally(e -> {
                    log.error("출석 체크 실패: {}", userAddress, e);
                    throw new BlockchainServiceException("블록체인 출석 체크 실패", e);
                });
        } catch (Exception e) {
            log.error("출석 체크 처리 실패: {}", userAddress, e);
            throw new BlockchainServiceException("블록체인 출석 체크 처리 실패", e);
        }
    }
    
    public CompletableFuture<TransactionReceipt> createPost(String userAddress, String contentUri, int postType) {
        try {
            return contract.createPost(contentUri, BigInteger.valueOf(postType))
                .sendAsync()
                .thenApply(receipt -> {
                    log.info("게시물 생성 완료: {}, 작성자: {}, Transaction Hash: {}", 
                        contentUri, userAddress, receipt.getTransactionHash());
                    monitorService.trackTransaction(receipt.getTransactionHash());
                    return receipt;
                })
                .exceptionally(e -> {
                    log.error("게시물 생성 실패: {}, 작성자: {}", contentUri, userAddress, e);
                    throw new BlockchainServiceException("블록체인 게시물 생성 실패", e);
                });
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            log.error("게시물 생성 처리 실패: {}, 작성자: {}", contentUri, userAddress, e);
            throw new BlockchainServiceException("블록체인 게시물 생성 처리 실패", e);
        }
    }
    
    public CompletableFuture<TransactionReceipt> voteWithPoints(String userAddress, long postId, long points) {
        try {
            return contract.voteWithPoints(BigInteger.valueOf(postId), BigInteger.valueOf(points))
                .sendAsync()
                .thenApply(receipt -> {
                    log.info("포인트 투표 완료: 게시물 ID: {}, 투표자: {}, 포인트: {}, Transaction Hash: {}", 
                        postId, userAddress, points, receipt.getTransactionHash());
                    monitorService.trackTransaction(receipt.getTransactionHash());
                    return receipt;
                })
                .exceptionally(e -> {
                    log.error("포인트 투표 실패: 게시물 ID: {}, 투표자: {}", postId, userAddress, e);
                    throw new BlockchainServiceException("블록체인 포인트 투표 실패", e);
                });
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            log.error("포인트 투표 처리 실패: 게시물 ID: {}, 투표자: {}", postId, userAddress, e);
            throw new BlockchainServiceException("블록체인 포인트 투표 처리 실패", e);
        }
    }
    
    public CompletableFuture<TransactionReceipt> formParty(String leaderAddress, List<String> members) {
        try {
            List<String> updatedMembers = new ArrayList<>(members);
            if (!updatedMembers.contains(leaderAddress)) {
                updatedMembers.add(0, leaderAddress);
            }
            
            return contract.formParty(updatedMembers)
                .sendAsync()
                .thenApply(receipt -> {
                    log.info("파티 형성 완료: 리더: {}, 멤버: {}, Transaction Hash: {}", 
                        leaderAddress, updatedMembers, receipt.getTransactionHash());
                    monitorService.trackTransaction(receipt.getTransactionHash());
                    return receipt;
                })
                .exceptionally(e -> {
                    log.error("파티 형성 실패: 리더: {}, 멤버: {}", leaderAddress, updatedMembers, e);
                    throw new BlockchainServiceException("블록체인 파티 형성 실패", e);
                });
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            log.error("파티 형성 처리 실패: 리더: {}, 멤버: {}", leaderAddress, members, e);
            throw new BlockchainServiceException("블록체인 파티 형성 처리 실패", e);
        }
    }
} */
