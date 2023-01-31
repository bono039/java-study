package ch12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch12.LottoAnnotation.TestType;

/**
 * Annotation 학습
 *  
 * @author : pej 
 * @date : 2023.01.23
 */
@LottoAnnotation(target = "TYPE_USE")
@LottoAnnotation(target = "TYPE_USE")
public class TestAnnotation {
    private static Logger logger = LogManager.getLogger(TestAnnotation.class);
    
    @LottoAnnotation(author="test1", order = 2, testType = TestType.MORNING, target = "TYPE_USE")
    int num = 0;
    
    @LottoAnnotation(target = "TYPE_USE")
    public String test() {
        return "test";
    }
    
    // TYPE_PARAMETER : 타입 매개변수(제네릭)에서 사용 할 수 있음
    // TYPE_USE : 모든 타입에서 사용 할 수 있음(클래스, 메소드, 타입 매개변수, 매개변수, 예외 등등)
    public static class Lotto<@LottoAnnotation(target = "TYPE_USE") T> {
        // <T> void : 타입 매개변수
        // print(T t) : 그냥 타입
        // public static <@LottoAnnotation(target = "TYPE_PARAMETER") T> void print(@LottoAnnotation(target = "TYPE_PARAMETER") T t) { }
        
        public static <@LottoAnnotation(target = "TYPE_USE") T> void print(@LottoAnnotation(target = "TYPE_USE") T t) {}
    }
    
    @SuppressWarnings("unused")
    public static void main(@LottoAnnotation(target = "TYPE_USE") String[] args) throws @LottoAnnotation(target = "TYPE_USE") RuntimeException {
        List<@LottoAnnotation(target = "TYPE_USE") String> list = new ArrayList<>();
        
        // 어노테이션 읽어오는 방법(Retention가 RUNTIME이어야 읽어올 수 있음)
        // 방법1)
        LottoAnnotation[] annotationType = TestAnnotation.class.getAnnotationsByType(LottoAnnotation.class);
        Arrays.stream(annotationType).forEach(e -> {
            logger.debug(e.target());
        });
        
        // 방법2)
        LottoContainer container = TestAnnotation.class.getAnnotation(LottoContainer.class);
        Arrays.stream(container.value()).forEach(e -> {
            logger.debug(e.target());
        });
    }
}
