package QAEngineer.ShareMySight.repository;

import QAEngineer.ShareMySight.entity.GlobalChat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<GlobalChat, String> {
}
