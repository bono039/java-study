---
created: 2023-02-12
title: 자바스터디_14_람다와스트림
author: pej
category: study
tag: study
---

#### 책 목차
- 람다식
- 스트림

#### 학습할 것
- 람다식 정의 및 사용하는 방법
- 함수형 인터페이스
- 스트림 정의 및 사용하는 방법

#### 학습 내용
1. 람다식(Lambda Exprssion)
	+ **메서드를 간단한 식으로 표현하는 방법**
	+ 메서드의 이름과 반환값이 없어지므로 익명 함수(Anonymous Function)라고 하지만 사실 익명 객체와 같음
	```
	// 일반
	int sum(int a, int b) {
		return a + b;
	}
	// 람다식
	(a, b) -> a + b
	```
	+ [메서드 참조(Method References)](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html)
		+ 하나의 메서드를 호출하는 경우에만 사용 할 수 있음
		+ static메서드인 경우 : **타입::static메서드**
		+ 임의 객체의 인스턴스 메서드인 경우 : **타입::인스턴스메서드**
		+ 특정 객체의 인스턴스 메서드인 경우 : **참조변수::인스턴스메서드**
		+ 생성자 메서드인 경우 : **타입::new**
		```
		// static메서드인 경우
		System.out::println
		// 특정 객체의 인스턴스 메서드인 경우
		Arrays.stream(stuArr).collect(Collectors.groupingBy(TestStudent::getHak))
		// 임의 객체의 인스턴스 메서드인 경우
		String::length
		// 생성자 메서드인 경우
		int[]::new
		```
	+ **외부 지역변수와 같은 영역(scope)이기 때문에 익명클래스와 다르게 외부 지역 변수의 값이 쉐도잉되지 않음**

> Shadowing
> + `If a declaration of a type in a particular scope has the same name as another declaration in the enclosing scope, then the declaration shadows the declaration of the enclosing scope`
> + **이름이 동일한 경우 외부 변수값을 내부에 선언된 변수값으로 덮음**
```
public static void main(String[] args) {
    String name = "test";

    // 지역 클래스
    class LocalClass {
        void printLocal() {
            System.out.println("LocalClass => " + name);
        }
    }
    
    // 익명 클래스
    Consumer<String> anoymousClass = new Consumer<String>() {
        @Override
        public void accept(String s) {
            System.out.println("anoymousClass => " + name);
        }
    };
    
    // 람다식
    Consumer<String> lambda = (str) -> {
        System.out.println("lambda => " + name);
    };
    
    // 출력
    LocalClass local = new LocalClass();
    local.printLocal();
    
    anoymousClass.accept(name);
    lambda.accept(name);
}
[실행결과]
LocalClass => test
anoymousClass => test
lambda => test
```

> Effective final
> + JDK 1.8에서 도입되었으며 `final` 키워드를 붙이지 않아도 값이 변경되지 않는다면 **사실상 final변수라고 간주함**

> 람다 캡쳐(Lambda Capture)
> + 람다식은 새로운 call stack을 만드는데, **필요한 지역 변수 캡쳐후 복사본을 만들어서 사용함**
> + 이때 필요한 지역 변수가 final이거나 effective final 이어야 하는데, 이러한 조건이 있는 이유는 변수에 대한 접근을 허용하는 것이 아니라 복사본을 만들기때문에 원본의 값이 바뀌면 안되기 때문임
```
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
[실행결과]
LocalClass => local
anoymousClass => anoymous
lambda => test
```
2. 함수형 인터페이스(Functional Interface)
	+ **한 개의 추상 메서드만 선언되어 있으며 람다식을 다루기 위한 인터페이스**
	+ 하나의 추상 메서드만 있어야 하는 이유는 람다식과 추상 메서드가 1:1로 연결되어야 하기 때문임
	+ **`@FunctionalInterface` 어노테이션으로 함수형 인터페이스인지 확인 할 수 있음**
	+ 인터페이스이기 때문에 static 메서드나 default 메서드 개수의 제약은 없음
	+ java.util.function 패키지에 자주 사용될만한 형식의 함수형 인터페이스가 정의되어 있음[^1]
	![함수형인터페이스_정리](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FC2R9G%2Fbtr0p7dKUfn%2Fo3u0jHvvGEKe8Xt33KPtNk%2Fimg.png)
