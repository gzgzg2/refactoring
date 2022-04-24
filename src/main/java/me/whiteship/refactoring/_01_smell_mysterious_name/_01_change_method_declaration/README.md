# 리팩토링 1. 함수 선언 변경하기 (Change Function Declaration)
> 좋은 이름을 가진 함수는 함수의 이름만 보고도 함수의 역할을 이해할 수 있다.
> 
> 함수의 이름을 명확하게 변경했을 때와 그렇지 않을 때를 비교하여 해당 리팩토링의 장점을 알아보쟈 
> 
> ***참고로 README의 내용은 스스로 작성한 예제코드이기 때문에 내용이 살짝 부실할 수 있다..***

## 1. 메소드의 이름이 명확하지 않을 경우
```java
public class MessageSender {

    private String title;
    private String message;
    private String source;
    private String destination;

    public MessageSender(String title, String message, String source, String destination) {
        this.title = title;
        this.message = message;
        this.source = source;
        this.destination = destination;
    }

    public void send() {
        if(checkDestination()) {
            System.out.printf("제목: %s, 내용: %s, 발신처: %s, 수신처: %s",  this.title, this.message, this.source, this.destination);
        }
    }

    //메세지의 목적지가 비어있는 지 확인한다.
    public boolean checkDestination() {
        return !destination.isEmpty();
    }
}
```
로직이 간단해서 이해는 쉬웠을 수 있다. 하지만 메소드명만 보았을 때 `checkDestination()` 메소드명이 명확하지 않아 어떠한 경우에 메세지를 전송하는지 알 수 없다.



## 2. 메소드의 이름이 명확한 경우
```java
public class MessageSender {

    private String title;
    private String message;
    private String source;
    private String destination;

    public MessageSender(String title, String message, String source, String destination) {
        this.title = title;
        this.message = message;
        this.source = source;
        this.destination = destination;
    }

    public void send() {
        if(!isEmptyDestination()) {
            System.out.printf("제목: %s, 내용: %s, 발신처: %s, 수신처: %s", this.title, this.message, this.source, this.destination);
        }
    }

    private boolean isEmptyDestination() {
        return !destination.isEmpty();
    }

    public static void main(String[] args) {
        MessageSender messageSender = new MessageSender("안녕", "오랜만", "발신처", "수신처");
        messageSender.send();
    }
}
```
`checkDestination()` 메소드를 `isEmptyDestination()` 로 변경하였다. 메소드 명에서 비어있는 Destination을 확인한다는 것을 알 수 있기에 
`checkDestination()`선언부 위에 작성되어 있던 주석도 필요가 없어졌다. 

이처럼 함수의 선언을 변경하여 파라미터나 메소드명이 명확해지면 불필요한 주석이 제거되고 이해하기 쉬운 코드를 작성할 수 있다는 장점이 있다. 
