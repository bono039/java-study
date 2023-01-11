package exam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.TreeSet;
/**
 * <pre>
 * [11-2] 다음 코드의 실행결과를 적으시오.
 * </pre>
 * 
 * @author : pej
 * @date : 2023.01.11
 */
public class Exercise11_2 {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {
        ArrayList list = new ArrayList();
        list.add(3);
        list.add(6);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(7);
        
        HashSet set = new HashSet(list);
        TreeSet tset = new TreeSet(set);
        Stack stack = new Stack();
        stack.addAll(tset);
        
        while (!stack.empty())
            System.out.println(stack.pop());
    }
}
