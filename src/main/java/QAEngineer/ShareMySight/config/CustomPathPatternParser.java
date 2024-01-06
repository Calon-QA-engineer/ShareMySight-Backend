package QAEngineer.ShareMySight.config;

import org.springframework.http.server.PathContainer;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

public class CustomPathPatternParser extends PathPatternParser {
  private final PathMatcher pathMatcher = new AntPathMatcher();
  
  @Override
  public PathPattern parse(@NonNull String pattern) {
    // Tambahkan logika pengecualian untuk pattern tertentu
    if (isExcluded(pattern)) {
      return null;
    }
    
    return super.parse(pattern);
  }
  
  private boolean isExcluded(String pattern) {
    // Tambahkan logika pengecualian berdasarkan pola tertentu
    // Contoh: pattern.equals("/socket.io") atau lainnya
    return pattern.equals("/socket.io");
  }
}
