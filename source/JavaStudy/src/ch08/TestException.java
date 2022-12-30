package ch08;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * try-catch 학습
 *  
 * @author : pej 
 * @date : 2022.12.30 
 */
public class TestException {
    private static Logger logger = LogManager.getLogger(TestException.class);
    
    public static void main(String args[]) {
      try {
          test();
      } catch (Exception e) {
          logger.error("## main -catch ##");
      }
    }
    
    public static void test() throws Exception {
      try {
          // 예외 강제로 발생
          throw new Exception();
      } catch (Exception e) {
          logger.error("## catch ##");
          // 예외 다시 던지기
          throw e;
      }
    }
    
//    public static void main(String args[]) {
//        try {
//            throw new NullPointerException();
//        } catch (NullPointerException e) {
//            logger.error("## NullPointerException ##");
//            e.printStackTrace();
//            logger.debug("## getMessage => " + e.getMessage());
//        } catch (IndexOutOfBoundsException e) {
//            logger.error("## IndexOutOfBoundsException ##");
//        }
//        
//        try {
//            throw new NullPointerException();
//        } catch (NullPointerException | IndexOutOfBoundsException e) {
//            // 멀티 catch문을 이용하면 이렇게 Exception마다 개별 처리 할 수도 있음
//            if ( e instanceof NullPointerException ) {
//                logger.error("## NullPointerException ##");
//            } else if (e instanceof IndexOutOfBoundsException) {
//                logger.error("## IndexOutOfBoundsException ##");
//            }
//        }
//        
//        try (FileInputStream fis = new FileInputStream("")) {
//            
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//    }
}
