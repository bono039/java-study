---
created: 2022-12-25
title: 자바스터티_10_날짜와시간형식화
author: pej
category: study
tag: study
aliases: [자바스터티_10_날짜와시간형식화]
---

#### 책 목차
	- 날짜와 시간
	- 형식화 클래스
	- java.time 패키지


#### 학습할 것
	- Date, Calender 클래스의 차이
	- 형식화 클래스 사용 방법
	- java.time.패키지

#### 학습 내용
1. 날짜와 시간
	+ Date 클래스
		+ 날짜, 시간 정보를 알려주는 클래스
		+ **Calender 클래스가 생기면서 대부분의 메서드가 `deprecatied` 되었음**
		```
        // 현재 날짜, 시간 가져옴
        Date now = new Date();
        logger.debug("## now : {} / now.toString() : {}", now, now.toString());
        
        // 24시간 형식
        SimpleDateFormat dateForamt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.debug("## 날짜 형식 : {}", dateForamt.format(now));
		[결과]
		## now : 2022-12-25T20:29:56.119+0900 / now.toString() : Sun Dec 25 20:29:56 KST 2022
		## 날짜 형식 : 2022-12-25 20:29:56
		```
	+ Calender 클래스
		+ 운영체제의 날짜, 시간 정보를 알려주는 클래스
		+ **추상클래스여서 직접 인스턴스를 생성 할 수 없음**
		+ 시스템 국가를 확인하여 BuddhistCanlendar 클래스 또는 GregorianCalendar 클래스를 인스턴스로 반환함
		```
        // getInstance() : 현재 시스템의 날짜와 시간 정보를 알려줌
        Calendar calNow = Calendar.getInstance();
        logger.debug("## Calendar now : {}", calNow.toString());
        [결과]
		## Calendar now : java.util.GregorianCalendar[time=1671968951280,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="Asia/Seoul",offset=32400000,dstSavings=0,useDaylight=false,transitions=22,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2022,MONTH=11,WEEK_OF_YEAR=53,WEEK_OF_MONTH=5,DAY_OF_MONTH=25,DAY_OF_YEAR=359,DAY_OF_WEEK=1,DAY_OF_WEEK_IN_MONTH=4,AM_PM=1,HOUR=8,HOUR_OF_DAY=20,MINUTE=49,SECOND=11,MILLISECOND=280,ZONE_OFFSET=32400000,DST_OFFSET=0]
		```
		```
		Calendar calNow = Calendar.getInstance();
        SimpleDateFormat dateForamt2 = new SimpleDateFormat("yyyy년 MM월 dd일  HH시 mm분 E요일");
        logger.debug("## Calendar 날짜 형식 : {}", dateForamt2.format(calNow.getTime()));
		[결과]
		## Calendar 날짜 형식 : 2022년 12월 25일  20시 54분 일요일
		```
