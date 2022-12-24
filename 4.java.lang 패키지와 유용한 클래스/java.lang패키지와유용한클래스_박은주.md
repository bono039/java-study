---
created: 2022-12-20
title: 자바스터티_09_java.lang패키지와유용한클래스
author: pej
category: study
tag: study
aliases: [자바스터티_09_java.lang패키지와유용한클래스]
---

#### 책 목차
	- java.lang패키지
	- 유용한 클래스


#### 학습할 것
	- java.lang 클래스
	- 래퍼(Wrapper) 클래스

#### 학습 내용

1. java.lang 패키지
	+ 자바에서 가장 기본이 되는 클래스들을 포함하고 있으며 별도의 import문 없이도 사용 할 수 있음
	+ Object 클래스
		+ **11개의 메서드만 있으며 별도의 인스턴스 생성 없이 바로 사용이 가능함**
		+ equals() 
			+ 파라미터로 받은 참조변수가 이 개체(Object)와 동일한지 여부를 주소값으로 판단하지만 **그 외에는(String, Date, File 등등) 주소값이 아닌 내용을 비교하도록 오버라이딩 되어 있음**
			```
			// Object 클래스의 equals() 메서드 소스 코드
			public boolean equals(Object obj) {
		        return (this == obj);
		    }
			```
			```
			// String 클래스의 equals() 메서드 소스 코드
			public boolean equals(Object anObject) {
		        if (this == anObject) {
		            return true;
		        }
		        if (anObject instanceof String) {
		            String anotherString = (String)anObject;
		            int n = value.length;
		            if (n == anotherString.value.length) {
		                char v1[] = value;
		                char v2[] = anotherString.value;
		                int i = 0;
		                while (n-- != 0) {
		                    if (v1[i] != v2[i])
		                        return false;
		                    i++;
		                }
		                return true;
		            }
		        }
		        return false;
		    }
			```
		+  hasCode() 
			+ 인스턴스의 주소값을 이용해서 해시코드를 만들기 때문에 서로 다른 두 인스턴스는 같은 해시코드를 가질 수 없음(단, String 클래스는 내용이 값으면 동일한 해시코드를 반환함)
			```
		    public static void main(String[] args) {
		        // 참조변수는 주소값을 가리킴
		        // 인스턴스가 생성될때마다 참조변수가 가리키는 주소값은 달라짐
		        // => 서로 다른 두 인스턴스를 비교하면 항상 false로 나옴
		        Object obj1 = new Object(); // Ox1234
		        Object obj2 = new Object(); // Ox5678
		        
		        System.out.println("obj1 hashCode : " + obj1.hashCode());
		        System.out.println("obj2 hashCode : " + obj2.hashCode());
		        
		        if (obj1.equals(obj2)) {
		            System.out.println("obj1 == obj2");
		        } else {
		            System.out.println("obj1 != obj2");
		        }
		        
		        // 강제로 같은 인스턴스의 주소값을 저장
		        obj2 = obj1;
			    
			    System.out.println("## 변경후 ##");
		        if (obj1.equals(obj2)) {
		            System.out.println("obj1 == obj2");
		        } else {
		            System.out.println("obj1 != obj2");
		        }
		        
		        // 인스턴스의 주소값을 변경하자 해시코드도 변경됨
		        System.out.println("obj1 hashCode : " + obj1.hashCode());
		        System.out.println("obj2 hashCode : " + obj2.hashCode());
		    }
			[결과]
			obj1 hashCode : 366712642
			obj2 hashCode : 1829164700
			obj1 != obj2
			## 변경후 ##
			obj1 == obj2
			obj1 hashCode : 366712642
			obj2 hashCode : 366712642
			```
		 + clone() 
			 + 자신을 복제하여 새로운 인스턴스를 생성하는 것으로 Object 클래스의 **clone()은 단순히 값만 복사하는 얕은 복사임 원본과 복사본이 같은 주소값을 가리키기때문에 원본을 변경하면 복사본도 영향을 받음**
			 ```
			public class TestClone {
			    public static void main(String[] args) {
			        Computer obj1 = new Computer();
			        obj1.setCpuNm("i7 12세대");
			        obj1.setRamSize(32);
			        
			        ArrayList<Computer> orgList = new ArrayList<>();
			        orgList.add(obj1);
			        
			        /**
			         * 얕은  복사(Shallow Copy) 
			         *  - 원본과 같은 주소값을 참조하기 때문에 원본의 값이 바뀌면 같이 변경됨
			         */
			        ArrayList<Computer> shallowCopyList = (ArrayList<Computer>) orgList.clone();
	        
			        /**
			         * 깊은  복사(Deep Copy) 
			         *  - 원본과 복사본이 다른 주소값을 참조하기때문에 원본의 값이 변경되어도 복사본의 값은 변경되지 않음
			         */
			        ArrayList<Computer> deepCopyList = new ArrayList<>();
			        for (Computer item : orgList) {
			            Computer tmp = new Computer();
			            tmp.setCpuNm(item.getCpuNm());
			            tmp.setRamSize(item.getRamSize());
			            tmp.setSsdSize(item.getSsdSize());
			            
			            deepCopyList.add(tmp);
			            // deepCopyList.add(item); // 얕은 복사
			        }
			        
			        // 값 변경
			        obj1.setSsdSize(128);
			        
			        System.out.println("#### orgList ####");
			        for (Computer item : orgList) {
			            System.out.println(item.toString());
			            
			        }
			        System.out.println("#### shallowCopyList ####");
			        for (Computer item : shallowCopyList) {
			            System.out.println(item.toString());
			        }
			        System.out.println("#### deepCopyList ####");
			        for (Computer item : deepCopyList) {
			            System.out.println(item.toString());
			        }
			    }
			}
			[결과]
			#### orgList ####
			item.hashCode : 366712642 / item : [cpuNm : i7 12세대, ssdSize :128, ramSize : 32]
			#### shallowCopyList ####
			item.hashCode : 366712642 / item : [cpuNm : i7 12세대, ssdSize :128, ramSize : 32]
			#### deepCopyList ####
			item.hashCode : 1829164700 / item : [cpuNm : i7 12세대, ssdSize :0, ramSize : 32]
			```
		 + getClass() 
			 + 자신이 속한 클래스의 Class객체를 반환하는 메서드
			```
			// Object 클래스의 getClass() 메서드 소스 코드
			public final native Class<?> getClass();
		
			// Class 클래스를 이용해서 인스턴스 생성
			TestClone.class.newInstance();
			// Class 클래스를 이용해서 생성한 인스턴스로 메서드 호출
			TestClone.class.newInstance().clone();
			```
	       > Class 객체
	       > + 클래스의 모든 정보를 가지고 있음
	       > + 클래스 파일이 클래스 로더에 의해서 메모리에 올라갈때 자동적으로 생성되며 클래스당 1개만 존재함
	            
           > 리플렉션(Reflection)[^1]
 	       > + 동적으로 인스턴스를 생성하고 메서드를 호출하는 방법
	+ String 클래스
		 + **변경 불가능한(immutable) 클래스**로 할당된 공간이 변하지 않는 것이 불변, 할당된 공간이 변하는것이 가변이라고 함
		 + 문자열 결합시 문자열의 값이 바뀌는게 아니라 결합된 문자열의 주소값을 가진 인스턴스가 생성됨 따라서 문자열 결합이나 연산시 StringBuffer나 StringBilder 사용하는 것이 좋음
	     > 문자열 리터널
	     > + 자바 파일에 있는 모든 문자열 리터널은 컴파일시에 클래스 파일에 저장됨
	     > + 클래스 로더에 의해 메모리에 올라갈 때 리터널들이 JVM내에 있는 상수 저장소(Constant Pool)에 저장됨
		+ String.join() 
			+ JDK 1.8부터 추가된 String 클래스 메서드
			+ 문자열 사이에 구분자를 넣어서 결합함
			+ 내부적으로 StringJoiner를 이용함
			 ```
            public static String join(CharSequence delimiter, CharSequence... elements) {
		        Objects.requireNonNull(delimiter);
		        Objects.requireNonNull(elements);
		        // Number of elements not likely worth Arrays.stream overhead.
		        StringJoiner joiner = new StringJoiner(delimiter);
		        for (CharSequence cs: elements) {
		            joiner.add(cs);
		        }
		        return joiner.toString();
		    }
			```
			```
			// String.join() 사용한 문자열 결합
			String joinTest = "join";
			System.out.println(String.join(" ", joinTest, "사용", "1", "2"));
			
			// StringJoiner 사용한 문자열 결합
			StringJoiner st = new StringJoiner(" ");
			st.add("StringJoiner");
			st.add("사용");
			System.out.println(st.toString());
			[결과]
		    join 사용 1 3
		    StringJoiner 사용
			```
		+ String.format()
			+ 형식화된 문자열 만들때 사용
			```
			String strFormat = String.format("%d + %d = %d", 1, 2, 1+2);
			System.out.println(strFormat);
			[결과]
			1 + 2 = 3
			```
	+ Math 클래스[^2]
		+ 수학 계산에 유용한 메서드들이 있는 클래스
		+ 메서드 이름중에 'Exact' 있는것들은 연산시 오버플로우가 발생하면 예외를 던지며 JDK 1.8부터 추가되었음
		```
		// Math 클래스의 addExact() 메서드 소스코드
		public static int addExact(int x, int y) {
	        int r = x + y;
	        // HD 2-12 Overflow iff both arguments have the opposite sign of the result
	        if (((x ^ r) & (y ^ r)) < 0) {
	            throw new ArithmeticException("integer overflow");
	        }
	        return r;
	    }
		```
	> java.lang.StrictMath 클래스
	> + Math 클래스는 OS에 따라 처리 결과가 달라질 수 있기 때문에 항상 같은 결과값이 나오도록 만들어진 클래스
	+ 래퍼(Wrapper) 클래스[^3]
		+ 기본형(primitive)을 참조형처럼 사용할 수 있게 해주는 클래스
		+ 래퍼 클래스의 생성자는 문자열이나 각 기본형의 값들을 파라미터로 받음
		+ final로 정의되어 있기 때문에 **변경 불가능한(immutable) 클래스**
		```
		public final class Integer extends Number implements Comparable<Integer> {
		...
		```	
        + 값 비교시 `equals()`  이용해야함
		+ **원래 기본형은 `null`을 허용하지 않지만 껍데기는 참조형이기때문에 `null`을 넣을 수 있음**
		
		![래퍼클래스구조도](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbvzp79%2FbtqEbacB01v%2FQQjO7cSc9tTvKJkyzFsK90%2Fimg.png)
		
	    | 기본형 타입 | 래퍼 클래스명 |
	    | ----------- | ------------- |
	    | boolean     | Boolean       |
	    | char        | Character     |
	    | byte        | Byte          |
	    | short       | Short         |
	    | int         | Integer       |
	    | long        | Long          |
	    | float       | Float         |
	    | double      | Double        |
	
	```
	Integer intObj = null; // 래퍼 클래스에 강제로 null 셋팅
    int testNum = intObj;
	
	// 기본형 타입은 null을 허용하지 않기때문에 에러 발생함
	int testNum = intObj == null ? 0 : intObj;
	int testNum = intObj; // Null 에러발생
	
	System.out.println("### testNum : " + testNum);
	```
	
	> 오토박싱(Autoboxing), 언박싱(Unboxing)
	> + 오토박싱(Autoboxing) : 기본형 값 -> 래퍼 클래스의 값
	> + 언박싱(Unboxing) : 래퍼 클래스의 값 -> 기본형 값
	> + JDK 1.5 이전에는 기본형과 참조형간의 연산이 불가능해서 래퍼 클래스를 이용해야 했음
	> + 현재는 컴파일러가 오토박싱, 언박싱 코드를 넣어주기 때문에 연산이 가능해짐
	> ```
	> // 참조형 - 래퍼클래스
	> Integer intObj = 1; // 오토박싱(#2)
	> // 기본형
	> int num = intObj; // 언박싱(#3)
	> [바이너리 코드]
	> Code:
	>  0: iconst_1
	>  1: invokestatic  #2 // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
	>  4: astore_1
	>  5: aload_1
	>  6: invokevirtual #3 // Method java/lang/Integer.intValue:()I
	>  9: istore_2
	>  10: return
	> ```

