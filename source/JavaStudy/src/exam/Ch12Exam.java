package exam;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 챕터12 문제
 *  
 * @author : pej 
 * @date : 2023.01.25
 */
public class Ch12Exam {
    private static Logger logger = LogManager.getLogger(Ch12Exam.class);
    
    /**
     * <pre>
     * 클래스 Box가 다음과 같이 정의되어 있을때, 다음 중 오류가 발생하는 문장은?
     * 정답 : a, b, c
     * </pre>
     *
     * @author : pej 
     * @date : 2023.01.25 
     */
    public void exam01( ) {
        class Box<T> {
            T item;
            
            void setItem(T item) {
                this.item = item;
            }
            
            T getItem() {
                return item;
            }
        }
        
        // a. 서로 다른 타입 매개변수로 할 수 없음
        // Box<Object> b = new Box<String>();
        // => Box<Object> b = new Box<>(); 
        
        // b. 형변환이 잘못되었음
        // Box<Object> b = (Object) new Box<String>();
        // => Box<Object> b = new Box<Object>();
        
        // c. 타입 매개변수를 String 타입으로 했기 때문에 오류 발생
        // new Box<String>().setItem(new Object());
        // => new Box<String>().setItem(new String());
        
        // d.타입 매개변수에 맞게 값을 입력함
        new Box<String>().setItem("ABC");
        
    }
    
    
    
    /**
     * <pre>
     * 지네릭 메서드 makeJuice()가 아래와 같이 정의되어 있을때, 이 메서드를 올바르게 호출한 문장을 모두 고르시오.
     * (Apple과 Grape는 Fruit의 자손이라고 가정하자.)
     * </pre>
     *
     * @author : pej 
     * @date : 2023.01.25 
     */
    public void exam02() {
        class Fruit {}
        class Apple extends Fruit {}
        class Grape extends Fruit {}
        class FruitBox<T> extends Fruit {
            FruitBox<T> list;
            
            FruitBox<T> getList() {
                return list;
            }}
        
         class Juicer {
             <T extends Fruit> String makeJuice(FruitBox<T> box) {
                String tmp = "";
                for (Fruit f : box.getList()) {
                    tmp += f + " ";
                }
                return tmp;
            }
        }
        
        
    }
    
    /**
     * <pre>
     * 다음 중 올바르지 않은 문장을 모두 고르시오.
     * 정답 : c,d,g
     * </pre>
     *
     * @author : pej 
     * @date : 2023.01.25 
     */
    @SuppressWarnings("unused")
    public void exam03() {
        class Fruit {}
        class Apple extends Fruit {}
        class Box<T extends Fruit> {
            T item;
            
            void setItem(T item) {
                this.item = item;
            }
            
            T getItem() {
                return item;
            }
        }
        
        // a.에러 없음
        // Box<?> a = new Box();
        // Box<?> a = new Box<>();
        
        /**
         * Q1. 이건 에러나는  차이가 뭔지?
         */
        // Box<String> b2 = new Box();
        
        // b.
        Box<?> b = new Box<>();
        
        // c. 타입 매개변수가 다름
        // Box<?> c = new Box<Object>();
        // => Box<?> c = new Box<>();
        Box<?> c1 = new Box<Fruit>();
        Box<?> c2 = new Box<>();
        
        // d. 타입 매개변수가 다름
        // Box<Object> d = new Box<Fruit>();
        // => Box<Fruit> d = new Box<Fruit>();
        
        // e. 에러없음
        Box e = new Box<Fruit>();
        
        // f. Apple 클래스와 Fruit 클래스가 상속관계라면 에러 없음
        Box<? extends Fruit> f = new Box<Apple>();
        
        // g. 에러 발생
        // 이유 : new 연산자는 타입이 명확해야 하므로 와일드카드 사용 못함
        // Box<? extends Object> g = new Box<? extends Fruit>();
         Box<? extends Object> g = new Box<>();
    }
    
    /**
     * <pre>
     * 아래의 메서드는 두 개의 ArrayList를 매개변수로 받아서, 하나의 새로운 ArrayList로 병합하는 메서드이다.
     * 이를 제네릭 메서드로 변경하시오.
     * </pre>
     *
     * @author : pej 
     * @date : 2023.01.25 
     */
    public void exam04() {
        class Product {}
        /*
        public static ArrayList<? extends Product> merge(
                ArrayList<? extends Product> list, ArrayList<? extends Product> list2) {
            ArrayList<? extends Product> newList = new ArrayList<>(list);
            
            newList.addAll(list2);
            
            return newList;
        }
        */
        
        public static ArrayList<? extends Product> merge(ArrayList<? extends Product> list, ArrayList<? extends Product> list2) {
            ArrayList<? extends Product> newList = new ArrayList<>(list);
            
            newList.addAll(list2);
            
            return newList;
        }
    }
    
    /**
     * <pre>
     * 어노테이션 TestInfo가 올바르게 적용되지 않은 것은?
     * 정답 : b, d
     * </pre>
     *
     * @author : pej 
     * @date : 2023.01.25 
     */
    @SuppressWarnings("unused")
    public void exam07() {
        @TestInfo
        int a = 0;
        
        // 요소 이름이 'value'가 아니면 이름을 생략 할 수 없음
        // @TestInfo(1)
        @TestInfo(count = 1)
        int b = 0;
        
        @TestInfo("bbb")
        int c = 0;
        
        // @TestInfo("bbb","ccc")
        @TestInfo({"bbb","ccc"})
        int d = 0;
    }
    
    public static void main(String[] args) {
        
    }
}
