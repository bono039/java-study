package ch09;

import java.math.BigDecimal;
import java.util.StringJoiner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * java.lang 패키지 관련 학습
 *  
 * @author : pej 
 * @date : 2022.12.30 
 */
public class TestLang {
    private static Logger logger = LogManager.getLogger(TestLang.class);
    
    /**
     * Object 테스트
     *
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void test1() {
        // 참조변수는 주소값을 가리킴
        // 인스턴스가 생성될때마다 참조변수가 가리키는 주소값은 달라짐
        // => 서로 다른 두 인스턴스를 비교하면 항상 false로 나옴
        Object obj1 = new Object(); // Ox1234
        Object obj2 = new Object(); // Ox5678
        
        logger.debug("obj1 hashCode : " + obj1.hashCode());
        logger.debug("obj2 hashCode : " + obj2.hashCode());
        
        if (obj1.equals(obj2)) {
            logger.debug("obj1 == obj2");
        } else {
            logger.debug("obj1 != obj2");
        }
        
        // 강제로 같은 인스턴스의 주소값을 저장
        obj2 = obj1;
        
        logger.debug("## 변경후 ##");
        if (obj1.equals(obj2)) {
            logger.debug("obj1 == obj2");
        } else {
            logger.debug("obj1 != obj2");
        }
        
        // 인스턴스의 주소값을 변경하자 해시코드도 변경됨
        logger.debug("obj1 hashCode : " + obj1.hashCode());
        logger.debug("obj2 hashCode : " + obj2.hashCode());
    }
    
    /**
     * JDK 1.8에서 추가된 String 관련 테스트
     *
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void test2() {
        String str1 = "1";
        String str2 = "2";
        
        logger.debug(str1.hashCode());
        logger.debug(str2.hashCode());
        
        
        // 2. String 클래스
        
        // String.join() 사용한 문자열 결합
        String joinTest = "join";
        logger.debug(String.join(" ", joinTest, "사용", "1", "2"));
        
        // StringJoiner 사용한 문자열 결합
        StringJoiner st = new StringJoiner(" ");
        st.add("StringJoiner");
        st.add("사용");
        logger.debug(st.toString());
        
        // String.format() 사용
        String strFormat = String.format("%d + %d = %d", 1, 2, 1+2);
        logger.debug(strFormat);
    }
    
    /**
     * 래퍼 클래스 테스트
     *
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void test3() {
        // 래퍼 클래스
        Integer i = new Integer("0");
        logger.debug("### hashCode : " + i.hashCode());
        i = 1;
        logger.debug("### hashCode : " + i.hashCode());
        
        Integer intObj = null;
//        int testNum = intObj;
        int testNum = intObj == null ? 0 : intObj;
//        logger.debug("### intObj : " + intObj.intValue()); // null 에러 발생
        logger.debug("### testNum : " + testNum);
    }
    
    /**
     * BigDecimal 테스트
     *
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void test4() {
        BigDecimal d1 = new BigDecimal(0.1);
        logger.debug(d1);
        
        BigDecimal d2 = new BigDecimal("0.1");
        logger.debug(d2);
    }

    public static void main(String[] args) {
    }
}
