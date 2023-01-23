package ch10;

import java.text.ChoiceFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Format 관련 클래스 학습
 *  
 * @author : pej 
 * @date : 2022.12.30 
 */
public class TestFormat {
    private static Logger logger = LogManager.getLogger(TestFormat.class);
    
    /**
     * DecimalFormat 테스트
     * 
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void decimalFormat() {
        double number = 1_000_000.00;
        DecimalFormat df = new DecimalFormat("#,###");
        logger.debug("format(#,###) : {}", df.format(number));
        
        try {
            Number num = df.parse("1,000,000");
            logger.debug("formate -> 문자열 : {}", num);
        } catch (ParseException e) {
            logger.error("error : {}", e.getMessage());
        }
    }
    
    /**
     * SimpleDateFormat 테스트
     * 
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void simpleDateFormat() {
        Date today = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        logger.debug("format(yyyy-MM-dd) : {}", sf.format(today));
        
        try {
            Date today2 = sf.parse("2022-12-31");
            logger.debug("formate -> 문자열 : {}", today2);
        } catch (ParseException e) {
            logger.error("error : {}", e.getMessage());
        }
    }
    
    /**
     * SimpleDateFormat 테스트
     * 
     * @author : pej 
     * @date : 2022.12.30 
     */
    @SuppressWarnings("unused")
    public static void choiceFormat() {
        int[] scores = { 41, 88, 95, 70, 60, 100 }; 
        
        // 방법1) 패턴 지정
        /**
         * #, ≤(\u2264) : 경계 포함
         * < : 경계 불포함
         */
//        String newPattern = "60≤D|70≤C|80≤B|90≤A";
        String newPattern = "60<D|70<C|80<B|90<A";
        ChoiceFormat cf = new ChoiceFormat(newPattern);
        
        // 방법2) 범위, 값 따로 지정
        double[] limitArr = { 60, 70, 80, 90 }; // double 타입만 가능
        String[] valueArr = { "D", "C", "B", "A" };
        ChoiceFormat cf2 = new ChoiceFormat(limitArr, valueArr);
        
        for (int i : scores) {
            logger.debug("점수 및 등급 : {} / {}", i, cf.format(i));
        }
    }
    
    /**
     * MessageFormat 테스트
     * 
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void messageFormat() {
        String pattern = "이름 : {0} \n 전화번호 : {1} \n";
        Object[] arguments = { "pej", "010-1234-5678" };
        logger.debug("format : {}", MessageFormat.format(pattern, arguments));
        
        // 쿼리문을 일괄적으로 만들어야 할때 사용하면 좋을듯
        String insertPattern = "INSERT INTO TEST (ID, NAME, PHON, ADDR) VALUES(''{0}'', ''{1}'', ''{2}'', ''{3}'');";
        Object[][] data = {
           {"1", "TEST1", "01011111111", "서울특별시 영등포구"},
           {"2", "TEST2", "01011111111", "서울특별시 영등포구"},
           {"3", "TEST3", "01011111111", "서울특별시 영등포구"},
           {"4", "TEST4", "01011111111", "서울특별시 영등포구"},
           {"5", "TEST5", "01011111111", "서울특별시 영등포구"}
        };
        
        for (int i=0; i<data.length; i++) {
            logger.debug(MessageFormat.format(insertPattern, data[i]));
        }
    }
    
    public static void main(String[] args) {
        decimalFormat();
//        simpleDateFormat();
//        choiceFormat();
//        messageFormat();
    }

}
