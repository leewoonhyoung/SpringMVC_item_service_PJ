package hello.itemservice.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override // preHandel은 호출전에 발생
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, uuid);

        //@RequestMapping : HandlerMethod
        // 정적 리소스 : ResourceHttpRequestHandler로 처리한다.

        if (handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            //호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
        }
        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true;
    }

    @Override // 정상 실행시 ModelAndView를 반환
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       log.info("postHandle [{}]", modelAndView);
    }


    @Override // 실패 성공에 상관 없이 항상 반환.
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        // request로 부터 uri 정보를 받아온다.
        String requestURI = request.getRequestURI();

        // requeste로 부터 log_id를 String으로 받아오도록 한다.
        String logId = (String) request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}]", logId, requestURI);
        if (ex != null){
            log.error("afterCompletion Error !", ex);
        }
    }
}
