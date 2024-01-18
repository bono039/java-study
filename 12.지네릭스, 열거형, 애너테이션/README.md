| created    | title           | author         | category |
|------------|-----------------|-----------------|----------|
| 2024-01-18 | 지네릭스, 열거형, 애너테이 | 한의정  | JAVA     |

<br/>

## 지네릭스
    다양한 타입의 객체들을 다루는 메소드나 컬렉션 클래스에 컴파일 시의 타입을 체크 해주는 기능 (compile-time type check)
	= "다룰 객체 타입을 미리 명시함으로써 형 변환 줄임"
- 장점
  1. 객체 타입 안전성 제공
  2. 형 변환 번거로움 낮춤 & 코드 간결해짐

- 용어

      class Box<T> {}
	    → Box<T> : 지네릭 클래스
		→ T      : 타입 변수 / 타입 매개변수
		→ Box    : 원시타입 

- 제한
  1. 모든 객체에 대해 동일하게 동작해야 하는 <b>static 멤버에 타입 변수 T를 사용할 수 없다</b>. <br/>(∵ 인스턴스 변수로 간주되기 때문)
     ```
	 class Box<T> {
		static T item;  // X
		static int compare(T t1, T t2) { ... } // X
	 }
	 ```

  2. 지네릭 타입 배열 생성 NO
     ```
	 class Box<T> {
		T[] itemArr;  // OK. T 타입 배열 위한 참조변수
		
		T[] toArray() {
			T[] tmpArr = new T[itemArr.length];	// X
			...
			return tmpArr;
		}
	 }
	 ```

<br/>

### 지네릭 클래스 선언
#### 1. <b>클래스</b>에 선언
```
class Box<T> {	// 지네릭 타입 T 선언
	T item;

	void setItem(T item) {this.item = item; }
	T getItem() {return item;}
}
```

→ T/E : 타입 변수, <b>임의의 참조형 타입</b> 의미

<br/>

<b>지네릭 클래스의 객체 생성과 사용</b>
<br/>
: 참조변수와 생성자에 대입된 타입(매개변수화된 타입)이 일치해야 한다.

```
Box<Apple> appleBox = new Box<Apple>();	// O
Box<Apple> appleBox = new Box<Grape>();	// X
```

<br/>

<b>제한된 지네릭 클래스</b>
<br/>
: 지네릭 타입에 <code>extends</code> 사용하면, 특정 타입 자손들만 대입할 수 있게 제한 가능<br/>
(인터페이스 구현해야 하는 제약 만들 때도 <code>implements</code> 말고 <code>extends</code> 사용)

```
class FruitBox<T extends Fruit> {	// Fruit의 자손만 타입으로 지정 가능
	ArrayList<T> list = new ArrayList<T>();
	...
}

class FruitBox<T extends Fruit & Eatble> {...}	// Fruit의 자손이면서 인터페이스 구현도 동시에
```

<br/>

<b>와일드 카드</b>
<br/>
: 어떠한 타입도 될 수 있다. (상한 : <code>extends</code> / 하한 : <code>super</code>)

| 와일드카드 | 의미                                  |
|:-------|:-------------------------------------------|
| <? extends T> | 와일드카드의 상한 제한. T와 그 자손들만 가능  |
| <? super T>   | 와일드카드의 하한 제한. T와 그 조상들만 가능  |
| <?>   | 제한 없음. 모든 타입 가능 (= <? extends Object>) |

<br/>            

```
class Juicer {
	static Juice makeJuice(FruitBox<? extends Fruit> box) {
		String tmp = "";
		for(Fruit f : box.getList())	tmp += f + " ";
		return new Juice(tmp);
	}
}
```
→ 매개변수로 <code>FruitBox<Fruit></code>뿐만 아니라, <code>FruitBox<Apple></code>과 <code>FruitBox<Grape></code>도 가능하게 된다. <br/>

- <code>Collections.sort()</code> 메소드 선언부 : <code> static<T> void sort(List<T> list, Comparator<? super T> c)</code>
	```
	class FruitComp implements Comparator<Fruit> {
		public int compare(Fruit t1, Fruit t2) {
			return t1.weight - t2.weight;
		}
	}

	...
	// List<Apple>을 Comparator<Fruit>로 정렬
	Collections.sort(appleBox.getList(), new FruitComp());
	```

<br/>


#### 2. 메소드에 선언 = 지네릭 메소드
<quote> - 메소드 선언부에 지네릭 타입이 선언된 메소드 <br/> - 선언 위치 : 반환 타입 바로 앞</quote>

```
static <T> void sort(List<T> list, Comparator<? super T> c)
```

- static 멤버에는 타입 매개 변수를 사용할 수 없으나, 메소드에 지네릭 타입 선언하고 사용하는 것은 OK
- 앞에 나온 makeJuice()를 지네릭 메소드로 바꾸고 호출하는 예제
	```
	static <T extends Fruit> Juice makeJuice(FruitBox<T> box) {
		String tmp = "";
		for(Fruit f : box.getList())	tmp += f + " ";
		return new Juice(tmp);
	}

	FruitBox<Fruit> fruitBox = new FruitBox<Fruit>();
	FruitBox<Apple> fruitBox = new FruitBox<Apple>();

	System.out.println(Juicer.<Fruit>makeJuice(fruitBox));
	System.out.println(Juicer.makeJuice(appleBox));	// 타입 변수에 대입된 타입 생략 가능
	```

- 단, 대입된 타입을 생략할 수 없는 경우, 참조변수나 클래스명 생략 불가

(~p.686)


<br/>



### 

<br/>



<br/>

---
### 🔗 출처 및 참고 자료
- Java의 정석
- [TCP SCHOOL](https://www.tcpschool.com/java/java_collectionFramework_concept)





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
