package jp.games_ranc.blockchain.contract;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

public class GamersRanCContract extends Contract {
    
    private static final String BINARY = "여기에 컴파일된 스마트 컨트랙트 바이너리 코드가 들어갑니다";
    
    public GamersRanCContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(BINARY, contractAddress, web3j, credentials, gasProvider);
    }
    
    public RemoteCall<TransactionReceipt> registerUser() {
        return executeRemoteCallTransaction(
            this.getFunction("registerUser")
        );
    }
    
    public RemoteCall<TransactionReceipt> checkIn() {
        return executeRemoteCallTransaction(
            this.getFunction("checkIn")
        );
    }
    
    public RemoteCall<TransactionReceipt> createPost(String contentUri, BigInteger postType) {
        return executeRemoteCallTransaction(
            this.getFunction("createPost", contentUri, postType)
        );
    }
    
    public RemoteCall<TransactionReceipt> voteWithPoints(BigInteger postId, BigInteger points) {
        return executeRemoteCallTransaction(
            this.getFunction("voteWithPoints", postId, points)
        );
    }
} 