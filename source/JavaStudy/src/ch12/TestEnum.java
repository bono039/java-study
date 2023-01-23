package ch12;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Enum 학습
 *  
 * @author : pej 
 * @date : 2023.01.23
 */
public class TestEnum {
    private static Logger logger = LogManager.getLogger(TestEnum.class);
    
    public static void main(String[] args) {
       DayEnum[] str = DayEnum.values();
       
       for (DayEnum item : str) {
           logger.debug("item() : {}", item);
       }
       
       logger.debug("DayEnum.EVENING == DayEnum.MORNING : {}", DayEnum.EVENING == DayEnum.MORNING);
       logger.debug("DayEnum.MORNING == DayEnum.MORNING : {}", DayEnum.MORNING == DayEnum.MORNING);
    }
}
