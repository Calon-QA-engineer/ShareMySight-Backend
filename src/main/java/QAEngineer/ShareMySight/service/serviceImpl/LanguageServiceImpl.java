package QAEngineer.ShareMySight.service.serviceImpl;

import QAEngineer.ShareMySight.entity.Language;
import QAEngineer.ShareMySight.exception.CustomException;
import QAEngineer.ShareMySight.model.LanguageDTO;
import QAEngineer.ShareMySight.model.request.LanguageRequest;
import QAEngineer.ShareMySight.repository.LanguageRepository;
import QAEngineer.ShareMySight.service.serviceInteface.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {
  private final LanguageRepository languageRepository;

  @Override
  public List<LanguageDTO> findAll() {
    List<Language> languages = languageRepository.findAll();
    return languages.stream()
            .map(language -> new LanguageDTO(language.getId(), language.getLanguageName()))
            .collect(Collectors.toList());
  }

  @Override
  public void add(LanguageRequest request) {
    Optional<Language> languageName = languageRepository.findByLanguageName(request.getLanguageName());
    
    if (languageName.isPresent()) {
      throw new CustomException("Language already exist", HttpStatus.BAD_REQUEST);
    }
    
    Language language = Language.builder()
      .languageName(request.getLanguageName())
      .statusRecord('A')
      .createdAt(new Date())
      .updatedAt(new Date())
      .build();

    languageRepository.save(language);
  }
}
