package ch14;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream 학습
 *  
 * @author : pej 
 * @date : 2023.02.24
 */
public class TestStream {
    public static void test1() {
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Stream<Integer> stream = list.stream();
        stream.forEach(System.out::println);
        // 에러 발생 => 스트림은 일회성이여서 다시 만들어서 사용해야함(최종연산을 수행하면 스트림은 닫힘)
        // stream.forEach(System.out::println);
    }
    
    public static void test2() {
        String[] strArr = { "a", "b", "c", "d" };
//      Stream<String> stream2 = Stream.of(strArr);
      Stream<String> stream2 = Arrays.stream(strArr);
      stream2.forEach(System.out::println);
      
      System.out.println("===============================");
      // 1) 기본형 스트림 이용
      int[] intArr = { 1, 2, 3, 4, 5 };
      IntStream intStream = Arrays.stream(intArr);
//      intStream.forEach(System.out::println);
      System.out.println("sum : " + intStream.sum());
      
      System.out.println("===============================");
      // 2) Stream 이용
      // Integer[] intArr2 = { new Integer(1), new Integer(2), new Integer(3), new Integer(4), new Integer(5) };
      Integer[] intArr2 = { 1, 2, 3, 4, 5 };
      Stream<Integer> intStream2 = Arrays.stream(intArr2);
      intStream2.forEach(System.out::println);
    }
    
    public static void test3() {
        String[] lineArr = {
            "Believe or not It is true",
            "Do or do not There is no try"
        };
        
        Stream<String> lineStream = Arrays.stream(lineArr);
        lineStream
            .flatMap(line -> Stream.of(line.split(" +")))
            .forEach(System.out::println);
        
    }

    public static void main(String[] args) {
        test3();
    }
}
