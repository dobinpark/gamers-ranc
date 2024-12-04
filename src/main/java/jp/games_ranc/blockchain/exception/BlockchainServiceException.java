package jp.games_ranc.blockchain.exception;

public class BlockchainServiceException extends RuntimeException {

    public BlockchainServiceException(String message) {
        super(message);
    }

    public BlockchainServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}