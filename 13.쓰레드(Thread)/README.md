---
created: 2023-02-04
title: 스레드_박은주
author: pej
category: study
tag: study
aliases: []
---

#### 책 목차
- 프로세스와 스레드
- 스레드의 구현과 실행
- start()와 run()
- 싱글스레드와 멀티스레드
- 스레드의 우선순위
- 스레드 그룹
- 데몬 스레드
- 스레드의 실행제어
- 스레드의 동기화

#### 학습할 것
- 스레드 만드는 방법
- 스레드 실행제어 하는 방법
- 스레드 동기화 방법

#### 학습 내용
1. 스레드(Thread)
	+ 프로세스(Process)와 스레드(Thread)
		+ 프로세스
			+ 자원(메모리)과 스레드로 구성되어 있음
			+ **모든 프로세스는 최소한 하나의 스레드를 가지고 있음**
		+ 스레드
			+ **프로그램(프로세스)안에 실행되는 흐름 단위**
			+ 프로세스 안에서 실제 작업을 수행
	+ 싱글 스레드와 멀티 스레드
		+ 싱글 스레드
			+ 장점 : 멀티스레드보다 작업종료시간이 더 빠름
			+ 단점 : 작업이 진행되지 못하게 막혀있으면(I/O 블락킹) 다음 작업을 수행하지 못함
		+ 멀티 스레드
			+ 장점 : 작업을 효율적으로 할 수 있음(여러가지 작업을 할 수 있음)
			+ 단점 : Context Switch(문맥교환)때문에 시간이 더 걸릴 수 있음
	+ 일반 스레드와 데몬 스레드
		+ **실행중인 사용자 스레드가 하나도 없을 때 프로그램은 종료됨**
		+ 일반 스레드(사용자 스레드) :
			+ 데몬 스레드가 아닌 스레드
			+ main 스레드도 포함됨
				+ main 스레드 : main메서드의 코드를 수행하는 스레드
		+ 데몬 스레드
			+ 일반 스레드의 작업을 도와주는 스레드
			+ 일반 스레드 모두 종료되면 자동으로 종료됨
			+ 무한루프와 조건문을 이용해서 실행 후 대기하다가 특정조건이 되면 작업을 수행하고 다시 대기하도록 작성함
	+ 스레드 만드는 방법
		+ **Thread 클래스 상속**
		+ **Runnalbe 인터페이스 구현**
		```
		// 방법1) Thread 클래스 상속
		Thread1 t1 = new Thread1();
		t1.setName("이것은 스레드 테스트1");
		t1.setPriority(1);
		t1.start();
		
		// 방법2) Runnalbe 인터페이스 구현
		Runnable run = new Thread2();
		Thread t2 = new Thread(run);
		t2.start();
		
		Thread t2 = new Thread(new Thread2());
		t2.start();
		```
	+ 스레드의 상태   
	![스레드상태](https://velog.velcdn.com/images%2Fjsj3282%2Fpost%2F07158899-9d27-4797-a934-fc71df2f6475%2F%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C.png)
	+ 스레드의 우선순위
		+ 우선순위에 따라서 얻을 수 있는 실행시간이 달라짐
		+ 1~10까지 가능하며 숫자가 높을수록 실행시간이 길어짐
		+ main()에서 실행되는 스레드는 기본적으로 우선순위 `5`로 되어 있음
		+ **OS스케쥴러의 영향을 많이 받기 때문에 차라리 PriorityQueue에 저장하는 것이 더 명확할 수 있으며 반드시 우선순위대로 실행되겠다는 생각을 하면 안됨**
	+ 스레드 그룹
		+ **모든 스레드는 그룹이 있어야하며** JVM에서 main, system 이라는 그룹을 자동으로 만들어줌
		+ 그룹을 지정하지 않으면 자동적으로 main 스레드 그룹으로 지정됨
		+ 보안상의 이유로 도입되었으며 자신이 속한 스레드 그룹, 하위 스레드 그룹만 변경 할 수 있음
		```
		public class TestThread {
		    private static Logger logger = LogManager.getLogger(TestThread.class);
		    
		    static class Thread4 extends Thread {
		        @Override
		        public void run() {
		            logger.debug("ID : {} / Name : {} / Priority : {} / ThreadGroup : {}", getId(), getName(), getPriority(), getThreadGroup());
		        }
		    }
		    
		    static class Thread5 implements Runnable {
		        @Override
		        public void run() {
		            logger.debug("ID : {} / Name : {} / Priority : {} / ThreadGroup : {}", Thread.currentThread().getId(), Thread.currentThread().getName(), Thread.currentThread().getPriority(), Thread.currentThread().getThreadGroup());
		        }
		    }
		        
		    public static void test3() {
		        ThreadGroup tGroup1 = new ThreadGroup("그룹1");
		        tGroup1.setMaxPriority(4);
		        
		        ThreadGroup tGroup2 = new ThreadGroup(tGroup1, "서브그룹");
		        tGroup2.setMaxPriority(3);
		        
		        Thread t4 = new Thread(tGroup1, new Thread4(), "스레드 그룹 테스트1");
		        t4.start();
		        
		        Thread t5 = new Thread(tGroup1, new Thread5(), "스레드 그룹 테스트2");
		        t5.start();
		        
		        Thread t6 = new Thread(tGroup2, new Thread5(), "스레드 그룹 테스트3");
		        t6.start();
		        
		        // 스레드 그룹 정보 출력
		        // getAllStackTraces() : 실행되는 모든 스레드 정보를 가져옴
		        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
		        for (Thread item : map.keySet()) {
		            System.out.println(item); 
		        }
		    }
		    
		    public static void main(String[] args) {
		        test3();
		    }
		}
		
		[실행결과]
		Thread[스레드 그룹 테스트3,3,서브그룹]
		Thread[스레드 그룹 테스트1,4,그룹1]
		Thread[Finalizer,8,system]
		Thread[main,5,main]
		Thread[Attach Listener,5,system]
		Thread[스레드 그룹 테스트2,4,그룹1]
		Thread[Reference Handler,10,system]
		Thread[Signal Dispatcher,9,system]
		ID : 17 / Name : 스레드 그룹 테스트3 / Priority : 3 / ThreadGroup : java.lang.ThreadGroup[name=서브그룹,maxpri=3]
		ID : 16 / Name : 스레드 그룹 테스트2 / Priority : 4 / ThreadGroup : java.lang.ThreadGroup[name=그룹1,maxpri=4]
		ID : 14 / Name : Thread-1 / Priority : 5 / ThreadGroup : java.lang.ThreadGroup[name=main,maxpri=10]
		```
    + 스레드의 실행제어
	    + start()
		    + **실행대기(RUNNABLE) 상태로 스레드를 변경함**
		    + 새로운 호출스택 생성하고 run() 호출
	    + sleep() 
		    + 현재 실행중인 스레드를 일정 시간 동안 일시정지함
		    + **특정 스레드를 멈추게 하는것은 불가능**
		+ interrupt()
			+ 대기(WAITING) 상태인 스레드를 실행대기(RUNNABLE) 상태로 변경함
	    + yield() 
		    + 할당받은 실행시간을 다른 스레드로 양보함
	    + join() 
		    + 다른 스레드가 종료될때까지 기다림
    + 스레드의 동기화
	    + **임계 영역(Critical Section) : 공유 데이터를 사용하는 코드 영역, 락(Lock)을 얻은 단 한개의 스레드만 들어올 수 있음**
	    + **스레드의 동기화 : 임계 영역을 다른 스레드가 간섭하지 못하게 하는것**
			+ 동기화 방법
				+ synchronized 이용
					+ 한 스레드만 들어 갈 수 있으므로 최소화 하는것이 좋음
					 ```
					방법1) 메서드 전체를 지정
					public synchronized void test() { }
					
					방법2) 특정 영역을 지정 
					synchronized { }
					```
				+ Lock과 Condition 이용
			+ **동기화를 효율적으로 하기 위해서 wait(), notify() 사용**
				+ wait() : Lock을 풀고 스레드를 waiting pool에 넣음
				+ notify() : waiting pool에서 대기중인 스레드 중에 하나만 깨움
				+ notifyAll() : waiting pool에서 대기중인 모든 스레드를 깨움

> start()와 run()의 차이
> + run() 메서드는 단순히 `Thread 클래스의 메서드를 호출`하는 것뿐이고,
> + start() 메서드는 `새로운 호출스택을 만들고 만들어진 호출스택에서 run() 메서드를 호출`함
> ```
> static class Thread1 extends Thread {
>     @Override
>     public void run() {
>         for (int i=0; i<100; i++) {
>             System.out.print("+");
>         }
>     }
> }
> public static void main(String[] args) {
>     // 싱글 스레드와 동일함
>     Thread1 t1 = new Thread1();
>     t1.run();
>     
>     Thread1 t2 = new Thread1();
>     t2.run();
>     
>     // 멀티 스레드
>     Thread1 t1 = new Thread1();
>     t1.start();
>     
>     Thread1 t2 = new Thread1();
>     t2.start();
> }
> ```
> ![start_run_차이](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbzvGRx%2FbtrZKo8nnYe%2FnnKSYV7koxJsUekwiS5Pd1%2Fimg.png)

#### 출처(참고문헌)
 - Java의 정석
 - https://hamait.tistory.com/612
 - https://velog.io/@jsj3282/Thread%EC%9D%98-%EC%83%81%ED%83%9C

#### 각주
