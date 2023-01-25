package ch12;

import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(CLASS)
@Target({ ElementType.FIELD, ElementType.TYPE_PARAMETER, ElementType.TYPE_USE })
public @interface LottoContainer {
    // LottoAnnotation 보다 적용 대상이 같거나 넒어야 함 => 감싸는 Container이니까
    
    /**
     * 감싸고 있는 어노테이션을 배열로 받아야 함
     * 안 하면 에러 발생 => The container annotation type @LottoContainer must declare a member value()
     */
    LottoAnnotation[] value();
}
