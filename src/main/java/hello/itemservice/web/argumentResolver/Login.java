package hello.itemservice.web.argumentResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 파라미터 형식으로 동작하기
@Retention(RetentionPolicy.RUNTIME) // 남아 있는 시간
public @interface Login {
}
