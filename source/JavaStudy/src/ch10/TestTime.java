package ch10;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Date, Calendar 클래스 학습
 *  
 * @author : pej 
 * @date : 2022.12.30 
 */
public class TestTime implements TemporalAdjuster {
    private static Logger logger = LogManager.getLogger(TestTime.class);
    
    /**
     * adjustInto() 내부적으로 사용하는 메서드로 실제로는 with()을 사용하면 됨
     * 
     * @author : pej 
     * @date : 2022.12.30 
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        // 2일 더하기
        return temporal.plus(2, ChronoUnit.DAYS);
    }
    
    /**
     * TemporalAdjuster 사용자정의 메서드
     * 
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void testAdjustInto() {
        LocalDate calDay = LocalDate.now();
        logger.debug("사용자정의 메서드 추가 : {}", calDay.with(new TestTime()));
        
    }
    /**
     * LocalDate 테스트
     * 
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void testLocalDate() {
        // 날짜
        LocalDate today = LocalDate.now(); // 현재 날짜
        logger.debug("### today : {}", today);
        
        LocalDate day = LocalDate.of(2023, 12, 29);
        // LocalDate day = LocalDate.of(2023, 2, 30); // 없는 날짜인 경우 java.time.DateTimeException 발생
        logger.debug("### day : {}", day);
        
        // get(), getXXX() : 특정 필드의 값 가져오기
        logger.debug("### getDayOfWeek() : {}", day.getDayOfWeek());
        // with(), withXXX() :  특정 필드의 값을 변경
        LocalDate changeYear = day.withYear(2023);
        logger.debug("### changeYear : {}", changeYear);
        
        // plus() : 날짜 더하기
        LocalDate changeDay = day.plusDays(1);
        logger.debug("### changeDay : {}", changeDay);
    }
    
    /**
     * LocalTime 테스트
     * 
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void testLocalTime() {
        // 시간
        LocalTime now = LocalTime.now(); // 현재시간
        logger.debug("### now : {}", now);
        
        LocalTime time = LocalTime.of(10, 10, 10);
        logger.debug("### time : {}", time);
    }
    
    /**
     * Instant 테스트
     * 
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void testInstant() {
        Instant now = Instant.now();
        logger.debug("Instant now : {}", now);
        
        // Instant, Date간의 변환
        Date convertDate = Date.from(now);
        logger.debug("Instant => Date : {}", convertDate);
        
        Instant convertInst = convertDate.toInstant();
        logger.debug("Date => Instant : {}", convertInst);
    }
    
    /**
     * Period, Duration, ChronoUnit 테스트
     * 
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void testCal() {
        LocalDate stDt = LocalDate.of(2022, 12, 01);
        LocalDate edDt = LocalDate.of(2022, 12, 29);
        
        /**
         * 년 차이 : getYears()
         * 월 차이 : getMonths()
         * 일 차이 : getDays()
         */
        logger.debug("Period 이용 - 기간 : {}", Period.between(stDt, edDt));
        
        long day = ChronoUnit.DAYS.between(stDt, edDt);
        logger.debug("ChronoUnit 이용 - 기간 : {}", day);
        
        LocalTime stTime = LocalTime.of(9, 30);
        LocalTime edTime = LocalTime.of(18, 30);
        
        // 시간이나 분 차이를 가져오는게 없음
        logger.debug("Duration 이용 - 차이 : {}", Duration.between(stTime, edTime));
        
        long betweenHour = ChronoUnit.HOURS.between(stTime, edTime);
        logger.debug("ChronoUnit 이용 - 시간 차이 : {}", betweenHour);

        long betweenMinute = ChronoUnit.MINUTES.between(stTime, edTime);
        logger.debug("ChronoUnit 이용 - 분 차이 : {}", betweenMinute);
    }
    
    /**
     * DateTimeFormatter 테스트
     * 
     * @author : pej 
     * @date : 2022.12.30 
     */
    public static void testDateTimeFormat() {
        // locale 포맷형식
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        logger.debug("locale 포맷형식  : {}", dtf.format(LocalDateTime.now()));
        
        // 포맷형식 지정
        DateTimeFormatter patten = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일  HH시 mm분 E요일");
        String formatDt = patten.format(LocalDateTime.now());
        logger.debug("포맷형식 지정 : {}", formatDt);
        // 파싱
        logger.debug("지정된 포맷형식 파싱 : {}", LocalDateTime.parse(formatDt, patten));
    }


    public static void main(String[] args) {
//        testInstant();
//        testCal();
//        testAdjustInto();
        testDateTimeFormat();
    }
}
