/*
package jp.games_ranc.blockchain.contract;

import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GamersRanCContract extends Contract {
    
    public static final String BINARY = "컴파일된 바이너리 코드";
    
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
    
    public RemoteCall<TransactionReceipt> formParty(List<String> members) {
        Function function = new Function(
            "formParty",
            Arrays.asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                members.stream()
                    .map(org.web3j.abi.datatypes.Address::new)
                    .collect(Collectors.toList())
            )),
            Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }
} */
