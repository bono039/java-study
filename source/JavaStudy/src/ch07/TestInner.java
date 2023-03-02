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
    
    public String name = "Outer Class";
    public static String staticName = "Outer";
    private int privateNumber = 100;
    
    void getMembers() {
        logger.debug("====Outer Class====");
        logger.debug("name : {}", name);
        logger.debug("staticName : {}", staticName);
        logger.debug("privateNumber : {}", privateNumber);
    }
    
    // 인스턴스 클래스
    class InnerClass {
        String name = "innerClass";
        /**
         *  에러 발생
         *  Static 클래스인 경우만 static 변수를 가질 수 있음
         */
        // static String staticName = "inner";
        String staticName = "inner";
        
        void getOuterMembers() {
            logger.debug("====Inner Class====");
            logger.debug("name : {}", name);
            logger.debug("staticName : {}", staticName);
            logger.debug("privateNumber : {}", privateNumber);
        }
        
        void testShadow(String name, String staticName) {
            logger.debug("====Inner Class :: testShadow====");
            // 매개변수
            logger.debug("name : {} / staticName : {}", name, staticName);
            // InnerClass 클래스의 멤버변수
            logger.debug("this.name : {}", this.name);
            logger.debug("this.staticName : {}", this.staticName);
            // OuterClass 클래스의 멤버변수
            logger.debug("TestInner.this.name : {}", TestInner.this.name);
            logger.debug("TestInner.staticName : {}", TestInner.staticName);
        }
    }
    
    // static 클래스
    static class StaticInnerClass {
        String name = "staticInnerClass";
        String staticName = "staticInner";
        
        void getOuterMembers() {
            logger.debug("====Static Inner Class====");
            /**
             * static 클래스이기 때문에 name 필드에 바로 접근 할 수 없음
             */
            logger.debug("name : {}", new TestInner().name);
            logger.debug("staticName : {}", staticName);
            logger.debug("privateNumber : {}", new TestInner().privateNumber);
        }
        
        void testShadow(String name, String staticName) {
            logger.debug("====Static Inner Class :: testShadow====");
            // 매개변수
            logger.debug("name : {} / staticName : {}", name, staticName);
            // Static Inner Class 클래스의 멤버변수
            logger.debug("this.name : {}", this.name);
            logger.debug("this.staticName : {}", this.staticName);
            // OuterClass 클래스의 멤버변수
            logger.debug("TestInner.this.name : {}", new TestInner().name);
            logger.debug("TestInner.staticName : {}", TestInner.staticName);
        }
    }
    
    void testLocalClass() {
        // 지역 클래스
        class LocalClass {
            String name = "localClass";
            String staticName = "localClass";
            
            void testShadow(String name, String staticName) {
                logger.debug("====Local Class :: testShadow====");
                // 매개변수
                logger.debug("name : {} / staticName : {}", name, staticName);
                // Local Class 클래스의 멤버변수
                logger.debug("this.name : {}", this.name);
                logger.debug("this.staticName : {}", this.staticName);
                // OuterClass 클래스의 멤버변수
                logger.debug("TestInner.this.name : {}", new TestInner().name);
                logger.debug("TestInner.staticName : {}", TestInner.staticName);
            }
        }
        
        LocalClass local = new LocalClass();
        local.testShadow("local", "local");
    }
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        TestInner out = new TestInner();
        // out.getMembers();
        
        TestInner.InnerClass inner = out.new InnerClass();
        // inner.getOuterMembers();

        StaticInnerClass staticInner = new StaticInnerClass();
        // staticInner.getOuterMembers();

        inner.testShadow("inner", "inner");
        staticInner.testShadow("staticInner", "staticInner");
        
        out.testLocalClass();
        
        // 익명 클래스
        Object obj = new Object() {
            void testShadow(String name, String staticName) {
                
                logger.debug("====Anoymous Class :: testShadow====");
                // 매개변수
                logger.debug("name : {} / staticName : {}", name, staticName);
                
                // OuterClass 클래스의 멤버변수
                logger.debug("TestInner.this.name : {}", new TestInner().name);
                logger.debug("TestInner.staticName : {}", TestInner.staticName);
            }
        };
    }
}
