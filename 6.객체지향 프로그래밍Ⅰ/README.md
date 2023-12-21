| created    | title               | author         | category |
|-------|------------------|-----------------|----------|
| 2023-12-14   | 객체지향프로그래밍1_한의정  | 한의정  | JAVA     |


<br/>

### 1. 객체 지향 언어
   > 특징 : <span style='background-color: #fff5b1'>재사용성, 유지보수, 중복 코드 제거</span>

<br/>

### 2. 클래스와 객체

| 용어    | 설명               |
|-------|------------------|
| **클래스**   | 객체 설계도           |
| **인스턴스화** | 클래스로부터 객체 만드는 과정 |
| **인스턴스**  | 클래스로부터 만들어진 객체   |

▷ 클래스 ⊃ 객체 ⊃ 인스턴스

<br/>

#### ◾ 객체
> 구성 요소 : 속성, 기능 (= **변수, 메소드**)

<br/>

#### ◾ 인스턴스 생성과 사용
      
     클래스명 변수명;          // 클래스 객체 참조 위한 참조변수 선언
     변수명 = new 클래스명();  // 클래스 객체 생성 후, 객체 주소를 참조 변수에 저장
▷ 인스턴스는 참조변수 통해서만 다룰 수 있으며, <code>**참조변수 타입 = 인스턴스 타입**</code>이어야 한다.<br/><br/>
➕ **_<span style='color: red'>가비지 컬렉터</span>_**, 자신 참조하는 참조변수가 하나도 없는 인스턴스는 자동으로 메모리에서 제거

<br/>

**◾ 객체 배열**
      
    TV[] tvArr = new TV[3];   // 객체 배열 생성

    // 1) 배열 초기화 블럭 사용해 배열 초기화
    tvArr = {new TV(), new TV(), new TV()};

    // 2) for문 사용해 배열 초기화
    for(int i = 0 ; i < tvArr.length ; i++) {
        tvArr[i] = new TV();
    }

<br/>

**◾ 클래스**
- 변수 → 배열 → 구조체 → **클래스**

   | 용어      |설명                                  |
   |---------|------------------------------------|
   | **변수**  | 하나의 데이터 저장                          |
   | **배열**  | 같은 종류의 여러 데이터를 하나의 집합으로 저장          |
   | **구조체** | 서로 관련된 여러 데이터를 종류에 관계없이 하나의 집합으로 저장 |
   | <span style="color:red">**클래스**</span> | 데이터 + 함수 (구조체 + 함수)                 |

<br/>

### 3. 변수와 메소드

#### ◾ 변수

  | 변수 종류     | 선언 위치                                  | 생성 시기           |
  |-----------|----------------------------------------|-----------------|
  | **클래스 변수**    | 클래스 영역                                 | 클래스가 메모리에 올라갈 때 |
  | **인스턴스 변수** | 클래스 영역                                 | 인스턴스 생성 시       |
  | **지역 변수**   | 클래스 영역 外 영역 <br> (메소드, 생성자, 초기화 블럭 내부) | 변수 선언문 수행 시     |

 &nbsp;&nbsp; ① **클래스 변수** <br/> &nbsp;&nbsp;→ **<code>static</code>** 붙음 <br/>&nbsp;&nbsp;→ 모든 인스턴스가 **공통된** 변수(저장공간) 보유 <br/>&nbsp;&nbsp;→ 객체 생성 없이, <code>'클래스명.클래스변수'</code>로 직접 사용 가능
<br/>&nbsp;&nbsp;→ [생성] 클래스가 메모리에 로딩될 때  / [종료] 프로그램 종료 시까지 유지

&nbsp;&nbsp; ② **인스턴스 변수** <br/> &nbsp;&nbsp;→ 인스턴스마다 다른 값 가지는 속성인 경우 사용

&nbsp;&nbsp; ③ **지역 변수**<br/> &nbsp;&nbsp;→ 메소드 내에 선언되어 메소드 내에서만 사용 가능 <br/>
<br/>

#### ◾ 메소드
> 특정 작업 수행하는 일련의 문장들을 하나로 묶은 것
- 내부가 보이지 않는 '블랙박스'
- 사용 이유 : ① 재사용성, ② 중복 코드 제거, ③ 프로그램 구조화
- 선언과 구현
   1) **선언부**, 반환타입 메소드명 매개변수선언(입력) 
   2) **구현부**, return 시, 반환 타입과 일치하거나 자동 형변환 가능한 <span style="color: red">**단 하나의 값**</span>만 반환 가능

