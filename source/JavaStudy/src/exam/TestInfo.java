package exam;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(CLASS)
@Target({ TYPE_USE })
public @interface TestInfo {
    int count() default 1;
    String[] value() default "aaa";
}
