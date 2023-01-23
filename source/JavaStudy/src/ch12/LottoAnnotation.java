package ch12;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(CLASS)
@Target({ FIELD, TYPE, TYPE_USE })
public @interface LottoAnnotation {
    String author() default "pej";
    int order() default 1;
    String[] testArr(); // => 기본값이 없으므로 반드시 있어야함
    TestType testType() default TestType.EVENING;
    
    public enum TestType {
        MORNING, AFTERNNON, EVENING;
    }
}