2. 형식화 클래스
	+ `parse()` 메서드를 이용해서 형식화된 데이터에서 원하는 데이터만 추출할 수 있음
	+ DecimalFormat 클래스
		+ 숫자 형식화시 사용됨
		+ `parse()` 메서드 이용하면 숫자로도 변환이 가능하나 `Number` 클래스 타입으로 반환됨
		```
		double number = 1_000_000.00;
		DecimalFormat df = new DecimalFormat("#,###");
		logger.debug("format(#,###) : {}", df.format(number));
		
		try {
			Number num = df.parse("1,000,000");
			logger.debug("formate -> 문자열 : {}", num);
		} catch (ParseException e) {
			logger.error("error : {}", e.getMessage());
		}
		[결과]
		#,### format : 1,000,000
		formate -> 문자열 : 1000000
		```
	+ SimpleDateFormat 클래스
		+ 날짜 형식화시 사용됨
		```
		Date today = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        logger.debug("format(yyyy-MM-dd) : {}", sf.format(today));
        
        try {
            Date today2 = sf.parse("2022-12-31");
            logger.debug("formate -> 문자열 : {}", today2);
        } catch (ParseException e) {
            logger.error("error : {}", e.getMessage());
        }
        [결과]
        format(yyyy-MM-dd) : 2022-12-27
        formate -> 문자열 : 2022-12-31T00:00:00.000+0900
		```
	+ ChoiceFormat 클래스
		+ 특정 범위에 속하는 값을 문자열로 변환해주는 형식화 클래스
		+ **`범위#값` 형태로 사용함**
		+ **패턴의 구분자로 `#, ≤(\u2264), <`만 제공함**
		```
        int[] scores = { 41, 88, 95, 70, 60, 100 }; 
        
        // 방법1) 패턴 지정
        /**
         * #, ≤(\u2264) : 경계 포함
         * < : 경계 불포함
         */
		// String newPattern = "60≤D|70≤C|80≤B|90≤A";
        String newPattern = "60<D|70<C|80<B|90<A";
        ChoiceFormat cf = new ChoiceFormat(newPattern);
	    
	    // 방법2) 범위, 값 따로 지정
        double[] limitArr = { 60, 70, 80, 90 }; // double 타입만 가능
        String[] valueArr = { "D", "C", "B", "A" };
        ChoiceFormat cf2 = new ChoiceFormat(limitArr, valueArr);
        
        for (int i : scores) {
            logger.debug("점수 및 등급 : {} / {}", i, cf.format(i));
        }
        [결과]
        점수 및 등급 : 41 / D
		점수 및 등급 : 88 / B
		점수 및 등급 : 95 / A
		점수 및 등급 : 73 / C
		점수 및 등급 : 60 / D
		점수 및 등급 : 100 / A
		```
	+ MessageFormat 클래스
		+ 정해진 형식이 있는 경우 사용하면 좋음
		+ 특이하게 `format()`메서드가 static으로 되어있음
		```
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
        [결과]
        format : 이름 : pej 
                 전화번호 : 010-1234-5678 
        INSERT INTO TEST (ID, NAME, PHON, ADDR) VALUES('1', 'TEST1', '01011111111', '서울특별시 영등포구');
		INSERT INTO TEST (ID, NAME, PHON, ADDR) VALUES('2', 'TEST2', '01011111111', '서울특별시 영등포구');
		INSERT INTO TEST (ID, NAME, PHON, ADDR) VALUES('3', 'TEST3', '01011111111', '서울특별시 영등포구');
		INSERT INTO TEST (ID, NAME, PHON, ADDR) VALUES('4', 'TEST4', '01011111111', '서울특별시 영등포구');
		INSERT INTO TEST (ID, NAME, PHON, ADDR) VALUES('5', 'TEST5', '01011111111', '서울특별시 영등포구');
		```

