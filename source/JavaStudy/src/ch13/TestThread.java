package ch13;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Thread 학습
 *  
 * @author : pej 
 * @date : 2023.02.04
 */
public class TestThread {
    private static Logger logger = LogManager.getLogger(TestThread.class);
    
    /**
     * 방법1) Thread 클래스 상속
     */
    static class Thread1 extends Thread {
        @Override
        public void run() {
//            logger.debug("ID : {} / Name : {}", getId(), getName());
//            logger.debug("Priority : {}", getPriority());
//            logger.debug("ThreadGroup : {}", getThreadGroup());
            
            for (int i=0; i<100; i++) {
                System.out.print("+");
            }
        }
    }
    
    /**
     * 방법2) Runnalbe 인터페이스 구현
     */
    static class Thread2 implements Runnable {
        @Override
        public void run() {
//            logger.debug("ID : {} / Name : {}", Thread.currentThread().getId(), Thread.currentThread().getName());
//            logger.debug("Priority : {}", Thread.currentThread().getPriority());
//            logger.debug("ThreadGroup : {}", Thread.currentThread().getThreadGroup());
            
            for (int i=0; i<100; i++) {
                System.out.print("-");
            }
        }
    }
    
    static class Thread3 extends Thread {
        @Override
        public void run() {
            try {
                error();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    static class Thread4 extends Thread {
        @Override
        public void run() {
//            logger.debug("Name : {} / ThreadGroup : {}", Thread.currentThread().getName(),Thread.currentThread().getThreadGroup());
//            logger.debug("ID : {} / Name : {} / Priority : {} / ThreadGroup : {}", getId(), getName(), getPriority(), getThreadGroup());
            for (int i=0; i<100; i++) {
                System.out.print("-");
            }
        }
    }
    
    static class Thread5 implements Runnable {
        @Override
        public void run() {
            logger.debug("ID : {} / Name : {} / Priority : {} / ThreadGroup : {}", Thread.currentThread().getId(), Thread.currentThread().getName(), Thread.currentThread().getPriority(), Thread.currentThread().getThreadGroup());
        }
    }
    
    static class Thread6 implements Runnable {
        @Override
        public void run() {
            
        }
    }
    
    public static void error() throws Exception{
        throw new Exception();
    }
    
    public static void test1() {
        // 방법1) Thread 클래스 상속
        Thread1 t1 = new Thread1();
        t1.setName("이것은 스레드 테스트1");
        t1.setPriority(1);
        t1.start();
        
        // 방법2) Runnalbe 인터페이스 구현
        // Runnable run = new Thread2();
        // Thread t2 = new Thread(run);
        // t2.start();
      
        // 방법2) Runnalbe 인터페이스 구현
        Thread t2 = new Thread(new Thread2());
        t2.setName("이것은 스레드 테스트2");
        t2.setPriority(9);
        t2.start();
        
        /**
         * start() : 멀티 실행 
         * run() : 단일 실행
         */
        // 이렇게 해도 실행됨
        // Thread2 t2 = new Thread2();
        // t2.run();
    }
    
    public static void test2() {
        Thread3 t3 = new Thread3();
        t3.start();
        t3.run();
    }
    
    public static void test3() {
        /**
         * 스레드 그룹 : 
         *  - 보안상의 이유로 도입되었으며 자신이 속한 스레드 그룹, 하위 스레드 그룹만 변경 할 수 있음
         *  - 모든 스레드는 그룹이 있어야하며 JVM에서 main, system 이라는 그룹을 만듦
         *  - 그룹을 지정하지 않으면 자동적으로 main 스레드 그룹으로 지정됨
         *  
         */
        ThreadGroup tGroup1 = new ThreadGroup("그룹1");
        tGroup1.setMaxPriority(4);
        
        ThreadGroup tGroup2 = new ThreadGroup(tGroup1, "서브그룹");
        tGroup2.setMaxPriority(3);
        
        /**
         * Q. 스레드 정보가 설정한 값으로 나오지 않음
         *  => Thread클래스에서 받은걸로 말로 Thread.currentThread() 이용하면 설정한 값으로 나옴
         */
        Thread t4 = new Thread(tGroup1, new Thread4(), "스레드 그룹 테스트1");
        t4.start();
        
        Thread t5 = new Thread(tGroup1, new Thread5(), "스레드 그룹 테스트2");
        t5.start();
        
        Thread t6 = new Thread(tGroup2, new Thread5(), "스레드 그룹 테스트3");
        t6.start();
        
        // 스레드 그룹 정보 출력
        // getAllStackTraces() : 실행되는 모든 스레드 정보를 가져옴
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        for (Thread item : map.keySet()) {
           System.out.println(item);
        }
    }
    
    public static void main(String[] args) {
        Thread1 t1 = new Thread1();
        t1.start();
        
        Thread4 t2 = new Thread4();
        t2.start();
    }
}