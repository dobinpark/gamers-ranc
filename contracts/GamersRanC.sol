// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/token/ERC20/IERC20.sol";
import "@openzeppelin/contracts/security/ReentrancyGuard.sol";

contract GamersRanC is Ownable, ReentrancyGuard {
    // 상태 변수
    IERC20 public token;  // 플랫폼 토큰
    
    struct Post {
        uint256 id;
        address author;
        string contentUri;  // IPFS 또는 다른 저장소 URI
        uint256 regularVotes;
        uint256 pointVotes;
        uint256 timestamp;
        bool isActive;
        PostType postType;
    }
    
    struct User {
        uint256 points;
        uint256 lastCheckIn;
        address[] party;
        uint256 totalEarnings;
        bool isRegistered;
    }
    
    enum PostType { GAME_LINK, REVIEW }
    
    // 매핑
    mapping(address => User) public users;
    mapping(uint256 => Post) public posts;
    mapping(uint256 => mapping(address => uint256)) public userVotes;
    
    // 상수
    uint256 public constant DAILY_POINTS = 100;
    uint256 public constant PARTY_BONUS = 50;
    uint256 public constant MIN_VOTE_POINTS = 10;
    uint256 public constant PARTY_SIZE = 4;
    
    // 이벤트
    event UserRegistered(address indexed user);
    event PostCreated(uint256 indexed postId, address indexed author, PostType postType);
    event PointsAwarded(address indexed user, uint256 amount, string reason);
    event VoteCast(uint256 indexed postId, address indexed voter, uint256 points);
    event PartyFormed(address indexed leader, address[] members);
    event RewardDistributed(address indexed user, uint256 amount);
    
    // 생성자
    constructor(address _tokenAddress) {
        token = IERC20(_tokenAddress);
    }
    
    // 사용자 등록
    function registerUser() external {
        require(!users[msg.sender].isRegistered, "User already registered");
        
        users[msg.sender].isRegistered = true;
        users[msg.sender].points = 0;
        users[msg.sender].lastCheckIn = 0;
        
        emit UserRegistered(msg.sender);
    }
    
    // 출석 체크
    function checkIn() external {
        require(users[msg.sender].isRegistered, "User not registered");
        require(
            block.timestamp >= users[msg.sender].lastCheckIn + 1 days,
            "Already checked in today"
        );
        
        uint256 pointsToAward = DAILY_POINTS;
        
        // 파티 보너스 계산
        if (users[msg.sender].party.length == PARTY_SIZE) {
            pointsToAward += PARTY_BONUS;
        }
        
        users[msg.sender].points += pointsToAward;
        users[msg.sender].lastCheckIn = block.timestamp;
        
        emit PointsAwarded(msg.sender, pointsToAward, "Daily check-in");
    }
    
    // 게시물 생성
    function createPost(string memory _contentUri, PostType _postType) external {
        require(users[msg.sender].isRegistered, "User not registered");
        
        uint256 postId = uint256(keccak256(abi.encodePacked(block.timestamp, msg.sender, _contentUri)));
        
        posts[postId] = Post({
            id: postId,
            author: msg.sender,
            contentUri: _contentUri,
            regularVotes: 0,
            pointVotes: 0,
            timestamp: block.timestamp,
            isActive: true,
            postType: _postType
        });
        
        emit PostCreated(postId, msg.sender, _postType);
    }
    
    // 포인트로 투표
    function voteWithPoints(uint256 _postId, uint256 _points) external {
        require(users[msg.sender].isRegistered, "User not registered");
        require(posts[_postId].isActive, "Post is not active");
        require(_points >= MIN_VOTE_POINTS, "Points below minimum");
        require(users[msg.sender].points >= _points, "Insufficient points");
        
        users[msg.sender].points -= _points;
        posts[_postId].pointVotes += _points;
        userVotes[_postId][msg.sender] += _points;
        
        emit VoteCast(_postId, msg.sender, _points);
    }
    
    // 파티 형성
    function formParty(address[] calldata _members) external {
        require(users[msg.sender].isRegistered, "User not registered");
        require(_members.length == PARTY_SIZE - 1, "Invalid party size");
        require(users[msg.sender].party.length == 0, "Already in a party");
        
        for (uint i = 0; i < _members.length; i++) {
            require(users[_members[i]].isRegistered, "Member not registered");
            require(users[_members[i]].party.length == 0, "Member already in a party");
        }
        
        address[] memory partyMembers = new address[](PARTY_SIZE);
        partyMembers[0] = msg.sender;
        for (uint i = 0; i < _members.length; i++) {
            partyMembers[i + 1] = _members[i];
        }
        
        for (uint i = 0; i < partyMembers.length; i++) {
            users[partyMembers[i]].party = partyMembers;
        }
        
        emit PartyFormed(msg.sender, partyMembers);
    }
    
    // 보상 분배 (관리자 전용)
    function distributeRewards(address[] calldata _recipients, uint256[] calldata _amounts) 
        external 
        onlyOwner 
        nonReentrant 
    {
        require(_recipients.length == _amounts.length, "Arrays length mismatch");
        
        for (uint i = 0; i < _recipients.length; i++) {
            require(users[_recipients[i]].isRegistered, "Recipient not registered");
            require(token.transfer(_recipients[i], _amounts[i]), "Token transfer failed");
            
            emit RewardDistributed(_recipients[i], _amounts[i]);
        }
    }
}

/*
1. 기본 구조
∙ oOpenZeppelin의 Ownable과 ReentrancyGuard를 상속하여 보안성 강화
∙ ERC20 토큰 인터페이스를 통한 토큰 관리
2. 데이터 구조
∙ Post: 게시물 정보 (ID, 작성자, 컨텐츠 URI, 투표 수 등)
∙User: 사용자 정보 (포인트, 출석체크, 파티원 등)
PostType: 게시물 유형 (게임 링크/리뷰)
3. 주요 기능
∙ registerUser(): 사용자 등록
∙ checkIn(): 일일 출석 체크 및 포인트 지급
∙ ∙createPost(): 게시물 작성
∙ voteWithPoints(): 포인트를 사용한 투표
∙ formParty(): 4인 파티 구성
∙ distributeRewards(): 보상 분배 (관리자 전용)
4. 상수 설정
∙ DAILY_POINTS: 일일 출석 포인트 (100)
∙ PARTY_BONUS: 파티 보너스 (50)
∙ MIN_VOTE_POINTS: 최소 투표 포인트 (10)
∙ PARTY_SIZE: 파티 크기 (4)

이 스마트 컨트랙트를 사용하기 위해서는 다음이 필요
1. OpenZeppelin 라이브러리 설치
2. ERC20 토큰 컨트랙트 배포 (또는 기존 토큰 사용)
3. 컨트랙트 배포 시 토큰 주소 지정
*/