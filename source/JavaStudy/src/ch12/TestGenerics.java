package ch12;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Generics 학습
 *  
 * @author : pej 
 * @date : 2023.01.21
 */
public class TestGenerics {
    private static Logger logger = LogManager.getLogger(TestGenerics.class);
    
    public static void test1() {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        /**
         * 에러 발생 : The method add(int, String) in the type ArrayList<String> is not applicable for the arguments (int)
         */
        // list.add(1); 
        
        // 코드 호환을 위해 이렇게 사용해도 되나 warning 발생함
        /**
         * warning 발생 : ArrayList is a raw type. References to generic type ArrayList<E> should be parameterized
         */
        ArrayList list2 = new ArrayList();
        list2.add("1");
        list2.add(1);
    }
    
    public static void main(String[] args) {
       
    }
}