- return문, 현재 실행중인 메소드 종료하고 호출한 메소드로 돌아감.
- 매개변수 유효성 검사 잊지 않기

<br/>

#### ◾ <span style='background-color: #ffdce0'>JVM의 메모리 구조</span>
1. **메소드 영역** 
    - 프로그램 실행 중 사용되는 클래스의 클래스 파일(.class)을 읽고 분석해 <span style="color: red">**클래스에 대한 정보(클래스 데이터)**</span>를 저장하는 영역
    - 클래스의 <span style="color: red">**클래스 변수**</span>도 이 영역에 함께 생성됨.
   <br/>
2. **힙**

   - <span style="color: red">**인스턴스 / 인스턴스 변수들**</span>이 생성되는 공간 
   
3. **호출 스택**

   - 작업에 필요한 메모리 공간 제공
   - 작업 종료 시, 할당되었던 메모리 공간은 반환하고 스택에서 제거됨
   - 맨 위 메소드 = 현재 실행 중인 메소드

<br/>

#### ◾ 기본형 vs 참조형 매개 변수
- **기본형** : 값 읽기만 → **_read only_**
- **참조형** : 인스턴스 주소 복사해 값 변경 가능 → **_read & write_**
<details>
<summary>기본형 매개변수 예제 코드</summary>

      class Data {int x;}

      class Main {
         public static void main(String[] args) {
            Data d = new Data();
            d.x = 10;
            System.out.println("main() : x = " + d.x);   // 출력 : main() : x = 10

            change(d.x);                                 // 출력 : change() : x = 1000
            System.out.println("After change(d.x)");     // 출력 : After change(d.x) 
            System.out.println("main() : x = " + d.x);   // 출력 : main() : x = 10
         }

         // 기본형 매개변수
         static void change(int x) {
            x = 1000;
            System.out.println("change() : x = " + x);
         }
      }
![img_3.png](img_3.png)
</details>
<br/>
<details>
<summary>참조형 매개변수 예제 코드</summary>

      class Data {int x;}

      class Main {
         public static void main(String[] args) {
            Data d = new Data();
            d.x = 10;
            System.out.println("main() : x = " + d.x);   // 출력 : main() : x = 10

            change(d);                                   // 출력 : change() : x = 1000
            System.out.println("After change(d)");       // 출력 : After change(d) 
            System.out.println("main() : x = " + d.x);   // 출력 : main() : x = 1000
         }

         // 참조형 매개변수
         static void change(Data d) {
            d.x = 1000;
            System.out.println("change() : x = " + d.x);
         }
      }
![img_4.png](img_4.png)
</details>

<br/>

#### ◾ 참조형 반환타입
> 메소드가 '**_객체의 주소_**' 를 반환한다는 의미

<br/>

#### ◾ 재귀 호출
> 메소드 내부에서 메소드 자신 다시 호출
- 종료 위한 조건문 필수!
- 반복문보다 수행시간 더 걸림 <br/> &nbsp; (∵ 몇 가지 과정이 추가 필요하므로 - 매개변수 복사와 종료 후 복귀할 주소 저장 등 )
- 장점 : 알아보기 쉬움
<details>
    <summary> 예 : 팩토리얼</summary>

    public class FactorialTest {

        public static void main(String[] args) {
            int result = factorial(4);
        
            System.out.println(result);
        }
            
        static int factorial(int n) {            
            if(n == 1)  return 1;
            return n * factorial(n - 1);  // 재귀호출
        }
    }
</details>

<br/>

#### ◾ 클래스 메소드(static 메소드) & 인스턴스 메소드
- **클래스 메소드**
  > 인스턴스와 관계 없는 메소드

    - 메소드 앞에 <code>**static**</code> 붙음
        - 멤버변수 中 모든 인스턴스에 공통으로 사용하는 값에 static 붙인다.
        - 메소드 내에서 인스턴스 변수를 사용하지 않는다면, static 붙이는 걸 고려한다.
    - 객체 생성 없이 '`클래스명.메소드명`'으로 호출 가능
  
<br/>
  
