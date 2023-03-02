package ch14;

import java.util.function.Consumer;

public class TestEffectiveFinal {
    public static void main(String[] args) {
        // effective final
        String name = "test";
        
        /**
         * name 필드가 변경되면 지역 클래스, 익명클래스, 람다에서 참조 할 수 없음
         * Local variable name defined in an enclosing scope must be final or effectively final
         */
        // name = "1";

        // 지역 클래스
        class LocalClass {
            void printLocal() {
                String name = "local";
                System.out.println("LocalClass => " + name);
            }
        }
        
        // 익명 클래스
        Consumer<String> anoymousClass = new Consumer<String>() {
            @Override
            public void accept(String s) {
                String name = "anoymous";
                System.out.println("anoymousClass => " + name);
            }
        };
        
        // 람다식
        Consumer<String> lambda = (str) -> {
            /**
             * 에러 발생 
             * name 필드와 같은 scope여서 동일한 이름으로 할 수 없음
             */
            // String name = "lambda";
            System.out.println("lambda => " + name);
        };
        
        // 출력
        LocalClass local = new LocalClass();
        local.printLocal();
        
        anoymousClass.accept(name);
        lambda.accept(name);
    }
}
