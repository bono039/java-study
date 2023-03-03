package exam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 챕터14 문제
 *  
 * @author : pej 
 * @date : 2023.02.28
 */
class Student { 
    String name;
    boolean isMale; // 성별
    int hak;        // 학년
    int ban;        // 반
    int score;

    Student(String name, boolean isMale, int hak, int ban, int score){ 
        this.name = name;
        this.isMale = isMale;
        this.hak = hak;
        this.ban = ban;
        this.score = score;
    }
  
    String getName() { return name;}
    boolean isMale() { return isMale;}
    int getHak() { return hak;}
    int getBan() { return ban;}
    int getScore() { return score; }

    public String toString() {
    return String.format("[%s, %s, %d학년 %d반, %3d점]", name, isMale ? "남":"여", hak, ban, score);
    }
    // groupingBy()에서 사용
    enum Level { HIGH, MID, LOW } // 성적을 상, 중, 하 세 단계로 분류
}

public class Ch14Exam {
    private static Logger logger = LogManager.getLogger(Ch14Exam.class);
    
    /**
     * <pre>
     * [14-1] 메서드를 람다식으로 변환하여 아래의 표를 완성하시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.01
     */
    public void exam01() {
        /**
         * int printVar(String name, int i) { 
         *   System.out.println(name+"="+i );
         * }
         * (a) (name, i) -> System.out.println(name+"="+i)
         */
        
        /**
         * int square(int x) { 
         *   return x * x;
         * }
         * (b) (x) -> x * x
         */
        
        /**
         * int roll() {
         *   return (int)(Math.random() * 6);
         * }
         * (c) () -> (int)(Math.random() * 6)
         */
        
        /**
         * int sumArr(int[] arr) { 
         *   int sum = 0; 
         *   for(int i : arr)
         *      sum += i; 
         *    return sum;
         * }
         * (d) (arr) -> {
         *         int sum = 0;
         *         for (int i : arr) {
         *            sum += i; 
         *         }
         *         return sum;
         *     }
         */
        
        /**
         * int[] emptyArr() { 
         *    return new int[]{};
         * }
         * (e) () -> new int[]{}
         */
    }
    
    /**
     * <pre>
     * [14-2] 람다식을 메서드 참조로 변환하여 표를 완성하시오. (변환이 불가능한 경우, ‘변환불가’라고 적어야함.)
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.01
     */
    public void exam02() {
        /**
         * (String s) -> s.length()
         * (1) String::length
         */
        
        
        /**
         * ()-> new int[]{}
         * (2) int[]::new
         */
        
        /**
         * arr -> Arrays.stream(arr)
         * (3) Arrays::stream
         */
        
        /**
         * (String str1, String str2) -> str1.equals(str2)
         * (4) String::equals
         */
        
        /**
         * (a, b) -> Integer.compare(a, b)
         * (5) Integer::compare
         */
        
        /**
         * (String kind, int num) -> new Card(kind, num)
         * (6) Card::new
         */
        /**
         * (x) -> System.out.println(x)
         * (7) System.out::println
         */
        
        /**
         * ()-> Math.random()
         * (8) Math::random
         */
        
        /**
         * (str) -> str.toUpperCase()
         * (9) String::toUpperCase
         */
        
        /**
         * () -> new NullPointerException()
         * (10) NullPointerException::new
         */
        
        /**
         * (Optional opt) -> opt.get()
         * (11) Optional::get
         */
        
        /**
         * (StringBuffer sb, String s) -> sb.append(s)
         * (12) StringBuffer::append
         */
        
        /**
         * (String s) -> System.out.println(s)
         * (13) System.out::println
         */
    }
    
    /**
     * <pre>
     * [14-3] 아래의 괄호안에 알맞은 함수형 인터페이스는?
     * 
     * ( ) f; // 함수형 인터페이스 타입의 참조변수 f를 선언
     * f = (int a, int b) -> a > b ? a : b;
     *
     * 답 : b, d
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.01
     */
    public static void exam03() {
        BiFunction<Integer, Integer, Integer> f = (a, b) -> a > b ? a : b;
        System.out.println(f.apply(1, 3));
        
        BinaryOperator<Integer> f2 = (a,b) -> a > b ? a : b;
        System.out.println(f2.apply(1,3));
    }
    
    /**
     * <pre>
     * [14-4] 두 개의 주사위를 굴려서 나온 눈의 합이 6인 경우를 모두 출력하시오.
     * [Hint] 배열을 사용하시오.
     * 
     * [실행결과]
     * [1, 5]
     * [2, 4]
     * [3, 3]
     * [4, 2]
     * [5, 1]
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.01
     */
    public static void exam04() {
        List<String> list = Arrays.asList("1","2","3","4","5");

        list.stream()
            .flatMap(item -> {
                String[] newArr = new String[item.split(" ").length];
                StringBuffer sb = new StringBuffer();
                
                for (int i = 0; i <newArr.length; i++) {
                    sb.append("[");
                    sb.append(item);
                    sb.append(",");
                    sb.append( 6 - Integer.parseInt(item));
                    sb.append("]");
                    
                    newArr[i] = sb.toString();
                }
                return Arrays.stream(newArr);
            })
            .forEach(System.out::println);
    }
    
    /**
     * <pre>
     * [14-5] 문자열 배열 strArr의 모든 문자열의 길이를 더한 결과를 출력하시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.01
     */
    public static void exam05() {
        String[] strArr = { "aaa","bb","c", "dddd" };
        int sum = Stream.of(strArr).mapToInt(String::length).sum();
        logger.debug("sum = {}", sum);
    }
    
