package ch12;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE_PARAMETER, ElementType.TYPE_USE })
@Repeatable(LottoContainer.class)
public @interface LottoAnnotation {
     /**
      * @Retention : 적용 기간
      * @Target : 적용 대상
      * @Repeatable : 중복 허용
      * @Documented : javadoc으로 작성한 문서에 포함되도록 함
      */
    String author() default "pej";
    int order() default 1;
    String[] testArr() default {""};
    String target(); // => 기본값이 없으므로 반드시 있어야함
    TestType testType() default TestType.EVENING;
    
    public enum TestType {
        MORNING, AFTERNNON, EVENING;
    }
}