3. java.time 패키지
	+ **JDK 1.8부터 추가되었으며 날짜와 시간이 별도의 클래스로 분리되어 있음** 
	
	| 구분               | 클래스명      |
	| ------------------ | ------------- |
	| 날짜               | LocalDate     |
	| 시간               | LocalTime     |
	| 날짜, 시간         | LocalDateTime |
	| 날짜, 시간, 시간대 | ZoneDateTime  |
	
	```
	// 날짜
	LocalDate today = LocalDate.now(); // 현재 날짜
	logger.debug("### today : {}", today);
	
	// 시간
	LocalTime now = LocalTime.now(); // 현재시간
	logger.debug("### now : {}", now);
	
	LocalTime time = LocalTime.of(10, 10, 10);
	logger.debug("### time : {}", time);
	
	LocalDate day = LocalDate.of(2023, 12, 29);
	// 없는 날짜인 경우 java.time.DateTimeException 발생
	// LocalDate day = LocalDate.of(2023, 2, 30); 
	logger.debug("### day : {}", day);
	
	// get(), getXXX() : 특정 필드의 값 가져오기
	logger.debug("### getDayOfWeek() : {}", day.getDayOfWeek());
	// with(), withXXX() :  특정 필드의 값을 변경
	LocalDate changeYear = day.withYear(2023);
	logger.debug("### changeYear : {}", changeYear);
	
	// plus() : 날짜 더하기
	LocalDate changeDay = day.plusDays(1);
	logger.debug("### changeDay : {}", changeDay);
	[결과]
	### today : 2022-12-29
	### now : 22:27:03.721
	### time : 10:10:10
	### day : 2023-12-29
	### getDayOfWeek() : FRIDAY
	### changeYear : 2023-12-29
	### changeDay : 2023-12-30
	```
	+ Instant
		+ **java.util.Date 클래스를 대체하기 위한 클래스로 UTC기준으로부터 경과된 시간을 타임스탬프로 표현함**
		+ JDK 1.8부터 Date에서 Instant로 변환할 수 있는 메서드가 추가됨
			+ `Date.from()` : Instant => Date
			+ `toInstant()` : Date => Instant
		```
		Instant now = Instant.now();
		logger.debug("Instant now : {}", now);
		
		// Instant, Date간의 변환
		Date convertDate = Date.from(now);
		logger.debug("Instant => Date : {}", convertDate);
		
		Instant convertInst = convertDate.toInstant();
		logger.debug("Date => Instant : {}", convertInst);
		[결과]
		Instant now : 2022-12-29T13:45:42.862Z
		Instant => Date : 2022-12-29T22:45:42.862+0900
		Date => Instant : 2022-12-29T13:45:42.862Z
		```
	+ Period, Duration, ChronoUnit 클래스
		+ `Period` : 날짜간의 연산시 사용
		+ `Duration` : 시간간의 연산시 사용
		+ `ChronoUnit` : TemporalUnit 인터페이스를 구현한 Enum 클래스로 날짜, 시간 단위가 정의되어 있음
		```
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
		[결과]
		Period 이용 - 기간 : P28D
		ChronoUnit 이용 - 기간 : 28
		Duration 이용 - 차이 : PT9H
		ChronoUnit 이용 - 시간 차이 : 9
		ChronoUnit 이용 - 분 차이 : 540
		```
	+ TemporalAdjusters 클래스
		+ **자주 사용될만한 날짜 계산 메서드가 정의되어 있는 클래스**
		+ `TemporalAdjuster` 인터페이스를 구현해서 필요한 메서드를 만들수도 있음
		```
		public class TestTime implements TemporalAdjuster {
		    private static Logger logger = LogManager.getLogger(TestTime.class);
		    
		    /**
		     * adjustInto() 내부적으로 사용하는 메서드로 실제로는 with()을 사용하면 됨
		     */
		    @Override
		    public Temporal adjustInto(Temporal temporal) {
		        // 2일 더하기
		        return temporal.plus(2, ChronoUnit.DAYS);
		    }
		
		    public static void testAdjustInto() {
		        LocalDate calDay = LocalDate.now();
		        logger.debug("### 사용자정의 메서드 추가 : {}", calDay.with(new TestTime()));
		    }
		
		    public static void main(String[] args) {
		        testAdjustInto();
		    }
		}
		[결과]
		### 사용자정의 메서드 추가 : 2022-12-31
		```
	+ DateTimeFormat 클래스
		+ JDK 1.8에서 추가된 클래스로 날짜와 시간을 형식화시 사용함
		```
		// locale 포맷형식
		DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
		logger.debug("locale 포맷형식  : {}", dtf.format(LocalDateTime.now()));
		
		// 포맷형식 지정
		DateTimeFormatter patten = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일  HH시 mm분 E요일");
		String formatDt = patten.format(LocalDateTime.now());
		logger.debug("포맷형식 지정 : {}", formatDt);
		// 파싱
		logger.debug("지정된 포맷형식 파싱 : {}", LocalDateTime.parse(formatDt, patten));
		[결과]
		locale 포맷형식  : 2022년 12월 29일 (목) 오후 11시 46분 29초
		포맷형식 지정 : 2022년 12월 29일  23시 46분 목요일
		지정된 포맷형식 파싱 : 2022-12-29T23:46
		```
#### 출처(참고문헌)
- Java의 정석
- 혼자 공부하는 자바
- https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html

#### 연결문서
- 자바스터디_09_java.lang패키와유용한클래스

#### 각주
