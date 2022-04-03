package hello.itemservice.web.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.LogRecord;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //httpServletRequest를 다운캐스팅받아 HttpServletRequest의 기능을 사용하게끔 한다.
        //HttpServletRequest의 requestURI기능을 사용하기 위해서이다!
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        //httpServletRequest를 통해 URI의 값을 가져온다.
        String requestURI = httpServletRequest.getRequestURI();

        //UUID를 생성하는 로직
        String uuid = UUID.randomUUID().toString();

        try{
            log.info("REQUEST [{}][{}]", uuid, requestURI);

            //핵심 doFilter문, 마치 자바의 ; 과 같은 역할.
            //다음 필터가 있으면 필터를 호출하고, 필터가 없으면 서블릿을 호출
            chain.doFilter(request, response);
        } catch (Exception e){
            throw e;
        } finally{
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }


    @Override
    public void destroy() {
        log.info("log filter doFilter");

    }




}
