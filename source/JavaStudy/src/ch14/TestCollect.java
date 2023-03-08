package ch14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class TestStudent {
    String name;
    boolean isMale; // 성별
    int hak;        // 학년
    int ban;        // 반
    int score;

    TestStudent(String name, boolean isMale, int hak, int ban, int score) { 
        this.name   = name;
        this.isMale = isMale;
        this.hak    = hak;
        this.ban    = ban;
        this.score  = score;
    }

    String  getName()  { return name;}
    boolean isMale()   { return isMale;}
    int     getHak()   { return hak;}
    int     getBan()   { return ban;}
    int     getScore() { return score;}

    public String toString() { 
        return String.format("[%s, %s, %d학년 %d반, %3d점]", name, isMale ? "남":"여", hak, ban, score); 
    }

    enum Level {
        HIGH, MID, LOW
    }
}

/**
 * Collect 학습
 *  
 * @author : pej 
 * @date : 2023.03.01
 */
public class TestCollect {
    @SuppressWarnings("unused")
    private static Logger logger = LogManager.getLogger(TestCollect.class);
    
    @SuppressWarnings("unused")
    public static void test1() {
        // collect() :  Collector를 매개변수로 하는 스트림의 최종연산
        /**
         * reduce()와 collect() 차이 
         *  - reduce() : 전체
         *  - collect() : 그룹별 reduce
         */
        
        /**
         *  - collect() : Collector를 매개변수로 하는 스트림 최종연산
         *  - Collector : collect() 관련 메서드가 있는 인터페이스
         *  - Collectors : Collector 인터페이스 구현체
         */
        // toList(), toSet(), toMap(), toCollection() : 스트림 -> 컬렉션으로 변환
        List<String> list = Arrays.asList("1", "2", "3", "4", "5");
        List<String> resultList1 = list.stream().collect(Collectors.toList());
        System.out.println("==== toList() ====");
        resultList1.stream().forEach(System.out::println);
        
        ArrayList<String> resultList2 = list.stream().collect(Collectors.toCollection(ArrayList::new));
        System.out.println("==== toCollection() ArrayList ====");
        resultList2.stream().forEach(System.out::println);
        
        HashSet<String> resultList3 = list.stream().collect(Collectors.toCollection(HashSet::new));
        System.out.println("==== toCollection() HashSet ====");
        resultList3.stream().forEach(System.out::println);
        
        // toArray() : 스트림 -> 배열로 변환, Object[] 타입으로 반환
        String[] strArr = { "AAAA", "AAAAAAAA", "AA", "A", "AAAAAAAAAAAA", "AAAAAAAAAAAAAAAAAAAAAAAAA", "" };
        Stream<String> stream = Stream.of(strArr);
        
        String[] result1 = (String[]) Stream.of(strArr).toArray();
        Object[] result2 = Stream.of(strArr).toArray();
        String[] result3 = Stream.of(strArr).toArray(String[]::new);
        // 출력
        System.out.println("==== 출력 ====");
        Arrays.stream(result3).forEach(System.out::println);
    }
    
    
    public static void test2() {
        TestStudent[] stuArr = {
            new TestStudent("나자바", true, 1, 1, 300),
            new TestStudent("나자바2", true, 1, 1, 300),
            new TestStudent("김지미", false, 1, 1, 250),
            new TestStudent("김자바", true, 1, 1, 200),
            new TestStudent("이지미", false, 1, 2, 150),
            new TestStudent("남자바", true, 1, 2, 100),
            new TestStudent("안지미", false, 1, 2, 50),
            new TestStudent("황지미", false, 1, 3, 100),
            new TestStudent("강지미", false, 1, 3, 150),
            new TestStudent("이자바", true, 1, 3, 200),
            new TestStudent("나자바", true, 2, 1, 300),
            new TestStudent("김지미", false, 2, 1, 250),
            new TestStudent("김자바", true, 2, 1, 200),
            new TestStudent("이지미", false, 2, 2, 150),
            new TestStudent("남자바", true, 2, 2, 100),
            new TestStudent("안지미", false, 2, 2, 50),
            new TestStudent("황지미", false, 2, 3, 100),
            new TestStudent("강지미", false, 2, 3, 150),
            new TestStudent("이자바", true, 2, 3, 200),
        };
        
        /**
         * - Collectors.groupingBy() : 분류 기준이 `Fuction`
         * - Collectors.partitioningBy() : 분류 기준이 `Predicate`, 반환타입이 boolean 타입  
         */
        
        // 학년별로 그룹화
        Map<Integer, List<TestStudent>> group1 = (Map<Integer, List<TestStudent>>) 
                Arrays.stream(stuArr)
                      .collect(Collectors.groupingBy(TestStudent::getHak))
        ;
        System.out.println("=== 학년별로 그룹화 === ");
        group1.values().stream().forEach(System.out::println);
        
        // 성별로 그룹화
        Map<Boolean, List<TestStudent>> partitioningGroup1 = 
                Arrays.stream(stuArr)
                      .collect(Collectors.partitioningBy(TestStudent::isMale))
        ;
        System.out.println("=== 성별로 그룹화 === ");
        partitioningGroup1.values().stream().forEach(System.out::println);
        
        // 학년별 그룹화 건수
        Map<Integer, Long> cntMap = Arrays.stream(stuArr).collect(Collectors.groupingBy(TestStudent::getHak, Collectors.counting()));
        System.out.println("=== 학년별 그룹화 건수=== ");
        System.out.println("1학년 : " + cntMap.get(1));
        System.out.println("2학년 : " + cntMap.get(2));
        
        // 학년별, 반별로 그룹화
        Map<Integer, Map<Integer, List<TestStudent>>> group2 = (Map<Integer, Map<Integer, List<TestStudent>>>) 
                Arrays.stream(stuArr)
                      .collect(Collectors.groupingBy(TestStudent::getHak, Collectors.groupingBy(TestStudent::getBan)))
        ;
        System.out.println("=== 학년별, 반별로 그룹화 === ");
        group2.values().stream().forEach(System.out::println);
        
        // 성별, 점수별로 그룹화
        Map<Boolean, Map<Boolean, Long>> group3 =
                Arrays.stream(stuArr)
                      .collect(Collectors.partitioningBy(TestStudent::isMale,
                                                         Collectors.partitioningBy(student -> student.getScore() >= 100, Collectors.counting())))
        ;
        System.out.println("=== 성별, 점수 100점 이상 그룹화 === ");
        group3.values().stream().forEach(System.out::println);
        System.out.println("남 : " + group3.get(true).get(true));
        System.out.println("여 : " + group3.get(false).get(true));
    }
    
    @SuppressWarnings("unused")
    public static void test3() {
        String[] array = {"Java", "Is", "Fun", "Isn't", "It", "?"};
        
        Map<Character, List<String>> map1 = Arrays.stream(array)
                .collect(Collectors.groupingBy(s -> s.charAt(0)));
//        System.out.println(map1);
        
        Map<Boolean, List<String>> map2 = Arrays.stream(array)
                .collect(Collectors.partitioningBy(s -> s.length() >= 3));
        map2.values().stream().forEach(System.out::println);
        
        Map<Boolean, Long> map3 = (Map<Boolean, Long>) Arrays.stream(array)
                .collect(Collectors.partitioningBy(s -> s.length() >= 3, Collectors.counting()));
        System.out.println(map3);
    }
    
    public static void main(String[] args) {
        test2();
    }
}