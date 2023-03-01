package ch14;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Optional 학습
 *  
 * @author : pej 
 * @date : 2023.02.27
 */
public class TestOptional {
    private static Logger logger = LogManager.getLogger(TestOptional.class);
    
    public static void test1() {
        // Optional<T> 객체 생성 방법
        String value = "test";
        Optional<String> opt = Optional.of(value);
        logger.debug("opt 실행결과 : {}", opt);  // 실행결과 : Optional[test]
        
        Optional<String> opt2 = Optional.of("aaa");
        logger.debug("opt2 실행결과 : {}", opt2);  // 실행결과 : Optional[aaa]
        
        // Optional<String> opt3 = Optional.of(null); // java.lang.NullPointerException 발생
        Optional<String> opt3 = Optional.ofNullable(null);
        logger.debug("opt3 실행결과 : {}", opt3);  // 실행결과 : Optional.empty
        
        // null로 초기화 가능하지만 되도록이면 empty() 이용
        Optional<String> opt4 = null;
        logger.debug("opt4 실행결과 : {}", opt4);  // 실행결과 : null
        Optional<String> opt5 = Optional.empty();
        logger.debug("opt5 실행결과 : {}", opt5);  // 실행결과 : Optional.empty
    }
    
    public static void test2() {
        // Optional<T> 객체의 값을 가져오는 방법
        String value = "test";
        Optional<String> opt = Optional.of(value);
        
        // get() : null이면 에러 발생함
        String str1 = opt.get();
        logger.debug("str1 실행결과 : {}", str1);
        
        // orElse() : 저장된 값이 null인 경우 대체할 값을 반환
        Optional<String> opt2 = Optional.ofNullable(null);
        String str2 = opt2.orElse("null");
        logger.debug("str2 실행결과 : {}", str2);
        
        // orElseGet() : orElse()의 변형, 저장된 값이 null인경우 대체할 값을 람다식으로 지정 할 수 있음
        String str3 = opt2.orElseGet(() -> new String("새로 만들기"));
        logger.debug("str3 실행결과 : {}", str3);
        
        // orElseThrow() : orElse()의 변형, 저장된 값이 null인 경우 지정된 예외를 발생시킴
//         String str4 = opt2.orElseThrow(NullPointerException::new);
//        logger.debug("str4 실행결과 : {}", str4);
        
        // isPresent() : 저장된 값이 null인 경우 false, 아니면 true를 반환
        logger.debug("isPresent 실행결과 : {}", opt2.isPresent());
        // ifPresent() : 저장된 값이 null이 아닌 경우만 수행됨
        opt.ifPresent(System.out::println);
    }
    
    public static void test3() {
        /**
         * OptionalInt, OptionalLong, OptionalDouble : 기본형을 감싼 래퍼 클래스
         * 사용이유 : 성능 때문에
         */
        
        // 값 가져오기
        // getAs타입() : getAsInt(), getAsLong(), getAsDouble()
        
        OptionalInt optInt = OptionalInt.of(0);
        OptionalInt optInt2 = OptionalInt.empty();
        logger.debug("equals 실행결과 : {}",  optInt.equals(optInt2));
    }
    
    public static void test4() {
        // 스트림의 최종연산
        
        // forEach() : 병렬인 경우에는 순서가 보장되지 않음, forEachOrdered() 보다 속도가 더 빠름 
        // forEachOrdered() : 병렬인 경우에도 순서가 보장됨
        System.out.println("forEach()");
        IntStream.range(1, 10).forEach(System.out::print);
        System.out.println("");
        System.out.println("====================");
        System.out.println("forEachOrdered()");
        IntStream.range(1, 10).forEachOrdered(System.out::print);
        System.out.println("");
        System.out.println("====================");

        // parallel() : 순서가 보장되지 않음
        System.out.println("parallel().forEach()");
        IntStream.range(1, 10).parallel().forEach(System.out::print);
        System.out.println("");
        System.out.println("====================");
        System.out.println("parallel().forEachOrdered()");
        IntStream.range(1, 10).parallel().forEachOrdered(System.out::print);
    }
    
    public static void test5() {
        // reduce() : 누적연산 수행
        // reduce(초기값, 누적 수행 작업)
        String[] strArr = { "AAAA", "AAAAAAAA", "AA", "A", "AAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAAAAAAAAA", "" };
        
        // Stream<String> -> Stream<Integer>으로 변환
        // Stream<Integer> intStream = Stream.of(strArr).map(String::length);
        // Stream<String> -> IntStream으로 변환
        IntStream intStream = Stream.of(strArr).mapToInt(String::length);
        intStream.forEach(System.out::println);
        
        int cnt = Stream.of(strArr).mapToInt(String::length).reduce(0, (a,b) -> a + 1);
        System.out.println("cnt : " + cnt);
        
        int sum = Stream.of(strArr).mapToInt(String::length).reduce(0, (a,b) -> a + b);
        System.out.println("sum : " + sum);
        
        OptionalInt max = Stream.of(strArr).mapToInt(String::length).reduce(Integer::max);
        System.out.println("max : " + max.getAsInt());
        OptionalInt min = Stream.of(strArr).mapToInt(String::length).reduce(Integer::min);
        System.out.println("min : " + min.getAsInt());
    }
    
    public static void main(String[] args) {
        test5();
    }
}
