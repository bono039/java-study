package exam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * 챕터11 문제
 *  
 * @author : pej 
 * @date : 2023.01.25
 */
public class Ch11Exam {
    private static Logger logger = LogManager.getLogger(Ch11Exam.class);
    
    /**
     * <pre>
     * [11-2] 다음 코드의 실행결과를 적으시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.28
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void exam02() {
        ArrayList list = new ArrayList();
        list.add(3);
        list.add(6);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(7);
        
        HashSet<Integer> testSet = new HashSet<>();
        testSet.add(5);
        testSet.add(4);
        testSet.add(3);
        testSet.add(3);
        testSet.add(2);
        testSet.add(1);
        // 자동으로 오름차순 해줌
        logger.debug("정렬 테스트 : {}", testSet);
        
        HashSet set = new HashSet(list);
        logger.debug("HashSet : {}", set);
        TreeSet tset = new TreeSet(set);
        logger.debug("TreeSet : {}", tset);
        
        Stack stack = new Stack();
        stack.addAll(tset);
        logger.debug("Stack : {}", stack);
        
        while(!stack.empty())
            System.out.println(stack.pop());
    }
   
    public static void test2() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        
        logger.debug("size : {} / capacity : {}", list.size(), ((Vector<Integer>) list).capacity());
    }
    
    @SuppressWarnings("rawtypes")
    static class Student implements Comparable {
        String name;
        int ban;
        int no;
        int kor;
        int eng;
        int math;
        
        Student(String name, int ban, int no, int kor, int eng, int math) {
            this.name = name;
            this.ban = ban;
            this.no = no;
            this.kor = kor;
            this.eng = eng;
            this.math = math;
        }
        
        int getTotal() {
            return kor+eng+math;
        }
        
        float getAverage() {
            return (int)((getTotal()/ 3f)*10+0.5)/10f;
        }
        
        public String toString() {
            return name
                +","+ban
                +","+no
                +","+kor
                +","+eng
                +","+math
                +","+getTotal()
                +","+getAverage()
            ;
        }
        
        public int compareTo(Object o) {
            if(o instanceof Student) {
                Student tmp = (Student)o;
                return name.compareTo(tmp.name);
            } else {
                return -1;
            }
        }
    } // class Student
    
    static int getGroupCount(TreeSet<Student> tset, int from, int to) {
        // 참조형(Student) TreeSet이기때문에 Student 인스턴스를 만들어서 넣어줘야함
        return tset.subSet(new Student("",0,0,from,from,from), new Student("",0,0,to,to,to)).size();
    }
     
    /**
     * <pre>
     * [11-6] 다음의 코드는 성적평균의 범위별로 학생 수를 세기 위한 것이다.
     * TreeSet이 학생들의 평균을 기준으로 정렬하도록 compare(Object o1, Object o2)와 평균점수의 범위를 주면 해당 범위에 속한 학생의 수를 반환하는 getGroupCount()를 완성하라.
     * [Hint] TreeSet의 subSet(Object from, Object to) 를 사용하라.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.03.28
     */
    public static void exam06() {
       // TreeSet
       TreeSet<Student> set = new TreeSet<>(new Comparator<Student>() {
           public int compare(Student s1, Student s2) {
               return (int) (s1.getAverage() - s2.getAverage());
           }
       });
        
       set.add(new Student("홍길동",1,1,100,100,100)); 
       set.add(new Student("남궁성",1,2,90,70,80)); 
       set.add(new Student("김자바",1,3,80,80,90)); 
       set.add(new Student("이자바",1,4,70,90,70)); 
       set.add(new Student("안자바",1,5,60,100,80));
       
       Iterator<Student> it = set.iterator();
       
       // 안자바 평균점수가 남궁성과 80점으로 동일하여 compare 결과 중복으로 판정되어 4개만 출력됨
       while (it.hasNext()) {
           System.out.println(it.next());
       }

       System.out.println("[60~69] : " + getGroupCount(set,60,70));
       System.out.println("[70~79] : " + getGroupCount(set,70,80));
       System.out.println("[80~89] : " + getGroupCount(set,80,90)); 
       System.out.println("[90~100] : " + getGroupCount(set,90,101));
    }
    
    
    public static void main(String[] args) {
//         test();
        exam06();
    }
    
    static class TestStudent {
        private String name;
        private int total;
     
        public TestStudent(String name, int total) {
            this.name = name;
            this.total = total;
        }
        
        public int getTotal() {
            return total;
        }
     
        @Override
        public String toString() {
            return String.format("%s,%d", this.name, this.total);
        }
    }
    
    public static void test() {
       TreeSet<TestStudent> set = new TreeSet<>(new Comparator<TestStudent>() {
           public int compare(TestStudent o1, TestStudent o2) {
               return Integer.compare(o1.getTotal(), o2.getTotal());
           }
       });

       set.add(new TestStudent("테스트1", 100)); 
       set.add(new TestStudent("테스트2", 90)); 
       set.add(new TestStudent("테스트3", 80)); 
       set.add(new TestStudent("테스트4", 70)); 
       set.add(new TestStudent("테스트5", 60));
       
       Iterator<TestStudent> it = set.iterator();
       
       while (it.hasNext()) {
           System.out.println(it.next());
       }
       
       /**
        *  참조형(TestStudent) TreeSet이기때문에 타입 오류 발생함
        */
       // System.out.println("[subSet] :"+set.subSet(60, 70)); // => 오류 발생
       System.out.println("[subSet] :"+set.subSet(new TestStudent("",60),new TestStudent("",70)));
    }
}