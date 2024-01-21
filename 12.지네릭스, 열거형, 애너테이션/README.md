| created    | title           | author         | category |
|------------|-----------------|-----------------|----------|
| 2024-01-18 | 지네릭스, 열거형, 애너테이션 | 한의정  | JAVA     |

<br/>

## [1] 지네릭스
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

<br/>

<b>지네릭 타입의 형 변환</b> &nbsp;/*복습하기*/
<br/>
- 지네릭 타입 - 원시 타입 간 형 변환 OK. but 경고 뜸
- 와일드 카드가 사용된 지네릭 타입끼리도 형 변환 가능한 경우 있음 

<br/>

<b>지네릭 타입 제거</b>

<quote>컴파일러, 지네릭 타입 이용해 소스 파일 체크하고, 필요한 곳에 형 변환 넣은 후, 지네릭 타입 제거</quote>

1. 지네릭 타입의 경계(bound)를 제거
2. 지네릭 타입 제거 후, 타입이 일치하지 않으면 형 변환 추가

<br/>

## [2] 열거형 (enums)
<quote>열거형이 갖는 값뿐만 아니라 타입까지 관리 (typesafe enum)</quote>
```
class Card {
	enum Kind  {CLOVER, HEART, DIAMOND, SPADE}	// 열거형 Kind 정의
	enum Value {TWO, TRHEE, FOUR}				// 열거형 Value 정의

	final Kind  kind;	// 타입이 int가 아닌 Kind
	final Value value;
}

if(Card.CLOVER == Card.TWO)				// true지만 false어야 의미 상 맞음
if(Card.Kind.CLOVER == Card.Value.TWO)	// false. 값은 같지만 타입이 다름
```
<br/>

- 정의 : `enum 열거형이름 { 상수명1, 상수명2, ... } `
- 사용 : `열거형이름.상수명`
- 예제
	```
	enum Direction {EAST, SOUTH, WEST, NORTH}

	class Unit {
		int x, y;		// 유닛 위치
		Direction dir;	// 열거형을 인스턴스 변수로 선언
	}

	void init() {
		dir = Direction.EAST;	// 유닛 방향을 EAST로 초기화
	}
	```
- 열거형 상수 간 비교 → <code>==</code> / <,> 비교 시 → <code>compareTo()</code> 사용
- 멤버 추가하기
	1. 열거형 상수 모두 정의하기 : 열거형 상수 이름 옆에 원하는 값을 괄호()와 함께 적기
	2. 지정된 값을 저장할 수 있는 인스턴스 변수와 생성자 새로 추가하기
		```
		enum Direction {
			 EAST(1), SOUTH(5), WEST(-1), NORTH(10);

			 private final int value;	// 정수 저장할 필드(인스턴스 변수) 추가
			 Direction(int value) { this.value = value; }	// 생성자 추가 (묵시적으로 접근 제어자는 private)

			 public int getValue() { return value; }
		}
		```




<br/>

#### 모든 열거형의 조상 java.lang.Enum
| 메소드 | 설명                                  |
|:-------|:-------------------------------------------|
| static E values() | 열거형의 모든 상수를 배열에 담아 반환  |
| static E valueOf(String name) | 전달된 문자열과 일치하는 열거형 상수 반환  |
| protected void finalize() | 해당 Enum 클래스가 final 메소드를 가질 수 없게 됨.  |
| String name()   | 열거형 상수의 이름을 문자열로 반환  |
| int ordinal()   | 열거형 상수가 정의된 순서 반환 (0부터 시작) |

<br/>

## [3] 애너테이션 (annotation)
<quote>프로그램의 소스코드 안에 다른 프로그램을 위한 정보를 미리 약속된 형식으로 포함시킨 것</quote>
- 테스트 프로그램에 알리는 역할을 할 뿐, 프로그램 자체에는 아무 영향 X (≒ 주석)

<br/>

#### 표준 애너테이션
| 애너테이션 | 설명                                  |
|:-------|:-------------------------------------------|
| @Override | 컴파일러에 오버라이딩하는 메소드라는 것을 알림  |
| @Deprecated | 앞으로 사용하지 않을 것을 권장하는 대상에 붙임  |
| @SuppressWarnings | 컴파일러의 특정 경고 메시지가 나타나지 않게 함 <br/> → deprecation, unchecked, rawtypes, varargs |
| @SafeVarags   | 지네릭스 타입의 가변인자에 사용  |
| @FunctionalInterface   | 함수형 인터페이스임을 알림 |
| @Native   | native 메소드에서 참조되는 상수 앞에 붙임 |

<br/>
<b>@FunctionalInterface</b>

- 함수형 인터페이스 올바르게 선언했는지 확인하고, 잘못된 경우 에러 발생 
```
@FunctionalInterface
public interface Runnable {
	public abstract void run();	// 추상 메소드
}
```

<br/>

<b>@SuppressWarnings</b>

- `deprecation` :  @Deprecated가 붙은 대상을 사용해 발생하는 경고
- `unchecked`   : 지네릭스로 타입을 지정하지 않아 발생하는 경고
- `rawtypes` : 지네릭스를 사용하지 않아 발생하는 경고
- `varargs` : 가변인자의 타입이 지네릭 타입일 때 발생하는 경고
```
@SuppressWarnings("unchecked")		// 지네릭스 관련 경고 억제
ArrayList list = new ArrayList();	// 지네릭 타입 지정 안 함
list.add(obj);						// 여기서 경고 발생

// 둘 이상의 경고 억제 시
@SuppressWarnings({"deprecation", "unchecked", "varargs"})
```

<br/>

<b>@SafeVarargs</b>

- static이나 final이 붙은 메소드에만 사용 가능 (= 오버라이드될 수 있는 메소드에서는 사용 불가)
- 메소드에 선언한 가변인자의 타입이 컴파일 시 제거되는 <b>non-refiable 타입</b>인 경우, 메소드를 선언하는 부분과 호출하는 부분에 <code>"unchecked"</code> 경고 발생
- 보통 @SafeVarargs와 @SuppressWarnings("varargs") 두 애너테이션 같이 사용

<br/>

(~p.710)

<br/>

---
### 🔗 출처 및 참고 자료
- Java의 정석
- [TCP SCHOOL](https://tcpschool.com/java/java_api_enum)





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
