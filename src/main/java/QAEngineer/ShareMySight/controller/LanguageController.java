package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.entity.Language;
import QAEngineer.ShareMySight.model.LanguageDTO;
import QAEngineer.ShareMySight.model.request.LanguageRequest;
import QAEngineer.ShareMySight.model.response.StandardResponse;
import QAEngineer.ShareMySight.service.serviceInteface.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "language")
public class LanguageController {
  private final LanguageService languageService;

  @GetMapping
  public ResponseEntity<StandardResponse<List<LanguageDTO>>> findAll() {
    List<LanguageDTO> languages = languageService.findAll();
    StandardResponse<List<LanguageDTO>> response = StandardResponse.<List<LanguageDTO>>builder()
            .status(true)
            .code(201)
            .message("Language added")
            .data(languages)
            .build();

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<StandardResponse<Object>> add(
    @RequestBody @Valid LanguageRequest request
    ) {
    languageService.add(request);
    StandardResponse<Object> response = StandardResponse.<Object>builder()
      .status(true)
      .code(201)
      .message("Language added")
      .data(null)
      .build();

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}