2. 유용한 클래스
	+ java.math.BigInteger 클래스
		+ long 타입보다 더 큰 값을 표현할 수 있는 클래스
		+ **변경 불가능한(immutable) 클래스**
		+ Math 클래스와 동일하게 메서드 이름중에 'Exact' 있는것들은 연산시 오버플로우가 발생하면 예외를 던짐
	+ java.math.BigDecimal 클래스
		+ double 타입보다 더 큰 값을 표현할 수 있는 클래스로 금액 관련된 작업시 정확도를 위해 사용하는 것이 좋음 `double 타입은 부동소숫점 방식으로 근사치값을 저장하지만 BigDecimal 타입은 정수를 이용해 실수를 표현하기 때문에 더 정확한 값을 얻을 수 있음`
		```
		BigDecimal d = new BigDecimal(0.1); // 이상한 값 나올수있음
		BigDecimal d = new BigDecimal("0.1"); // 정상적인 값 나옴
		```
		+ **변경 불가능한(immutable) 클래스**
		+ Math 클래스와 동일하게 메서드 이름중에 'Exact' 있는것들은 연산시 오버플로우가 발생하면 예외를 던짐


#### 출처(참고문헌)
- Java의 정석
- 혼자 공부하는 자바
- https://velog.io/@heoseungyeon/StringBuilder%EC%99%80-StringBuffer%EB%8A%94-%EB%AC%B4%EC%8A%A8-%EC%B0%A8%EC%9D%B4%EA%B0%80-%EC%9E%88%EB%8A%94%EA%B0%80
- https://velog.io/@dmsgp8292/%EA%B8%B0%EB%B3%B8%EC%9E%90%EB%A3%8C%ED%98%95%EA%B3%BC-Wrapper-%ED%81%B4%EB%9E%98%EC%8A%A4
- https://coding-factory.tistory.com/547
- https://velog.io/@new_wisdom/Java-BigDecimal%EA%B3%BC-%ED%95%A8%EA%BB%98%ED%95%98%EB%8A%94-%EC%95%84%EB%A7%88%EC%B0%8C%EC%9D%98-%EB%84%88%EB%93%9C%EC%A7%93

#### 연결문서
- 자바스터디_08_예외처리

#### 각주
[^1]: [이슈] Reflection에 대해 검색해보면 모호한 설명들이 많으며 개념이 이해가 잘되지 않는다. 어떤 경우에 사용하는지 그리고 이유는 뭔지 알아볼 필요가 있다.
[^2]: [이슈] Math 클래스 메서드를 보면 StrictMath 클래스의 메서드를 사용하고 있는데, StrictMath.round()메서드는 Math.round()를 사용함. round() 메서드도 OS마다 똑같은 값이 나와야 하는 것이 아닌지? 
 [^3]: [이슈] 래퍼 클래스 사용 이유가 뭘까?
