package QAEngineer.ShareMySight.service.serviceInteface;

import QAEngineer.ShareMySight.model.request.LanguageRequest;
import org.springframework.stereotype.Service;

@Service
public interface LanguageService {
  void add(LanguageRequest request);
}