3. 스트림(Stream)
	+ 특징
		+ 데이터를 읽기만 할뿐 변경하지 않음
		+ Iterator처럼 한번 사용하면 다시 사용 할 수 없음(일회용)
		+ 최종 연산전까지 중간연산이 수행되지 않음(지연된 연산)
		+ 작업을 병렬로 처리함
	+ 스트림의 제공 기능
		+ 중간 연산 : 연산 결과가 **스트림**
		+ 최종 연산 : 연산 결과가 Optional, 단 한 번만 가능
	+ 스트림 사용법
	```
	// 컬렉션인 경우
	List<Integer> list = Arrays.asList(1,2,3,4,5);
	Stream<Integer> stream = list.stream();
	
	// 배열인 경우
	String[] strArr = { "a", "b", "c", "d" };
	int[] intArr = { 1,2,3,4,5};
	double[] doubleArr = {1.1,2.1,3.1,4.1,5.1};
	
	// 방법1)
	Stream<String> stream = Arrays.stream(strArr);
	IntStream intStream = Arrays.stream(intArr);
	DoubleStream doubleStream = Arrays.stream(doubleArr);
	
	// 방법2)
	Stream<String> stream2 = Stream.of(strArr);
	Stream<int[]> intStream2 = Stream.of(intArr);
	Stream<double[]> doubleStream2 = Stream.of(doubleArr);
	```
	+ 기본형 스트림
		+ `IntStream` , `DoubleStream` , `LongStream`만 있음
		+ **오토박싱, 언박싱 안해도 되기 떄문에 더 효율적임**
		+ count(), sum()등 편리하게 제공해주는 함수도 있음
		```
		// 1) 기본형 스트림 이용
		int[] intArr = { 1, 2, 3, 4, 5 };
		IntStream intStream = Arrays.stream(intArr);
		// intStream.forEach(System.out::println);
		System.out.println("sum : " + intStream.sum());
		
		System.out.println("===============================");
		
		// 2) Stream 이용
		// Integer[] intArr2 = { new Integer(1), new Integer(2), new Integer(3), new Integer(4), new Integer(5) };
		Integer[] intArr2 = { 1, 2, 3, 4, 5 };
		Stream<Integer> intStream2 = Arrays.stream(intArr2);
		intStream2.forEach(System.out::println);
		
		[실행결과]
		===============================
		sum : 15
		===============================
		1
		2
		3
		4
		5
		```
	+ Optional
		+ JDK 1.8부터 추가된 제네릭 클래스
		+ null 에러를 해결 할 수 있는 방안을 제공함
		+ Optional 객체 생성 방법
			+ Optional.of() : 값 지정(null 허용하지 않음)
			+ Optional.empty() : 초기화
			+ Optional.ofNullable() : **null을 허용함**
			```
			String value = "test";
			Optional<String> opt = Optional.of(value);
			logger.debug("opt 실행결과 : {}", opt);  // 실행결과 : Optional[test]
			
			Optional<String> opt2 = Optional.of("aaa");
			logger.debug("opt2 실행결과 : {}", opt2);  // 실행결과 : Optional[aaa]
			
			// java.lang.NullPointerException 발생
			// Optional<String> opt3 = Optional.of(null); 
			Optional<String> opt3 = Optional.ofNullable(null);
			logger.debug("opt3 실행결과 : {}", opt3);  // 실행결과 : Optional.empty
			
			// null로 초기화 가능하지만 되도록이면 empty() 이용
			Optional<String> opt4 = null;
			logger.debug("opt4 실행결과 : {}", opt4);  // 실행결과 : null
			Optional<String> opt5 = Optional.empty();
			logger.debug("opt5 실행결과 : {}", opt5);  // 실행결과 : Optional.empty
			[실행결과]
			opt 실행결과 : Optional[test]
			opt2 실행결과 : Optional[aaa]
			opt3 실행결과 : Optional.empty
			opt4 실행결과 : null
			opt5 실행결과 : Optional.empty
			```
		+ Optional 객체의 값을 가져오는 방법
			+ get() : null이면 에러 발생함
			+ orElse() : 저장된 값이 null인 경우 대체할 값을 반환
			+ orElseGet() : orElse()의 변형, 저장된 값이 null인경우 대체할 값을 람다식으로 지정 할 수 있음
			+ orElseThrow() : orElse()의 변형, 저장된 값이 null인 경우 지정된 예외를 발생시킴
			+ isPresent() : 저장된 값이 null인 경우 false, 아니면 true를 반환
			+ ifPresent() : 저장된 값이 null이 아닌 경우만 수행됨(람다식 사용가능)
			```
			String value = "test";
			Optional<String> opt = Optional.of(value);
			
			// get() : null이면 에러 발생함
			String str1 = opt.get();
			logger.debug("str1 실행결과 : {}", str1);
			
			// orElse() : 저장된 값이 null인 경우 대체할 값을 반환
			Optional<String> opt2 = Optional.ofNullable(null);
			String str2 = opt2.orElse("null");
			logger.debug("str2 실행결과 : {}", str2);
			
			// orElseGet() : orElse()의 변형, 저장된 값이 null인경우 대체할 값을 람다식으로 지정 할 수 있음
			String str3 = opt2.orElseGet(() -> new String("새로 만들기"));
			logger.debug("str3 실행결과 : {}", str3);
			
			// orElseThrow() : orElse()의 변형, 저장된 값이 null인 경우 지정된 예외를 발생시킴
			String str4 = opt2.orElseThrow(NullPointerException::new);
			logger.debug("str4 실행결과 : {}", str4);
			
			// isPresent() : 저장된 값이 null인 경우 false, 아니면 true를 반환
			logger.debug("isPresent 실행결과 : {}", opt2.isPresent());
			// ifPresent() : 저장된 값이 null이 아닌 경우만 수행됨
			opt.ifPresent(System.out::println);
			[실행결과]
			str1 실행결과 : test
			str2 실행결과 : null
			str3 실행결과 : 새로 만들기
			isPresent 실행결과 : false
			test
			```
		+ 스트림처럼 기본형 Optional 클래스가 있음(`OptionalInt`, `OptionalLong`, `OptionalDouble`)
			+ 기본형 Optional 객체의 값을 가져오는 방법
				+ getAs타입() : getAsInt(), getAsLong(), getAsDouble()
				```
				OptionalInt optInt = OptionalInt.of(0);
				OptionalInt optInt2 = OptionalInt.empty();
				logger.debug("equals 실행결과 : {}",  optInt.equals(optInt2));
				[실행결과]
				equals 실행결과 : false
				```
	+ 스트림의 최종연산
		+ 최종 연산는 단일값이거나 스트림 요소가 담긴 배열 또는 컬렉션일 수 있음
		+ for문
			+ forEach() : 병렬인 경우에는 순서가 보장되지 않음, forEachOrdered() 보다 속도가 더 빠름
			+ forEachOrdered() : 병렬인 경우에도 순서가 보장됨
		```
        System.out.println("forEach()");
		IntStream.range(1, 10).forEach(System.out::print);
		System.out.println("");
		System.out.println("====================");
		System.out.println("forEachOrdered()");
		IntStream.range(1, 10).forEachOrdered(System.out::print);
		System.out.println("");
		System.out.println("====================");
		
		// parallel() : 순서가 보장되지 않음
		System.out.println("parallel().forEach()");
		IntStream.range(1, 10).parallel().forEach(System.out::print);
		System.out.println("");
		System.out.println("====================");
		System.out.println("parallel().forEachOrdered()");
		IntStream.range(1, 10).parallel().forEachOrdered(System.out::print);
		[실행결과]
		forEach()
		123456789
		====================
		forEachOrdered()
		123456789
		====================
		parallel().forEach()
		657192834
		====================
		parallel().forEachOrdered()
		123456789
		```
	+ reduce()
		+ 누적연산 수행함
		+ `reduce(초기값, 누적 수행 작업)`
		```
		String[] strArr = { 
			"AAAA", 
			"AAAAAAAA", 
			"AA", 
			"A", 
			"AAAAAAAAAAAA", 
			"AAAAAAAAAAAAAAAAAAAAAAAAA", 
			"" 
		};
		
		// Stream<String> -> Stream<Integer>으로 변환
		// Stream<Integer> intStream = Stream.of(strArr).map(String::length);
		// Stream<String> -> IntStream으로 변환
		IntStream intStream = Stream.of(strArr).mapToInt(String::length);
		intStream.forEach(System.out::println);
		
		int cnt = Stream.of(strArr).mapToInt(String::length).reduce(0, (a,b) -> a + 1);
		System.out.println("cnt : " + cnt);
		
		int sum = Stream.of(strArr).mapToInt(String::length).reduce(0, (a,b) -> a + b);
		System.out.println("sum : " + sum);
		
		OptionalInt max = Stream.of(strArr).mapToInt(String::length).reduce(Integer::max);
		System.out.println("max : " + max.getAsInt());
		OptionalInt min = Stream.of(strArr).mapToInt(String::length).reduce(Integer::min);
		System.out.println("min : " + min.getAsInt());
		[실행결과]
		4
		8
		2
		1
		12
		25
		0
		cnt : 7
		sum : 52
		max : 25
		min : 0
		```
   + collect()
	   + Collector를 매개변수로 하는 스트림의 최종연산
	   + **그룹별 reduce()**
	   + 스트림 -> 컬렉션으로 변환 : toList(), toSet(), toMap(), toCollection()
	   + 스트림 -> 배열로 변환 : toArray()
		```
		// toList(), toSet(), toMap(), toCollection() : 스트림 -> 컬렉션으로 변환
		List<String> list = Arrays.asList("1", "2", "3", "4", "5");
		List<String> resultList1 = list.stream().collect(Collectors.toList());
		System.out.println("==== toList() ====");
		resultList1.stream().forEach(System.out::println);
		
		ArrayList<String> resultList2 = list.stream().collect(Collectors.toCollection(ArrayList::new));
		System.out.println("==== toCollection() ArrayList ====");
		resultList2.stream().forEach(System.out::println);
		
		HashSet<String> resultList3 = list.stream().collect(Collectors.toCollection(HashSet::new));
		System.out.println("==== toCollection() HashSet ====");
		resultList3.stream().forEach(System.out::println);
		
		// toArray() : 스트림 -> 배열로 변환, Object[] 타입으로 반환
		String[] strArr = { 
		    "AAAA", 
		    "AAAAAAAA", 
		    "AA", 
		    "A", 
		    "AAAAAAAAAAAA", 
		    "AAAAAAAAAAAAAAAAAAAAAAAAA", 
		    "" 
		};
		Stream<String> stream = Stream.of(strArr);
		
		String[] result1 = (String[]) Stream.of(strArr).toArray();
		Object[] result2 = Stream.of(strArr).toArray();
		String[] result3 = Stream.of(strArr).toArray(String[]::new);
		
		// 출력
		System.out.println("==== 출력 ====");
		Arrays.stream(result3).forEach(System.out::println);
		[실행결과]
		==== toList() ====
		1
		2
		3
		4
		5
		==== toCollection() ArrayList ====
		1
		2
		3
		4
		5
		==== toCollection() HashSet ====
		1
		2
		3
		4
		5
		==== 출력 ====
		AAAA
		AAAAAAAA
		AA
		A
		AAAAAAAAAAAA
		AAAAAAAAAAAAAAAAAAAAAAAAA
		```
		+ 그룹화 : groupingBy(), partitioningBy()
			+ groupingBy, partitioningBy 분류의 차이만 있을뿐 `Map`에 담겨 반환
				+ groupingBy() : 분류 기준이 `Fuction`
				+ partitioningBy() : 분류 기준이 `Predicate`, 반환타입이 boolean 타입
			```
			class TestStudent {
			    String name;
			    boolean isMale; // 성별
			    int hak;        // 학년
			    int ban;        // 반
			    int score;
			
			    TestStudent(String name, boolean isMale, int hak, int ban, int score) { 
			        this.name   = name;
			        this.isMale = isMale;
			        this.hak    = hak;
			        this.ban    = ban;
			        this.score  = score;
			    }
			
			    String  getName()  { return name;}
			    boolean isMale()   { return isMale;}
			    int     getHak()   { return hak;}
			    int     getBan()   { return ban;}
			    int     getScore() { return score;}
			
			    public String toString() { 
			        return String.format("[%s, %s, %d학년 %d반, %3d점]", name, isMale ? "남":"여", hak, ban, score); 
			    }
			
			    enum Level {
			        HIGH, MID, LOW
			    }
			}
			
			public static void test2() {
			    TestStudent[] stuArr = {
			        new TestStudent("나자바", true, 1, 1, 300),
			        new TestStudent("나자바2", true, 1, 1, 300),
			        new TestStudent("김지미", false, 1, 1, 250),
			        new TestStudent("김자바", true, 1, 1, 200),
			        new TestStudent("이지미", false, 1, 2, 150),
			        new TestStudent("남자바", true, 1, 2, 100),
			        new TestStudent("안지미", false, 1, 2, 50),
			        new TestStudent("황지미", false, 1, 3, 100),
			        new TestStudent("강지미", false, 1, 3, 150),
			        new TestStudent("이자바", true, 1, 3, 200),
			        new TestStudent("나자바", true, 2, 1, 300),
			        new TestStudent("김지미", false, 2, 1, 250),
			        new TestStudent("김자바", true, 2, 1, 200),
			        new TestStudent("이지미", false, 2, 2, 150),
			        new TestStudent("남자바", true, 2, 2, 100),
			        new TestStudent("안지미", false, 2, 2, 50),
			        new TestStudent("황지미", false, 2, 3, 100),
			        new TestStudent("강지미", false, 2, 3, 150),
			        new TestStudent("이자바", true, 2, 3, 200),
			    };
			    
			    // 학년별로 그룹화
			    Map<Integer, List<TestStudent>> group1 = (Map<Integer, List<TestStudent>>) 
			            Arrays.stream(stuArr)
			                  .collect(Collectors.groupingBy(TestStudent::getHak))
			    ;
			    
			    System.out.println("=== 학년별로 그룹화 === ");
			    group1.values().stream().forEach(System.out::println);
			    
				// 성별로 그룹화
			    Map<Boolean, List<TestStudent>> partitioningGroup1 = 
			                Arrays.stream(stuArr)
			                    .collect(Collectors.partitioningBy(TestStudent::isMale))
			        ;
			    System.out.println("=== 성별로 그룹화 === ");
			    partitioningGroup1.values().stream().forEach(System.out::println);
			    
			    // 학년별 그룹화 건수
			    Map<Integer, Long> cntMap = Arrays.stream(stuArr).collect(Collectors.groupingBy(TestStudent::getHak, Collectors.counting()));
			    System.out.println("=== 학년별 그룹화 건수=== ");
			    System.out.println("1학년 : " + cntMap.get(1));
			    System.out.println("2학년 : " + cntMap.get(2));
			    
			    // 학년별, 반별로 그룹화
			    Map<Integer, Map<Integer, List<TestStudent>>> group2 = (Map<Integer, Map<Integer, List<TestStudent>>>) 
			            Arrays.stream(stuArr)
			                  .collect(Collectors.groupingBy(TestStudent::getHak, Collectors.groupingBy(TestStudent::getBan)))
			    ;
			    System.out.println("=== 학년별, 반별로 그룹화 === ");
			    group2.values().stream().forEach(System.out::println);
			    
			    // 성별, 점수별로 그룹화
			    Map<Boolean, Map<Boolean, Long>> group3 =
			            Arrays.stream(stuArr)
			                  .collect(Collectors.partitioningBy(TestStudent::isMale,
			                                                     Collectors.partitioningBy(student -> student.getScore() >= 100, Collectors.counting())))
			    ;
			    System.out.println("=== 성별, 점수 100점 이상 그룹화 === ");
			    group3.values().stream().forEach(System.out::println);
			    System.out.println("남 : " + group3.get(true).get(true));
			    System.out.println("여 : " + group3.get(false).get(true));
			}
		   ```
 
 > collect vs Collector vs Collectors
 > + collect : Collector를 매개변수로 하는 스트림 최종연산
 > + Collector : collect() 관련 메서드가 있는 **인터페이스**
 > + Collectors : **Collector 인터페이스 구현체**
 
#### 출처(참고문헌)
- Java의 정석
- https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
- https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html#shadowing
- https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
- https://velog.io/@snack655/Java-Fire%ED%95%9C-Effectively-Final%EC%9D%B4%EB%9E%80
- https://yeon-kr.tistory.com/211

#### 각주
[^1]: https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html