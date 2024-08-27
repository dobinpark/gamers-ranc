package jp.games_ranc.repository;

import jp.games_ranc.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findById(Long id);
    User findByNickName(String nickName);
    User deleteById(Long id);
}
