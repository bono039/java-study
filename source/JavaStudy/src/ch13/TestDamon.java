package ch13;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Damon Thread 학습
 *  
 * @author : pej 
 * @date : 2023.02.04
 */
public class TestDamon implements Runnable {
    private static Logger logger = LogManager.getLogger(TestDamon.class);
    
    private static boolean isExit = false;
    
    public void exit() {
        logger.debug("데몬 스레드 종료");
    }
    
    @Override
    public void run() {
        while (true) {
            logger.debug("데몬 스레드 실행중 ");
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                logger.error(e);
            }
            
            if (isExit) {
                exit();
            }
        }
    }
    
    public static void main(String[] args) {
        /**
         * 데몬 스레드 :
         *  - 일반 스레드를 보조해주는 역할로 일반 스레드가 종료되면 데몬 스레드도 종료됨
         *  - 실행 후 대기하다가 특정 조건에 해당되면 작업을 수행함
         *  - 데몬 스레드가 생성한 스레드는 자동적으로 데몬 스레드가 됨
         *  - 예) 가비지 컬렉터, 파일 자동저장 등등
         */
        Thread t = new Thread(new TestDamon());
        t.setDaemon(true);
        t.start();
        
        for (int i=0; i<10; i++) {
            logger.debug(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e);
            }
            
            if (i == 5) {
                isExit = true;
            }
        }
    }
}
