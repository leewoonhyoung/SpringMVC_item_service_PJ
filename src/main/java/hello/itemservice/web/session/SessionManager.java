package hello.itemservice.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Arrays;

@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";

    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
     * 세션 생성
     */

    public void createSession(Object value, HttpServletResponse response){

        //서버는 세션 id를 생성하는데 uuid를 사용해서 만든다.
        String sessionId = UUID.randomUUID().toString();
        //서버는 sessionStore에 uuid 세션 id와 실제값(value)을 넣는다
        sessionStore.put(sessionId, value);

        //서버는 웹브라우저 클라이언트에게 보낼 MysessionCookie를 만든다.
        //서버는 mySessionCookie에 uuid세션id와 그 값을 key로 사용할수 있는 세션저장소 index값을 넣는다.
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);

        //서버는 쿠키저장소에게 uuid세션id와 세션저장소값을 보낸다.
        response.addCookie(mySessionCookie);
    }

    /**
     * Session 조회
     */

    public Object getSession(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME); // 세션 쿠키 이름과
        if(sessionCookie == null){
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    /**
     * 세션 만료
     */
    //여기의 request에는
    // client에 저장되있던 sessionCookie가
    // 서버로 날라와 key값을 매칭해보고 삭제할지 결정한다.
    public void expire(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null){
            sessionStore.remove(sessionCookie.getValue());
        }
    }


    private Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null){
            return null;
        }

        //request.getcookies() 를 하면 배열로 들어온다.
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);

    }

}
