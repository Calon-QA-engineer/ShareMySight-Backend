package QAEngineer.ShareMySight.repository;

import QAEngineer.ShareMySight.entity.GlobalChat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<GlobalChat, String> {
}