    /**
     * <pre>
     * [14-6] 문자열 배열 strArr의 문자열 중에서 가장 긴 것의 길이를 출력하시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.01
     */
    public static void exam06() {
        String[] strArr = { "aaa","bb","c", "dddd" };
        int max = Stream.of(strArr).mapToInt(String::length).max().getAsInt();
        logger.debug("max = {}", max);
    }
    /**
     * <pre>
     * [14-7] 임의의 로또번호(1~45)를 정렬해서 출력하시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.01
     */
    public static void exam07() {
       // Random().ints(개수, 범위시작, 범위끝)
       // 1보다 크거나 같고 46보다 작은 난수 6개
       IntStream intStream = new Random().ints(6, 1, 46);
       /**
        * Q. forEachOrdered() 정렬이 왜 안되는지?
        */
       // intStream.distinct().forEachOrdered(System.out::println);
       intStream.distinct().sorted().forEach(System.out::println);
    }
    
    /**
     * <pre>
     * [14-8] 다음은 불합격(150점 미만)한 학생의 수를 남자와 여자로 구별하여 출력하는 프 로그램이다. (1)에 알맞은 코드를 넣어 완성하시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.01
     */
    public static void exam08() {
        Student[] stuArr = {
            new Student("나자바", true, 1, 1, 300),
            new Student("김지미", false, 1, 1, 250),
            new Student("김자바", true, 1, 1, 200),
            new Student("이지미", false, 1, 2, 150),
            new Student("남자바", true, 1, 2, 100),
            new Student("안지미", false, 1, 2, 50),
            new Student("황지미", false, 1, 3, 100),
            new Student("강지미", false, 1, 3, 150),
            new Student("이자바", true, 1, 3, 200),
            
            new Student("나자바", true, 2, 1, 300),
            new Student("김지미", false, 2, 1, 250),
            new Student("김자바", true, 2, 1, 200),
            new Student("이지미", false, 2, 2, 150),
            new Student("남자바", true, 2, 2, 100),
            new Student("안지미", false, 2, 2, 50),
            new Student("황지미", false, 2, 3, 100),
            new Student("강지미", false, 2, 3, 150),
            new Student("이자바", true, 2, 3, 200)
        };
        
        /*
        (1) 알맞은 코드를 넣으시오.
        */
        Map<Boolean, Map<Boolean, List<Student>>> me = Arrays.stream(stuArr)
                .collect(
                        Collectors.partitioningBy(
                                Student::isMale,
                                Collectors.partitioningBy(student -> student.getScore()< 150)
                         )
                )
        ;
        long failedMaleStuNum2 = me.get(true).get(true).size();
        long failedFemaleStuNum2 = me.get(false).get(true).size();
        
        // 답안
        Map<Boolean, Map<Boolean, Long>> failedStuBySex = Stream.of(stuArr)
                .collect(
                        Collectors.partitioningBy(
                                Student::isMale,
                                Collectors.partitioningBy(student -> student.getScore()< 150, Collectors.counting())
                        )
                 )
        ;

        long failedMaleStuNum = failedStuBySex.get(true).get(true);
        long failedFemaleStuNum = failedStuBySex.get(false).get(true);

        System.out.println("불합격[남자]:"+ failedMaleStuNum +"명");
        System.out.println("불합격[여자]:"+ failedFemaleStuNum +"명");
        System.out.println("불합격[남자]:"+ failedMaleStuNum2 +"명");
        System.out.println("불합격[여자]:"+ failedFemaleStuNum2 +"명");
    }
    
    /**
     * <pre>
     * [14-9] 다음은 각 반별 총점을 학년 별로 나누어 출력하는 프로그램이다. (1)에 알맞은 코드를 넣어 완성하시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.01
     */
    public static void exam09() {
        Student[] stuArr = {
            new Student("나자바", true, 1, 1, 300),
            new Student("김지미", false, 1, 1, 250),
            new Student("김자바", true, 1, 1, 200),
            new Student("이지미", false, 1, 2, 150),
            new Student("남자바", true, 1, 2, 100),
            new Student("안지미", false, 1, 2, 50),
            new Student("황지미", false, 1, 3, 100),
            new Student("강지미", false, 1, 3, 150),
            new Student("이자바", true, 1, 3, 200),
            new Student("나자바", true, 2, 1, 300),
            new Student("김지미", false, 2, 1, 250),
            new Student("김자바", true, 2, 1, 200),
            new Student("이지미", false, 2, 2, 150),
            new Student("남자바", true, 2, 2, 100),
            new Student("안지미", false, 2, 2, 50),
            new Student("황지미", false, 2, 3, 100),
            new Student("강지미", false, 2, 3, 150),
            new Student("이자바", true, 2, 3, 200)
      };

        /*
        (1) 알맞은 코드를 넣으시오.
        */
      Map<Integer, Map<Integer, Long>> totalScoreByHakAndBan = Stream.of(stuArr)
              .collect(
                  Collectors.groupingBy(Student::getHak, 
                          Collectors.groupingBy( Student::getBan, Collectors.summingLong(Student::getScore))
                   )
              );

       for (Object e : totalScoreByHakAndBan.entrySet()) { 
           System.out.println(e);
       }
    }

    public static void main(String[] args) {
//        exam04();
        test();
    }
}