- **인스턴스 메소드**
  > 인스턴스 변수 관련 작업하는 = 메소드 작업 수행하는데 **인스턴스 변수**를 필요로 하는 메소드
    - 반드시 객체 생성해야 호출 가능 

<br/>

#### ◾ 클래스 멤버와 인스턴스 멤버 간 참조와 호출
- 클래스 멤버 (클래스 변수와 메소드)가 인스턴스 멤버(인스턴스 변수와 메소드)를 참조·호출하고자 하는 경우, 인스턴스를 생성해야 사용 가능하다.
- 인스턴스 메소드에서는 호출 없이 바로 인스턴스 변수 사용 가능


<br/>

### 4. 오버로딩
#### ◾ 정의
  >한 클래스 내 같은 이름의 메소드를 여러 개 정의하는 것

 &nbsp; = 하나의 메소드명으로 여러 기능 구현
<br/>

#### ◾ 조건
  > 1. 메소드명이 같아야 한다.
  > 2. 매개변수의 개수 / 타입이 달라야 한다.

<br/>

#### ◾ 장점
  - 여러 메소드들이 1개의 이름으로 정의되어 있어 기억하기 쉽고 오류 가능성을 줄일 수 있다.
  - 메소드 이름 절약 가능

#### 
<details>
    <summary> 가변인자(varargs)와 오버로딩 (동시사용 지양🙅‍♀️)</summary>

> ***가변인자** : 메소드의 매개변수 개수를 동적으로 지정
- `타입... 변수명`과 같은 형식으로 선언
- 가변인자는 매개변수 중 <u>**항상 마지막**</u>에 있어야 함

      public PrintStream printf(String format, Object... args) { ... }    // O
      public PrintStream printf(Object... args, String format) { ... }    // X

- 내부적으로 배열 이용

      String concat(String... str) { ... }
      System.out.println(concat());          // 인자 없음
      System.out.println(concat("a"));       // 인자 1개
      System.out.println(concat("a", "b"));  // 인자 2개
      System.out.println(concat(new String[]{"a", "b"}));  // 배열도 가능

- 되도록 가변인자 사용한 메소드는 오버로딩하지 말기,,❗
</details>

<br/>

### 5. 생성자 (p.291-299)

#### ◾ 정의
> 인스턴스 변수들 초기화하는 메소드

#### ◾ 조건
> 1. 생성자 이름 = 클래스 이름이어야 한다.
> 2. 생성자는 리턴값이 없다.

- 오버로딩 가능 → 매개변수 O/X 생성자

<br/>

#### ◾ 기본 생성자 (default constructor)
- **_클래스에 생성자가 하나도 없는 경우_**, 컴파일러가 자동으로 기본 생성자 추가해 컴파일

#### ◾ 매개변수가 있는 생성자
- 매개변수 선언해 인스턴스 생성과 동시에 원하는 값으로 초기화 가능

<br/>

#### ◾ 생성자 간 호출 : this() vs this ⭐
- 조건
   > 1. 생성자 이름으로 클래스 이름 대신 **this()** 사용
   > 2. 한 생성자에서 다른 생성자 호출 시, **반드시 첫 줄에서만** 호출 가능

       Car(String color) {
           door = 5;                // 첫 번쨰 줄

           Car(color, "auto", 4);   // 에러1. 생성자 2번째 줄에서 다른 생성자 호출
                                    // 에러2. this(color, "auto", 4); 가 맞음
       }

<details>
    <summary> 예 : CarTest2.java</summary>

    class Car {
        String color;
        String gearType;
        int door;

        Car() {
            this("white", "auto", 4);   // => Car(String color, String gearType, int door) 호출
        }

        Car(String color) {
            this(color, "auto", 4);
        }
        Car(String color, String gearType, int door) {
            this.color = color;             // => 인스턴스 변수 앞에 this를 붙임 !
            this.gearType = gearType;
            this.door = door;
        }
    }

    class CarTest2 {
        public static void main(String[] args) {
            Car c1 = new Car();
            Car c2 = new Car("blue");
        }
    }
</details>

- **this** : _참조변수_. 인스턴스 자신 가리킴. 인스턴스 변수에 접근 가능. 인스턴스 멤버만 사용 가능.인스턴스 주소 저장.
- **this(), this(매개변수)** : _생성자_. 같은 클래스의 다른 생성자를 호출할 때 사용

