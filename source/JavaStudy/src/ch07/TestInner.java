package ch07;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Inner 클래스 학습
 *  
 * @author : pej 
 * @date : 2022.12.30 
 */
public class TestInner {
    private static Logger logger = LogManager.getLogger(TestInner.class);
    
    // static 클래스
    static class Inner2 {
        
    }
    // 인스턴스 클래스
    class Inner3 {
        
    }
    
    void test() {
        // 지역 클래스
        @SuppressWarnings("unused")
        class Inner4 {
            
        }
    }
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        logger.debug("## Inner Class");
        
        // 익명 클래스
        Object obj = new Object() {
            void test2() {
                logger.debug("## Anoymous Class");
            }
        };
    }
}
