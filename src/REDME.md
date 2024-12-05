# Games RanC Blockchain Integration

## 환경 설정
1. 환경 변수 설정:
   ```
   BLOCKCHAIN_NETWORK_URL=http://localhost:8545
   BLOCKCHAIN_CONTRACT_ADDRESS=your_contract_address
   BLOCKCHAIN_KEYSTORE_PATH=/path/to/keystore
   BLOCKCHAIN_KEYSTORE_PASSWORD=your_keystore_password
   ```

2. 스마트 컨트랙트 배포:
    - Remix IDE나 Truffle을 사용하여 GamersRanC.sol 컨트랙트를 배포
    - 배포된 컨트랙트 주소를 환경 변수에 설정

3. 키스토어 생성:
    - Web3j 명령줄 도구를 사용하여 키스토어 파일 생성
    - 생성된 키스토어 파일 경로와 비밀번호를 환경 변수에 설정