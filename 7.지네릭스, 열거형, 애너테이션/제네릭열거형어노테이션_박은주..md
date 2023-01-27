---
created: 2023-01-17
title: 자바스터디_12_지네릭스_열거형_애너테이션
author: pej
category: study
tag: study
aliases: [자바스터디_12_지네릭스_열거형_애너테이션]
---

#### 책 목차
- 제네릭(Generics)
- 열거형(Enum)
- 어노테이션

#### 학습할 것
- 제네릭 사용법 및 주요 개념
- 제네릭 메소드 만들기
- 열거형을 정의하는 방법
- 어노테이션을 정의하는 방법

#### 학습 내용
1. 제네릭(Generics)
	+ **클래스나 메소드에서 컴파일시의 타입 체크 기능**
	+ **다른 타입으로 저장되는 것을 방지하며 원래의 타입과 다른 타입으로 형변환 되어 발생 할 수 있는 오류를 줄여줌**
	+ JDK 1.5에서 도입되었으며 한 가지 타입만 정해서 저장 할 수 있음
	+ 컴파일시 **하위 버전과의 호환성 때문에** 컴파일러가 제네릭 타입이 맞는지 소스를 체크하고 필요한 부분에 형변환을 넣어주고 제네릭 타입을 제거함 그래서 **클래스 파일에는 제네릭 타입이 없음**
	```
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
	```
	+ 제네릭의 제한
		+ static 멤버변수, static 메소드, 배열을 생성 할 수 없음
			+ 매개변수화된 타입은 인스턴스 변수로 간주되기 때문에
			+ new 연산자로 생성시 정확한 타입을 알아야 하는데 컴파일시점에서는 T가 어떤 타입일지 알 수가 없음
		```
		public class Box<T> {
		    // 에러 발생 : Cannot make a static reference to the non-static type T
		    static T str;
		    static T test() {
		        // 에러 발생 : Variable must provide either dimension expressions or an array initializer
		        T[] tmpArr = new T[];
		    }
		}
		```
	+ 제네릭 주요 개념
		+ **바운드 타입 매개변수(Bounded Type Parameters)**
			+ extends 키워드를 이용하여 **타입의 제한을 주고자 할 때** 사용함
			 ```
			class Apple {}
			
			Box<T extnds Apple>
			```
		+ **Multiple Bounds**
			+ 여러개로 타입의 제한을 주고자 할 때 사용함(인터페이스도 마찬가지)
			```
		   interface Eatable() {}
		   class Apple {}
		   class Grape {}
		   
		   Box<T extnds Apple & Grape & Eatable>
		   ```
		+ **와일드 카드**
			+ 제네릭 타입이 다른 것만으로는 재정의(오버로딩)이 성립하지 않기 때문에 와일드 카드가 고안되었으며 **어떠한 타입도 들어 갈 수 있음**
			+ Object 타입과 다른 점이 없기 때문에 extends, super로 제한 할 수 있음
			+ &를 사용 할 수 없음
		```
		<? extends T> : T와 그 자손들만 가능 
		<? super T> : T와 그 조상들만 가능 
		<?> : 모든 타입 가능 <? extends Object>와 동일
		```
	+ 제네릭 메서드
		+ 메서드 선언부에 **제네릭 타입으로 선언된 메서드**를 의미
		+ **선언된 제네릭 타입은 지역변수와 같으며 메서드 내에서만 사용됨**
		+ 대입된 타입을 생략 할 수 없는 경우에는 참조변수나 클래스명을 생략 할 수 없음
		```
		public static <T extends Fruit> String makeJuice(FruitBox<T> box) {
           return "";
        }
	    FruitBox<Apple> appleBox = new FruitBox<Apple>();
        Juicer.makeJuice(appleBox); // == Juicer.<Apple>makeJuice(appleBox);
        ```
     + 제네릭의 형변환
	     + 제네릭 타입과 넌제네릭 타입간의 형변환은 가능
	     ```
	     Box<? extends Object> box = new Box<String>();
		```
		
	 > 용어 정리
     > ```
     > ArrayList<String> list = new ArrayList<>();
     > ```
     > + ArrayList : 원시 타입
     > + String : 타입 매개변수 또는 매개변수화된 타입(Parameterized Type)
     > + ArrayList<String> : 매개변수화된 타입(Parameterized Type)

