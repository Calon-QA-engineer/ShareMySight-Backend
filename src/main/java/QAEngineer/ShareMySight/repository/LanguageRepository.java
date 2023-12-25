package QAEngineer.ShareMySight.repository;

import QAEngineer.ShareMySight.entity.Language;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends MongoRepository<Language, Object> {
}
