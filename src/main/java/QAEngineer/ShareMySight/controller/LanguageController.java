package QAEngineer.ShareMySight.controller;

import QAEngineer.ShareMySight.model.request.LanguageRequest;
import QAEngineer.ShareMySight.model.response.AuthenticationResponse;
import QAEngineer.ShareMySight.model.response.StandardResponse;
import QAEngineer.ShareMySight.service.serviceInteface.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "language")
public class LanguageController {
  private final LanguageService languageService;
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
