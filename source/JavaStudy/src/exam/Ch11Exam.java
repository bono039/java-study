package exam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ch11Exam {
    private static Logger logger = LogManager.getLogger(Ch11Exam.class);
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void test() {
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
//                return name.compareTo(tmp.name);
                if (this.getAverage() > tmp.getAverage()) {
                    return 1;
                } else if (this.getAverage() < tmp.getAverage()) {
                    return -1;
                } else {
                    return 0;
                }
            } else {
                return -1;
            }
        }
    } // class Student
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    static int getGroupCount(TreeSet tset, int from, int to) {
        TreeSet ts = (TreeSet) tset.subSet(from, to);
        return ts.size();
    }
    
    public static void main(String[] args) {
//      test();
      test2();
    }
    
//    public static void main(String[] args) {
//        // TreeSet
//        TreeSet set = new TreeSet(new Comparator() {
//            public int compare(Object o1, Object o2) {
//                Student tmp1 = (Student) o1;
//                Student tmp2 = (Student) o2;
//                return tmp1.compareTo(tmp2);
//                
////                Student s1 = (Student) o1;
////                Student s2 = (Student) o2;
////                return (Float.compare(s1.getAverage(), s2.getAverage()));
//            }
//        });
//        
//       set.add(new Student("홍길동",1,1,100,100,100)); 
//       set.add(new Student("남궁성",1,2,90,70,80)); 
//       set.add(new Student("김자바",1,3,80,80,90)); 
//       set.add(new Student("이자바",1,4,70,90,70)); 
//       set.add(new Student("안자바",1,5,60,100,80)); 
//        
//       Iterator it = set.iterator();
//        
//       while(it.hasNext()) {
//           System.out.println(it.next());
//       }
//
////       System.out.println("[60~69] :"+getGroupCount(set,60,70));
////       System.out.println("[70~79] :"+getGroupCount(set,70,80));
//       System.out.println(getGroupCount(set,80,90));
////       System.out.println("[90~100] :"+getGroupCount(set,90,101));
//    }
}