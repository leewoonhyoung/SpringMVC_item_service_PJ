package hello.itemservice.web.filter;

import hello.itemservice.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    //로그인 되지 않은 사용자는 상품 관리 뿐만 아니라 미래에 개발페이지에도 접근하지 못하도록 하는 로직.


    //whiteList 는 인증 체크 필터를 통과하지 못하더라도 접속을 허용한다.
    private  static final String[] whiteList = {"/", "members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //HttpServletRequest 를 다운 캐스팅, HttpServletResponse를 다운 캐스팅후 , httpServletRequesst로 부터 URI를 가져온다.
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작 {}", requestURI);

            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);

                //세션이 존재하지 않을때 false를 통해서 추가로 생성하지 않게끔 한다. httpSerssion의 문법
                HttpSession session = httpRequest.getSession(false);

                //만약 세션에 정보가 없거나. session의 Attribute의 Session상수가 없다면 redirect를 해서 출입을 막는다.
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 {}", requestURI);
                    //로그인으로 redirect
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    //여기의 return은 redirect후에 아무런 기능을 수행하지 않도록 마치는 중요한 역할을 한다.
                    return;
                }
            }
            // chain.doFilter를 활용해서 다음 filter가 존재하면 실행하고 실행되지 않는다면 마쳐라.
            chain.doFilter(request, response);
        } catch(Exception e){
            throw e; // 예외 로깅 가능 but 톰캣까지 예외를 보내주어야 한다.
        } finally{
            log.info("인증 체크 필터 종료{}", requestURI);
        }

    }
    /**
     * 화이트 리스트인 경우 인증 체크 X
     */

    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