<br/>

#### ◾ 생성자 이용한 인스턴스 복사
- 인스턴스 생성 시 결정사항
   > 1. **클래스** - 어떤 클래스의 인스턴스를 생성할 것인가?
   > 2. **생성자** - 선택한 클래스의 어떤 생성자로 인스턴스를 생성할 것인가?

<details>
    <summary>예</summary>

    Car(Car c) {
        // = Car(String color, String gearType, int door)
        this(c.color, c.gearType, c.door);
    }
</details>

<br/>

### 6. 변수의 초기화
- 멤버 변수(클래스 변수, 인스턴스 변수)와 배열의 초기화 → 선택적
- 지역 변수 초기화 → 반드시 해야 함

| 자료형                  | 기본값         |
|----------------------|-------------|
| **boolean**          | false       |
| **char**             | '\u0000'    |
| **byte, short, int** | 0           |
| **long**             | 0L          |
| **float**            | 0.0f        |
| **double**           | 0.0d 또는 0.0 |
| **참조형 변수**           | null        |

<br/>

#### ◾ 멤버 변수 초기화 방법
1. **명시적 초기화** (explicit initialization)
   - 변수 선언과 동시에 초기화하는 것

         class Car {
              int door = 4;           // 기본형 변수의 초기화
              Engine e = new Engine;  // 참조형 변수의 초기화
         }

2. **생성자** (constructor)
3. **초기화 블럭** (initialization block)

   > 초기화 작업이 복잡해 명시적 초기화만으로는 부족한 경우 사용
   1) **인스턴스 초기화 블럭**
      - 클래스 내에 블럭 {} 만들고 그 안에 코드 작성.
      - 인스턴스 생성할 때마다 수행
      - 생성자보다 먼저 수행
   2) **클래스 초기화 블럭**
      - 인스턴스 초기화 블럭 앞에 `static`만 붙이면 됨.
      - 처음 로딩될 떄 1번만 수행.

<details>
    <summary>예</summary>

    class BlockTest {

        // 클래스 초기화 블럭
        static {
            System.out.println("static { }");
        }

        // 인스턴스 초기화 블럭
        {
            System.out.println("{ }");
        }

        public BlockTest() {
            System.out.println("생성자");
        }

        public static void main(String[] args) {
            System.out.println("BlockTest bt = new BlockTest(); ");
            BlockTest bt = new BlockTest();

            System.out.println("BlockTest bt2 = new BlockTest(); ");
            BlockTest bt2 = new BlockTest();
        }
    }
</details>

<br/>

#### ◾ 멤버 변수 초기화 시기와 순서
- 클래스 변수의 초기화 시점 &nbsp; &nbsp; : 클래스가 처음 로딩될 때, 단 1번
- 인스턴스 변수의 초기화 시점 : 인스턴스가 생성될 때마다 각 인스턴스별로 초기화


- 클래스 변수의 초기화 순서 &nbsp; &nbsp; : 기본값 → 명시적 초기화 → 클래스 초기화 블럭
- 인스턴스 변수의 초기화 순서 : 기본값 → 명시적 초기화 → 인스턴스 초기화 블럭 → 생성자


    class InitTest {

        // 명시적 초기화
        static int cv = 1;
        int iv = 1;

        static { cv = 2; }    // 클래스 초기화 블럭
        { iv = 2; }           // 인스턴스 초기화 블럭

        // 생성자
        InitTest() {
            iv = 3;
        }
    }

<br/>

---
### 🔗 출처 및 참고 자료
- [Java의 정석 3판](https://www.yes24.com/Product/Goods/24259565)
- [마크다운 형광펜](https://geniewishescometrue.tistory.com/entry/%EB%A7%88%ED%81%AC%EB%8B%A4%EC%9A%B4-%EA%B4%80%EB%A0%A8-%ED%8C%81-%EA%B8%80-%EC%83%89%EC%83%81-%ED%98%95%EA%B4%91%ED%8E%9C)
- [마크다운 표 만들기](https://inasie.github.io/it%EC%9D%BC%EB%B0%98/%EB%A7%88%ED%81%AC%EB%8B%A4%EC%9A%B4-%ED%91%9C-%EB%A7%8C%EB%93%A4%EA%B8%B0/)
- [마크다운 토글](https://computer-science-student.tistory.com/388)
