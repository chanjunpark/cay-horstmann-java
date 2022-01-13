# cay-horstmann-java
'카이 호스트만의 코어 자바8(길벗)' 을 읽으며 학습한 내용을 정리한 레퍼지토리 입니다. 블로그에 정리한 내용이 있는 경우 해당 챕터에 링크를 포함해 놨습니다.
***
## Chapter 2. Object Oriented Programming
- 인스턴스 변수는 지역 변수와 달리 명시적으로 초기화하지 않아도 된다.
- 패키지는 주로 클래스 이름의 유일성을 보장하기 위해 사용된다.
- 중첩클래스 : 가시성을 제한하거나 Element, Node, Item 등 일반적인 이름을 쓰면서 정돈된 상태를 유지하기에 유용하다.
- 내부클래스 : static을 붙이지 않은 중첩클래스를 내부클래스라고 한다.
  - static 중첩클래스와의 근본적 차이는 자신을 생성한 외부 클래스의 객체에 대한 참조가 포함된다는 점이다. 
  - 예를 들어, Network 안에 포함된 Member 객체는 자신이 어느 네트워크에 속하는지(어느 네트워크 객체에서 본인이 생성된지) 알게 된다.
  - 이에, 내부클래스(Inner class)를 비정적 중첩 클래스라고도 한다.
***
## Chapter 3. Interface & Lambda expression
#### 인터페이스
- 인터페이스를 이용하면 구현하지 않고도 수행해야 할 일을 명시할 수 있다. -> 객체 지향의 핵심
- 서브 타입의 모든 값을 변환 없이 슈퍼타입 변수에 할당할 수 있으면 타입 S는 타입 T의 슈퍼타입이다.
- 인터페이스는 또 다른 인터페이스를 확장해서 원래 있던 메서드 외의 추가 메서드를 제공할 수 있다.
- 클래스는 인터페이스를 몇 개든 구현할 수 있다.
#### 정적 메서드와 기본 메서드
- 자바8은 인터페이스에 실제 구현이 있는 *메서드(정적메서드와 기본메서드)* 를 추가할 수 있다.
  ```java
  public interface IntSequence{
    default boolean hasNext() { return true; }
    int next();
  } 
  ```
- 기본 메서드에는 반드시 default 제어자를 붙여야 한다.
  - 기본 메서드의 중요한 용도는 인터페이스를 진화시키는 것이다. 
  - 인터페이스에 기본 메서드가 아닌 메서드를 추가하는 일은 소스 수준에서 호환되지 않으며(컴파일 불가), 바이너리 수준에서 호환된다.
  - 예를 들어, 자바8은 Collection 인터페이스에 stream 메서드를 추가했다. 만약 stream 메서드가 기본메서드가 아니었다면 Collection 인터페이스를 구현한 Bag 클래스는 새로운 메서드를 구현하지 않았으므로 컴파일 되지 않는다. 그러나 프로그램에서는 여전히 Bag 인스턴스를 생성할 수 있다. 하지만 프로그램의 Bag 인스턴스에서 stream 메서드를 호출하면 AbstractMethodError가 일어난다.
  - 메서드를 default로 선언하면 이런 문제를 해결할 수 있다. 메서드를 기본 메서드를 만들면 Bag 클래스의 컴파일이 가능해지고 인스턴스에서 stream 메서드를 호출하더라도 Collection.stream 메서드가 호출된다.
    ```java
    public class Bag implements Collection
    ```
- 클래스에서 인터페이스 두 개를 구현하려면 기본 메서드를 작성하거나 이름과 파라미터 타입이 같은 메서드(기본이든 아니든 무관)를 작성할 때 반드시 충돌을 해결해야 한다.
  - 예를 들어, 아래 예에서 위임을 하지 않는 경우 자바 컴파일러는 두 메서드 중 하나를 우선해서 선택할 수 없고, 오류가 발생한다.
  - Employee 클래에서 getID를 작성하여 고유의 ID 체계를 구축하거나, 충돌한 메서드 중 하나로 위임해야 한다.
    ```java
    public interface Person {
      String getName();
      dafault int getId() { return 0; }
    }

    public interface Identified {
      dafault int getId() { return Math.abs(hashCode()); }
    }

    public class Employee implements Person, Identified{
      public int getID() { return Identified.super.getID(); } // 둘 중 하나로 위임하여 해결 가능
    }
    ```
  - 만약 두 메서드 중 하나만 기본 메서드로 구현되어 있다면, 기본 메서드를 상속 받을까? 아니다. 컴파일러는 오류를 보고하며, 모호성을 해결하는 일은 개발자의 책임이다.
#### 인터페이스의 예
- 이 책에서 예를 든 인터페이스에는 Comparable, Comparator, Runnable 등이 있다.
- Runnable
  - 특정 작업을 별도의 스레드에서 실행하거나 실행 스레드 풀에 넣으려고 할 때, 이러한 작업을 정의하려면 Runnable 인터페이스를 구현해야 한다. Runnable 인터페이스는 메서드를 한 개만 갖는다.
  ```java
  Class HelloTask implements Runnable {
    public void run() {
      for ( int i = 0; i < 1000; i++) {
        System.out.println("Hello, World!"
      }
    }
  }
  
  Runnable Task = new HelloTask();
  Thread thread = new Thread(task);
  thread.start();
  ```
#### 람다 표현식
- 자바에는 함수 타입이 없다. 대신 함수를 객체로 표현한다. 다시 말해, 특정 인터페이스를 구현하는 클래스의 인스턴스로 표현한다. 람다표현식은 이런 인스턴스를 생성하는 편리한 문법을 제공한다.
- 람다 표현식 문법은 다음과 같다.
```java
(String first, String second) -> first.length() - second.length();
```
- 람다 표현식의 결과 타입은 명시하지 않는다. 하지만 컴파일러는 구현부로부터 결과 타입을 추론해서 기대하는 타입과 일치하는지 검사한다.
- 람다 표현식은 추상 메서드가 한 개만 포함된 인터페이스에만 사용할 수 있는데, 이러한 인터페이스를 함수형 인터페이스라고 한다.
  - Runnable이나 Comparator처럼 액션을 표현하는 인터페이스와 호환된다.
  - 함수형 인터페이스 변환 예시) Arrays.sort 메서드의 두 번째 파라미터는 Comparator 인터페이스의 인스턴스가 필요하다. Comparator의 메서드가 하나만 있다. 이 때 두 번째 파라미터로 다음과 같이 람다를 전달할 수 있다.
  ```java
  Arrays.sort(words, (first, second) -> first.length() - second.length());
  ```
- Object 타입은 자바에서 모든 클래스의 슈퍼타입인데, 람다 표현식은 Object 타입 변수에 저장할 수 없다. Object는 함수형 인터페이스가 아니라 클래스이기 때문이다.
#### 메서드 참조와 생성자 참조





***
## Chapter 7. Collection
#### 컬렉션 프레임워크 개요

