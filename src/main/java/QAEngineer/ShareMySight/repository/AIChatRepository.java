package QAEngineer.ShareMySight.repository;

import QAEngineer.ShareMySight.entity.AIChat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AIChatRepository extends MongoRepository<AIChat, String> {
    @Query(value = "{'user.id': ?0}", fields = "{'user': 0, 'status_record': 0, '_class': 0}")
    List<AIChat> findAllByUserIdExcludingFields(String userId);
}
