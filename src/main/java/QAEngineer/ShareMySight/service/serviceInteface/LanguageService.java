package QAEngineer.ShareMySight.service.serviceInteface;

import QAEngineer.ShareMySight.entity.Language;
import QAEngineer.ShareMySight.model.LanguageDTO;
import QAEngineer.ShareMySight.model.request.LanguageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LanguageService {
  List<LanguageDTO> findAll();
  void add(LanguageRequest request);
}
