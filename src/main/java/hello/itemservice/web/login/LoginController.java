package hello.itemservice.web.login;

import hello.itemservice.domain.login.LoginService;
import hello.itemservice.domain.member.Member;
import hello.itemservice.web.SessionConst;
import hello.itemservice.web.argumentResolver.Login;
import hello.itemservice.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.validation.Valid;
import java.util.Enumeration;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

//    첫번째 코드
//
//    private final LoginService loginService;
//
//    @GetMapping("/login")
//    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm){
//        return "login/loginForm";
//    }
//
//
//    //@Valid는 loginForm 의 @NotNull과 같은 한정 어노테이션을 사용하겠다라는 의미!
//    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
//        log.info("login? {}", loginMember);
//
//        if(loginMember == null){
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
//            return "login/loginForm";
//        }
//
//    }

//    //두번째 코드
//
//    private final LoginService loginService;
//
//    @GetMapping("/login")
//    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm){
//        return "login/loginForm";
//    }
//
//    //version 1 에 HttpServletResponse , response.addCookie가 추가되었다.
//    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response){
//        if(bindingResult.hasErrors()){
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
//        log.info("login? {}", loginMember);
//
//        if (loginMember == null){
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
//            return "login/loginForm";
//        }
//
//        // 로그인 성공 처리
//        //쿠키에 시간 정보를 주지 않으면 세션 쿠키 종료
//        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
//        response.addCookie(idCookie);
//
//        return "redirect:/";
//    }
//
//    @PostMapping("/logout")
//    public String logout(HttpServletResponse response){
//        expireCookie(response, "memberId");
//        return "redirect:/";
//    }
//
//    private void expireCookie(HttpServletResponse response, String cookieName){
//        Cookie cookie = new Cookie(cookieName, null);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//    }


//    //세번째 코드
//    private final LoginService loginService;
//    //SessionManger 주입이 두번째 코드에서 추가되었다.
//    private final SessionManager sessionManager;
//
//    @GetMapping("/login")
//    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm){
//        return "login/loginForm";
//    }
//
//    @PostMapping("/login")
//    public String loginV2(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response){
//        if(bindingResult.hasErrors()){
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
//        log.info("login? {}", loginMember);
//
//        if (loginMember == null){
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
//            return "login/loginForm";
//        }
//
//        //로그인 성공 처리
//        //세션 관리자를 통해 세션을 생성
//        sessionManager.createSession(loginMember, response);
//        return "redirect:/";
//    }

//    //네번째 코드
//
//    private final LoginService loginService;
//
//    @GetMapping("/login")
//    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm){
//        return "login/loginForm";
//    }
//
//
//    @PostMapping("/login")
//    public String loginV3(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
//        if (bindingResult.hasErrors()) {
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
//        log.info("login? {}", loginMember);
//
//        if (loginMember == null) {
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
//            return "login/loginForm";
//        }
//
//        //로그인 성공처리
//        //세션 존재시 해당 세션을 반환
//        HttpSession session = request.getSession();
//
//        //세션에 로그인 회원의 정보를 보관한다.
//        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
//        return "redirect:/";
//    }
//
//    @PostMapping("/logout")
//    public String logoutV3(HttpServletRequest request){
//
//        //세션을 삭제한다.
//        HttpSession session = request.getSession(false);
//        if (session != null){
//            session.invalidate();
//        }
//        return "redirect:/";
//    }

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String loginV4(
            @Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
            @RequestParam(defaultValue = "/") String redirectURL,
            HttpServletRequest httpServletRequest){

        if (bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        log.info("login? {}" , loginMember);

        if(loginMember == null){
            bindingResult.reject("loginFail",
                    "아이디 또는 비밀번호가 맞지 않습니다.");
            return  "login/loginForm";
        }

        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = httpServletRequest.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        //redirectURL 적용
        return "redirect:" + redirectURL;
    }



}









    /**
    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm){
        return "login/loginForm";
    }

//    @PostMapping("/login")
    public String loginForm(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response){
        if (bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        log.info("login? {}", loginMember);

        if(loginMember == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공 처리 TODO

        //쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두 종료)
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);
        return "redirect:/";

    }
//    @PostMapping("/login")
    public String loginV2(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response){
        if (bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}" , loginMember);

        if(loginMember == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //세션 관리자를 통해 세션을 생성하고 회원 데이터를 보관한다.
        sessionManager.createSession(loginMember, response);
        return "redirect:/";

    }

   // @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);

        if(loginMember == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        //로그인 성공처리

        //세션이 있으면 있는 세션을 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();

        //세션에 로그인 정보를 보관하는 방법이다. 하나의 세션의 여러 값을 저장할 수 있다.
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";

    }

    @PostMapping("/login")
    public String loginV4(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                          @RequestParam(defaultValue = "/") String redirectURL,
                          HttpServletRequest request){
        if (bindingResult.hasErrors()) {
        return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login?{}", loginMember);

        if(loginMember == null){
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }
        //세션이 있으면 세션을 반환하고 없으면 신규 세션을 생성한다.
        HttpSession session = request.getSession();

        //세션에 로그인 회원 정보를 보관한다.
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        //redirectURL 사용
        return "redirect:" + redirectURL;
    }



//    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        expireCookie(response, "memberId");
        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request){
        sessionManager.expire(request);
        return "redirect:/";
    }
    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request){
        HttpSession session = request.getSession(false); // 세션에 null을 반환하게 만든다.
        if (session != null){
            session.invalidate();
        }
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String memberId) {
        Cookie cookie = new Cookie(memberId, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
**/


