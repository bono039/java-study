package ch10;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
  * Date, Calendar 클래스 학습
  *  
  * @author : pej 
  * @date : 2022.12.30 
  */
public class TestDate {
    private static Logger logger = LogManager.getLogger(TestDate.class);
    
    public static void main(String[] args) {
        // 현재 날짜, 시간 가져옴
        Date now = new Date();
        logger.debug("## now : {} / now.toString() : {}", now, now.toString());
        
        // 24시간 형식
        SimpleDateFormat dateForamt = new SimpleDateFormat("yyyy년 MM월 dd일  HH시 mm분 E요일");
        logger.debug("## 날짜 형식 : {}", dateForamt.format(now));
        
        // Calendar
        // getInstance() : 현재 시스템의 날짜와 시간 정보를 알려줌
        Calendar calNow = Calendar.getInstance();
        logger.debug("## Calendar now : {}", calNow.toString());
//        calNow.setTime(now);
        
        SimpleDateFormat dateForamt2 = new SimpleDateFormat("yyyy년 MM월 dd일  HH시 mm분 E요일");
        logger.debug("## Calendar 날짜 형식 : {}", dateForamt2.format(calNow.getTime()));
        
    }
}
