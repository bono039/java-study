package exam;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exam.Test.Apple;
import exam.Test.Card;
import exam.Test.Deck;
import exam.Test.FruitBox;
import exam.Test.Juicer;
import exam.Test.Card.Kind;
import exam.Test.Card.Number;

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
     * 
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
        
        // a. 대입된 타입이 같아야 함
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
    
    class Fruit {}
    class Apple extends Fruit {}
    class Grape extends Fruit {}
    class FruitBox<T> extends Fruit {}
    
    static class Juicer {
        // 제네릭 메서드
        static <T extends Fruit> String makeJuice(FruitBox<T> box) {
           return "";
        }
    }
    
    /**
     * <pre>
     * 지네릭 메서드 makeJuice()가 아래와 같이 정의되어 있을때, 이 메서드를 올바르게 호출한 문장을 모두 고르시오.
     * (Apple과 Grape는 Fruit의 자손이라고 가정하자.)
     * 
     * 정답 : c,d 
     * </pre>
     *
     * @author : pej 
     * @date : 2023.01.25 
     */
    public void exam02() {
        FruitBox<Apple> appleBox = new FruitBox<Apple>();
        Juicer.makeJuice(appleBox); // == Juicer.<Apple>makeJuice(appleBox);
        
        FruitBox<? extends Fruit> box = new FruitBox<Apple>();
        Juicer.makeJuice(box);
        
        // a. 서로 다른 타입으로 에러 발생
        // Juicer.<Apple>makeJuice(new FruitBox<Fruit>());
        // => Juicer.<Fruit>makeJuice(new FruitBox<Fruit>());
        
        // b. 서로 다른 타입으로 에러 발생
        // Juicer.<Fruit>makeJuice(new FruitBox<Grape>());
        // => Juicer.<Grape>makeJuice(new FruitBox<Grape>());
         
        // c. 
        Juicer.<Fruit>makeJuice(new FruitBox<Fruit>());
        
        // d. 
        Juicer.makeJuice(new FruitBox<Apple>());
        
        // e. Object가 Fruit 클래스의 하위 클래스가 아니므로 에러 발생
        // Juicer.makeJuice(new FruitBox<Object>());
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
    @SuppressWarnings({"unused", "hiding", "rawtypes"})
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
        
        /**
         * Q1. 이미 제네릭 메서드가 아닌가?
         */
        /*
        public static ArrayList<? extends Product> merge(ArrayList<? extends Product> list, ArrayList<? extends Product> list2) {
            ArrayList<? extends Product> newList = new ArrayList<>(list);
            
            newList.addAll(list2);
            
            return newList;
        }
        */
        /*
        public static ArrayList<T extends Product> merge(ArrayList<T> list, ArrayList<T> list2) {
            ArrayList<T> newList = new ArrayList<>(list);
            
            newList.addAll(list2);
            
            return newList;
        }
        */
    }
    
    // Card클래스 
    public static class Card {
        enum Kind { CLOVER, HEART, DIAMOND, SPADE }
        enum Number {
            ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
        }

        Kind kind; 
        Number num;

        Card() {
            this(Kind.SPADE, Number.ACE);
        }

        Card (Kind kind, Number num) { 
            this.kind = kind; 
            this.num = num;
        }

        public String toString() {
            return "[" + kind.name() + "," + num.name() + "]";
        } // toString()의 끝
    }
    
    public static class Deck {
        final int CARD_NUM = Card.Kind.values().length * Card.Number.values().length; // 카드의 개수
        
        Card cardArr[] = new Card[CARD_NUM]; // Card객체 배열을 포함

        Deck () {
            /*
            (1) 알맞은 코드를 넣어서 완성하시오. Deck의 카드를 초기화한다.
            */
            for (int i=0; i<cardArr.length; i++) {
                Card card = new Card();
                cardArr[i] = card; 
            }
        }

        Card pick(int index) {  // 지정된 위치(index)에 있는 카드 하나를 꺼내서 반환
            return cardArr[index];
        }
        
        Card pick() {   // Deck에서 카드 하나를 선택한다.
            int index = (int) (Math.random() * CARD_NUM);
            return pick(index);
        }

        void shuffle() { // 카드의 순서를 섞는다.
            for(int i=0; i < cardArr.length; i++) {
                int r = (int)(Math.random() * CARD_NUM);
                Card temp = cardArr[i]; 
                cardArr[i] = cardArr[r]; 
                cardArr[r] = temp;
            }
        }
    } // Deck클래스의 끝
    
    /**
     * <pre>
     * 아래는 예제7-3에 열거형 Kind와 Number를 새로 정의하여 적용한 것이다.
     * (1)에 알맞은 코드를 넣어 예제를 완성하시오. 
     * (Math.random()을 사용했으므로 실행결과가 달라 질 수 있다.)
     * </pre>
     *
     * @author : pej 
     * @date : 2023.01.26
     */
    public static void exam05() {
        Deck d = new Deck();    // 카드 한 벌(Deck)을 만든다. 
        Card c = d.pick(0);     // 섞기 전에 제일 위의 카드를 뽑는다.
        System.out.println(c);  // System.out.println(c.toString());과 같다.

        d.shuffle();    // 카드를 섞는다.
        c = d.pick(0);  // 섞은 후에 제일 위의 카드를 뽑는다.
        System.out.println(c);
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
        exam05();
    }
}
