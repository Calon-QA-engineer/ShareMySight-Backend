package QAEngineer.ShareMySight.logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Log request details
        long startTime = Instant.now().toEpochMilli();
        log.info("Request URL=" + request.getRequestURL().toString() +
                ":: Request Method=" + request.getMethod() +
                ":: Start Time=" + Instant.now());
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        // Log response details
        log.info("Request URL={}:: Request Method={}:: Response with status={}:: Time Taken={}ms", request.getRequestURL().toString(), request.getMethod(), response.getStatus(), (Instant.now().toEpochMilli() - startTime));
    }
}
