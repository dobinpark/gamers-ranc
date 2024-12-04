package jp.games_ranc.blockchain.contract;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class GamersRanCContract extends Contract {
    
    private static final String BINARY = "여기에 컴파일된 스마트 컨트랙트 바이너리 코드가 들어갑니다";
    
    protected GamersRanCContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(BINARY, contractAddress, web3j, credentials, gasProvider);
    }
    
    public static GamersRanCContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        return new GamersRanCContract(contractAddress, web3j, credentials, gasProvider);
    }
    
    public RemoteCall<TransactionReceipt> registerUser() {
        Function function = new Function(
            "registerUser",
            Collections.emptyList(),
            Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }
    
    public RemoteCall<TransactionReceipt> checkIn() {
        Function function = new Function(
            "checkIn",
            Collections.emptyList(),
            Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }
    
    public RemoteCall<TransactionReceipt> createPost(String contentUri, BigInteger postType) {
        Function function = new Function(
            "createPost",
            Arrays.asList(
                new org.web3j.abi.datatypes.Utf8String(contentUri),
                new org.web3j.abi.datatypes.generated.Uint256(postType)
            ),
            Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }
    
    public RemoteCall<TransactionReceipt> voteWithPoints(BigInteger postId, BigInteger points) {
        Function function = new Function(
            "voteWithPoints",
            Arrays.asList(
                new org.web3j.abi.datatypes.generated.Uint256(postId),
                new org.web3j.abi.datatypes.generated.Uint256(points)
            ),
            Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }
    
    public RemoteCall<TransactionReceipt> formParty(java.util.List<String> members) {
        Function function = new Function(
            "formParty",
            Arrays.asList(
                new org.web3j.abi.datatypes.DynamicArray<>(
                    org.web3j.abi.datatypes.Address.class,
                    org.web3j.abi.Utils.typeMap(members, org.web3j.abi.datatypes.Address.class)
                )
            ),
            Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }
} 