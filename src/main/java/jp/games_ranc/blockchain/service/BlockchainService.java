package jp.games_ranc.blockchain.service;

import jp.games_ranc.blockchain.contract.GamersRanCContract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import jakarta.annotation.PostConstruct;

import java.math.BigInteger;
import java.util.List;

@Slf4j
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
    private ContractGasProvider gasProvider;
    
    @PostConstruct
    public void initializeBlockchain() {
        try {
            web3j = Web3j.build(new HttpService(networkUrl));
            Credentials credentials = Credentials.create(privateKey);
            
            gasProvider = new StaticGasProvider(
                BigInteger.valueOf(20000000000L), // gas price
                BigInteger.valueOf(6721975L)      // gas limit
            );
            
            contract = GamersRanCContract.load(
                contractAddress,
                web3j,
                credentials,
                gasProvider
            );
            
            log.info("블록체인 서비스가 초기화되었습니다. 네트워크: {}", networkUrl);
        } catch (Exception e) {
            log.error("블록체인 서비스 초기화 실패", e);
            throw new RuntimeException("블록체인 서비스 초기화 실패", e);
        }
    }
    
    public void registerUser(String userAddress) {
        try {
            contract.registerUser().send();
            log.info("사용자 등록 완료: {}", userAddress);
        } catch (Exception e) {
            log.error("사용자 등록 실패: {}", userAddress, e);
            throw new RuntimeException("블록체인 사용자 등록 실패", e);
        }
    }
    
    public void checkIn(String userAddress) {
        try {
            contract.checkIn().send();
            log.info("출석 체크 완료: {}", userAddress);
        } catch (Exception e) {
            log.error("출석 체크 실패: {}", userAddress, e);
            throw new RuntimeException("블록체인 출석 체크 실패", e);
        }
    }
    
    public void createPost(String userAddress, String contentUri, int postType) {
        try {
            contract.createPost(contentUri, BigInteger.valueOf(postType)).send();
            log.info("게시물 생성 완료: {}, 작성자: {}", contentUri, userAddress);
        } catch (Exception e) {
            log.error("게시물 생성 실패: {}, 작성자: {}", contentUri, userAddress, e);
            throw new RuntimeException("블록체인 게시물 생성 실패", e);
        }
    }
    
    public void voteWithPoints(String userAddress, long postId, long points) {
        try {
            contract.voteWithPoints(
                BigInteger.valueOf(postId),
                BigInteger.valueOf(points)
            ).send();
            log.info("포인트 투표 완료: 게시물 ID: {}, 투표자: {}, 포인트: {}", postId, userAddress, points);
        } catch (Exception e) {
            log.error("포인트 투표 실패: 게시물 ID: {}, 투표자: {}", postId, userAddress, e);
            throw new RuntimeException("블록체인 포인트 투표 실패", e);
        }
    }
    
    public void formParty(String leaderAddress, List<String> members) {
        try {
            contract.formParty(members).send();
            log.info("파티 형성 완료: 리더: {}, 멤버: {}", leaderAddress, members);
        } catch (Exception e) {
            log.error("파티 형성 실패: 리더: {}", leaderAddress, e);
            throw new RuntimeException("블록체인 파티 형성 실패", e);
        }
    }
} 