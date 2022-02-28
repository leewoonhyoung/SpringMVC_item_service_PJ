package hello.itemservice.web.argumentResolver;

import hello.itemservice.domain.member.Member;
import hello.itemservice.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");


        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class); // login 에노테이션이 붙어 있는가?
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());// Member 클래스가 들어온다.

        return hasLoginAnnotation && hasMemberType;

    }

    @Override // 위의 supportsParameter 가 true면 실행된다.
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
         log.info("resolveArguments 실행");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if(session == null){
            return null;
        }
        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
