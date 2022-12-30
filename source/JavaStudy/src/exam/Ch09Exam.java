package exam;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 챕터9 문제
 *  
 * @author : pej 
 * @date : 2022.12.30 
 */
public class Ch09Exam {
    private static Logger logger = LogManager.getLogger(Ch09Exam.class);
    
    /**
     * 주어진 바이트 배열을 문자열로 변환
     *
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void exam06() {
        byte[] byteArr = { 73,32,108,111,118,101,32,121,111,117 };
        String str = new String(byteArr, StandardCharsets.UTF_8);
        logger.debug("byte배열 -> 문자열 : {}", str);
    }
    
    /**
     * 문자열 "모든 프로그램은 자바 언어로 개발될 수 있다."에서 
     * "자바" 문자열이 포함되어 있는지 확인하고 "자바"를 "Java"로 대치한 새로운 문자열을 만들기
     * 
     * @author : pej 
     * @date : 2022.12.30
     */
    public static void exam07() {
        String str = "모든 프로그램은 자바 언어로 개발될 수 있다.";
        
        int index = str.indexOf("자바");
        if (index == -1) {
            logger.debug("{} 문자열 포함 안됨", "자바");
        } else {
            str.replace("자바", "Java");
            logger.debug("문자열 변경 : {}", str);
        }
    }
    /**
     * 결과값이 true와 false가 나오는 이유에 대해 설명하시오.
     * 
     * @author : pej 
     * @date : 2022.12.25 
     */
    public static void exam08() {
        Integer intObj1 = 100; // => Integer intObj1 = new Integer(100);와 같은 의미
        Integer intObj2 = 100;
        Integer intObj5 = new Integer(100);
        Integer intObj3 = 300;
        Integer intObj4 = 400;
        
        logger.debug("intObj1.hashCode : {} ", intObj1.hashCode());
        logger.debug("intObj2.hashCode : {} ", intObj2.hashCode());
        logger.debug("intObj5.hashCode : {} ", intObj5.hashCode());
        logger.debug("intObj3.hashCode : {} ", intObj3.hashCode());
        logger.debug("intObj4.hashCode : {} ", intObj4.hashCode());
        
        logger.debug("intObj1.intValue() == intObj2.intValue() : {} ", intObj1.intValue() == intObj2.intValue());
        logger.debug("intObj1.equals(intObj2) : {} ", intObj1.equals(intObj2));
        
        /**
         * 해시코드가 동일하기 때문에 '=='으로 비교시 true가 나오는 것
         * Q. 래퍼클래스는 인스턴스 생성시 새로운 주소값으로 참조하지 않는 것인가?
         */
        
        logger.debug("intObj1 == intObj2 : {} ", intObj1 == intObj2); // => true
        logger.debug("intObj3 == intOb4 : {}", intObj3 == intObj4); // => false
    }
    
    public static void main(String[] args) {

    }
}
