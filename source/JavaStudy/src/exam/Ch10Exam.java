package exam;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 챕터10 연습문제
 *  
 * @author : pej 
 * @date : 2023.01.15 
 */
public class Ch10Exam {
    private static Logger logger = LogManager.getLogger(Ch10Exam.class);
    
    /**
     * <pre>
     * [10-1] Calendar클래스와 SimpleDateFormat클래스를 이용해서 2010년의 매월 두 번째 일요일의 날짜를 출력하시오.
     * 
     * [실행결과]
     * 2010-01-10은 2번째 일요일입니다.
     * 2010-02-14은 2번째 일요일입니다.
     * 2010-03-14은 2번째 일요일입니다.
     * 2010-04-11은 2번째 일요일입니다.
     * 2010-05-09은 2번째 일요일입니다.
     * 2010-06-13은 2번째 일요일입니다.
     * 2010-07-11은 2번째 일요일입니다.
     * 2010-08-08은 2번째 일요일입니다.
     * 2010-09-12은 2번째 일요일입니다.
     * 2010-10-10은 2번째 일요일입니다.
     * 2010-11-14은 2번째 일요일입니다.
     * 2010-12-12은 2번째 일요일입니다.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.01.15
     */
    public static void exercise10_1() {
        Calendar startDt = Calendar.getInstance();
        startDt.set(2010,1,1);

        logger.debug("YEAR : {}", startDt.get(Calendar.YEAR));
        logger.debug("MONTH : {}", startDt.get(Calendar.MONTH));
        logger.debug("DAY_OF_MONTH : {}", startDt.get(Calendar.DAY_OF_MONTH));
        
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd은  2번째 일요일입니다.");
//        logger.debug("cal : {}", sf.format(cal.getTime()));
    }
    
    public static void exercise10_2() {
        
    }
    
    /**
     * <pre>
     * [10-3] 문자열 '123,456,789.5'를 소수점 첫 번째 자리에서 반올림하고, 그 값을 만 단 위마다 콤마(,)로 구분해서 출력하시오.
     * 
     * [실행결과]
     * data:123,456,789.5
     * 반올림:123456790 
     * 만단위:1,2345,6790
     * </pre>
     * 
     * @author : pej 
     * @date : 2023.01.15 
     */
    public static void exercise10_3() {
        String data = "123,456,789.5";
        System.out.println("data: "+ data);
        
        String[] patten = {"#", "#,####,####"};

        String dataStr = "123,456,789.5".replace(",", "");
        Double number = Double.parseDouble(dataStr);
        
        DecimalFormat df = new DecimalFormat(patten[0]);
        System.out.println("반올림 : "+ df.format(number));
        
        df = new DecimalFormat(patten[1]);
        System.out.println("만단위 : "+ df.format(number));
    }
    public static void exercise10_4() {
        
    }
    public static void exercise10_5() {
        
    }
    public static void exercise10_6() {
        
    }
    
    /**
     * <pre>
     * [10-8] 서울과 뉴욕간의 시차가 얼마인지 계산하여 출력하시오.
     * 
     * [실행결과]
     * 2016-01-28T23:01:00.136+09:00[Asia/Seoul]
     * 2016-01-28T09:01:00.138-05:00[America/New_York] 
     * sec1=32400
     * sec2=-18000
     * diff=14 hrs
     * </pre>
     * 
     * @author : pej 
     * @date : 2023.01.15 
     */
    public static void exercise10_8() {
        // ZonedDateTime : LocalDateTime + 시간대
        ZonedDateTime seoulDate = LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul"));
        System.out.println("현재 서울 시간 : " + seoulDate);
       
        ZonedDateTime newYorkDate = LocalDateTime.now().atZone(ZoneId.of("America/New_York"));
        System.out.println("현재 뉴욕 시간 : " +newYorkDate);
        
        // ZoneOffset : UTC로부터 얼마만큼 떨어져 있는지 알려주며 간단하게 말하자면 시차를 의미함. 시간대 규칙이 적용되어서 나옴(써머타임)
        ZoneOffset seoulOffset = ZonedDateTime.now().getOffset();
        ZoneOffset newYorkOffset = newYorkDate.getOffset();
        
        // OffsetDateTime : ZoneOffset과 비슷하지만 시간대 규칙이 적용되지 않음
        ZoneOffset seoulOffsetDateTime = seoulDate.toOffsetDateTime().getOffset();
        ZoneOffset newYorkOffsetDateTime = newYorkDate.toOffsetDateTime().getOffset();
        
        int seoulOffsetInSec = seoulOffsetDateTime.get(ChronoField.OFFSET_SECONDS);
        int newYorkOffsetInSec = newYorkOffsetDateTime.get(ChronoField.OFFSET_SECONDS);
        
        System.out.println("sec1 = "+ seoulOffsetInSec);
        System.out.println("sec2 = "+ newYorkOffsetInSec);
        
        // 방법1) Duration 이용
        Duration du = Duration.between(seoulDate, newYorkDate);
//        System.out.println("diff = "+ du.toHours() + "hrs");
        // 방법2) ChronoUnit 이용
        long diffHour = ChronoUnit.HOURS.between(seoulDate, newYorkDate);
        System.out.println("diff = "+ diffHour + "hrs");
    }
    
    
    public static void main(String[] args) {
//        exercise10_1();
        exercise10_8();
    }
}
