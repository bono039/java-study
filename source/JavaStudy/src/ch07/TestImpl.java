package ch07;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Interface 구현체 학습
 *  
 * @author : pej 
 * @date : 2022.12.30 
 */
public class TestImpl implements TestInterface {
    private static Logger logger = LogManager.getLogger(TestImpl.class);
    
    @Override
    public void print() {
      logger.debug("TestImpl - print()");
    }
    
    public static void main(String[] args) {
        TestImpl test = new TestImpl();
        test.printDefault();
        TestInterface.printStatic();
    }
}