2. 열거형(Enums)
	+ **변수가 상수 집합이 되도록 하는 특수한 데이터 유형**
	+ 값뿐만 아니라 타입까지 체크함(**typesafe enum**)
	+ 상수값이 바뀌면 해당 상수를 참조하는 모든 소스를 다시 컴파일해야 하지만 열거형 상수를 이용하면 기존의 소스를 다시 컴파일하지 않아도 됨
	+ 열거형 상수는 '=='으로 비교 할 수 있음
	+ static 변수를 사용하는 것과 동일하게 **열거형.상수명**으로 호출하면 됨
	```
	public class TestEnum {
	    private static Logger logger = LogManager.getLogger(TestEnum.class);
	    
	    public static void main(String[] args) {
	       DayEnum[] str = DayEnum.values();
	       
	       for (DayEnum item : str) {
	           logger.debug("item() : {}", item);
	       }
	       
	       logger.debug("DayEnum.EVENING == DayEnum.MORNING : {}", DayEnum.EVENING == DayEnum.MORNING);
	       logger.debug("DayEnum.MORNING == DayEnum.MORNING : {}", DayEnum.MORNING == DayEnum.MORNING);
	    }
	}
	[실행결과]
	item() : MORNING
	item() : EVENING
	item() : AFTERNNON
	DayEnum.EVENING == DayEnum.MORNING : false
	DayEnum.MORNING == DayEnum.MORNING : true
	```
   
    > java.lang.Enum
    > + 모든 열거형의 조상
    > + values(), valueOf() 메서드는 컴파일러가 자동으로 추가해주며 모든 열거형이 가지고 있음


3. 어노테이션(Annotation)
	+ **주석처럼 프로그램에는 영향을 주지 않으며 프로그램에 유용한 정보를 제공할 수 있음**
	+ 표준 어노테이션
		+ 자바에서 기본적으로 제공하는 어노테이션으로 컴파일러시 오류 및 경고를 알려줄 수 있음
		+ 예) @Override, @Deprecated, @FunctionalInterface
	+ 메타 어노테이션
		+ 어노테이션을 위한 어노테이션
		+ 적용대상이나 유지기간을 지정할 때 사용함
		+ java.lang.annotation에 정의되어 있음

	![메타어노테이션](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FP4DU6%2FbtqVbvseXnM%2F5HG4KWreIQOWKJFIZkiFBK%2Fimg.png)

	| 어노테이션 | 대상 타입       | 의미                                                     |
	|:---------- |:--------------- |:-------------------------------------------------------- |
	| @Target    | ANNOTATION_TYPE | 어노테이션                                               |
	| @Target    | CONSTRUCTOR     | 생성자                                                   |
	| @Target    | FIELD           | 기본형 타입 필드(멤버변수, enum상수)                     |
	| @Target    | LOCAL_VARIABLE  | 지역변수                                                 |
	| @Target    | METHOD          | 메서드                                                   |
	| @Target    | PACKAGE         | 패키지                                                   |
	| @Target    | PARAMETER       | 매개변수                                                 |
	| @Target    | TYPE            | 클래스의 모든 요소(클래스, 인터페이스, enum, 어노테이션) |
	| @Target    | TYPE_PARAMETER  | 타입 매개변수(JDK 1.8)                                   |
	| @Target    | TYPE_USE        | 참조형 타입(JDK 1.8)                                     |
	| @Retention | SOURCE          | 소스 파일에만 존재, 클래스 파일에는 존재하지 않음        |
	| @Retention | CLASS           | 클래스 파일에 존재, 실행시에는 사용불가(기본값)          |
	| @Retention | RUNTIME         | 클래스 파일에 존재, 실행시에도 사용가능                                                          |
	
	+ 좀 더 자세한 내용은 [URL 참조](https://docs.oracle.com/javase/tutorial/java/annotations/predefined.html)

	```
	@Documented
	@Retention(CLASS)
	@Target({ FIELD, TYPE, TYPE_USE })
	public @interface LottoAnnotation {
	    String author() default "pej";
	    int order() default 1;
	    String[] testArr(); // => 기본값이 없으므로 반드시 있어야함
	    TestType testType() default TestType.EVENING;
	    
	    public enum TestType {
	        MORNING, AFTERNNON, EVENING;
	    }
	}
	@LottoAnnotation(testArr = { "class" })
	public class TestAnnotation {
	    @LottoAnnotation(author="test1", order = 2, testType = TestType.MORNING, testArr = { "filed" })
	    int num = 0;
	    
	    @LottoAnnotation(testArr = { "method" })
	    public String test() {
	        return "test";
	    }
	}
	```
	+ 어노테이션 규칙
		+ 기본형, String, enum, 어노테이션, Class만 허용됨
		+ 상수 선언 가능
		+ 예외, 제네릭 타입을 선언 할 수 없음
		+ ()안에 매개변수를 선언 할 수 없음
		+ **요소 이름 `value`이면 이름을 생략하고 값을 넣을 수 있음**
		```
		public @interface LottoAnnotation {
			String value();
		}
		@LottoAnnotation("test")
		public class TestAnnotation { }
		```

	> java.lang.annotation.Annotation
	> + 모든 어노테이션의 조상
	> + 어노테이이션은 명시적으로 상속이 허용되지 않으므로 묵시적으로 상속 받음
	
	> 마커 어노테이션(Marker Annotation)
	> + 요소가 하나도 없는 어노테이션
	> + 예) Serializable, Cloneable
	
#### 출처(참고문헌)
- Java의 정석
- https://docs.oracle.com/javase/tutorial/java/generics/index.html
- https://docs.oracle.com/javase/tutorial/java/annotations/predefined.html

#### 연결문서
- 자바스터디_11_컬렉션프레임워크

#### 각주
