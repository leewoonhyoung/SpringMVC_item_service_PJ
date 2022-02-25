package hello.itemservice.web.session;

import hello.itemservice.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.websocket.Session;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

        SessionManager sessionManager = new SessionManager();

        @Test
    public void sessionTest(){

            //세션 생성
            MockHttpServletResponse response = new MockHttpServletResponse();
            Member member = new Member();
            sessionManager.createSession(member, response);


            //요청에 응답 쿠키를 저장한다.
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setCookies(response.getCookies());

            // 세션을 조회한다.
            Object result = sessionManager.getSession(request);
            Assertions.assertThat(result).isEqualTo(member);

            //세션을 만료한다.
            sessionManager.expire(request);
            Object expired = sessionManager.getSession(request);
            Assertions.assertThat(expired).isNull();
        }

}