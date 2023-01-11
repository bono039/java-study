package exam;

import java.util.ArrayList;

/**
 * <pre>
 * [11-1] 다음은 정수집합 와 의 교집합 차집합 합집합을 구하는 코드이 1,2,3,4 3,4,5,6의 교집합, 차집합, 합집합을 구하는 코드이다.
 * 코드를 완성하여 실행결과와 같은 결과를 출력하시오.
 * [Hint] ArrayList addAll(), removeAll(), retainAll()을 사용하라.
 * 
 * [실행결과]
 * list1=[1, 2, 3, 4] 
 * list2=[3, 4, 5, 6] 
 * kyo=[3, 4] 
 * cha=[1, 2] 
 * hap=[1, 2, 3, 4, 5, 6]
 * </pre>
 * 
 * @author : pej
 * @date : 2023.01.11
 */
public class Exercise11_1 {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {
        ArrayList list1 = new ArrayList();
        ArrayList list2 = new ArrayList();
        ArrayList kyo = new ArrayList(); // 교집합
        ArrayList cha = new ArrayList(); // 차집합
        ArrayList hap = new ArrayList(); // 합집합
        
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);
        list2.add(3);
        list2.add(4);
        list2.add(5);
        list2.add(6);
        /*
         * (1) . 알맞은 코드를 넣어 완성하시오
         */
        System.out.println("list1=" + list1);
        System.out.println("list2=" + list2);
        System.out.println("kyo=" + kyo);
        System.out.println("cha=" + cha);
        System.out.println("hap=" + hap);
    }
    
    public static void exercise11_1_pej() {
        ArrayList list1 = new ArrayList();
        ArrayList list2 = new ArrayList();
        ArrayList kyo = new ArrayList(); // 교집합
        ArrayList cha = new ArrayList(); // 차집합
        ArrayList hap = new ArrayList(); // 합집합
        
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);
        list2.add(3);
        list2.add(4);
        list2.add(5);
        list2.add(6);
        /*
         * (1) . 알맞은 코드를 넣어 완성하시오
         */
        System.out.println("list1=" + list1);
        System.out.println("list2=" + list2);
        System.out.println("kyo=" + kyo);
        System.out.println("cha=" + cha);
        System.out.println("hap=" + hap);
    }
    
    public static void exercise11_1_kha() {
        ArrayList list1 = new ArrayList();
        ArrayList list2 = new ArrayList();
        ArrayList kyo = new ArrayList(); // 교집합
        ArrayList cha = new ArrayList(); // 차집합
        ArrayList hap = new ArrayList(); // 합집합
        
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);
        list2.add(3);
        list2.add(4);
        list2.add(5);
        list2.add(6);
        /*
         * (1) . 알맞은 코드를 넣어 완성하시오
         */
        System.out.println("list1=" + list1);
        System.out.println("list2=" + list2);
        System.out.println("kyo=" + kyo);
        System.out.println("cha=" + cha);
        System.out.println("hap=" + hap);
    }
}
