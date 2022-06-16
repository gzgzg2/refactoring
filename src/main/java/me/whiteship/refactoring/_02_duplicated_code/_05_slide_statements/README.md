
# 리팩토링 5. 코드 정리하기 (Slide Statements)
> - 관련있는 코드끼리 묶여있어야 코드를 더 쉽게 이해할 수 있다. 
> - 함수에서 사용할 변수를 상단에 미리 정의하기 보다는, 해당 변수를 사용하는 코드 바로 위에 선언하자.
> - 관련있는 코드끼리 묶은 다음, 함수 추출하기 (Extract Function)를 사용해서 더 깔끔하게 분리할 수도 있다. 
> 

## 리팩토링 전
```java
public class StudyDashboard {

    private void printParticipants(int eventId) throws IOException {
        // Get github issue to check homework
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        GHIssue issue = repository.getIssue(eventId);

        // Get participants
        Set<String> participants = new HashSet<>();
        issue.getComments().forEach(c -> participants.add(c.getUserName()));

        // Print participants
        participants.forEach(System.out::println);
    }

    private void printReviewers() throws IOException {
        // Get github issue to check homework
        Set<String> reviewers = new HashSet<>();
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        GHIssue issue = repository.getIssue(30);
      
        // Get reviewers
        issue.getComments().forEach(c -> reviewers.add(c.getUserName()));

        // Print reviewers
        reviewers.forEach(System.out::println);
    }

    public static void main(String[] args) throws IOException {
        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.printReviewers();
        studyDashboard.printParticipants(15);

    }

}
```
이 경우 변수를 사용되는 위치가 아닌 최상위에 모두 몰아넣었기 때문에 변수의 사용처를 찾으려면 코드를 모두 분석해야할 것이다. **(코드의 문맥이 섞임)**  
뿐만 아니라 코드를 메서드로 추출할 때도 변수가 어디서 사용되는지 찾은 후에 추출해야하기 때문에 복잡하다.


## 리팩토링 후
```java
public class StudyDashboard {

    private void printParticipants(int eventId) throws IOException {
        // Get github issue to check homework
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        GHIssue issue = repository.getIssue(eventId);

        // Get participants
        Set<String> participants = new HashSet<>();
        issue.getComments().forEach(c -> participants.add(c.getUserName()));

        // Print participants
        participants.forEach(System.out::println);
    }

    private void printReviewers() throws IOException {
        // Get github issue to check homework
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        GHIssue issue = repository.getIssue(30);

        // Get reviewers
        Set<String> reviewers = new HashSet<>();
        issue.getComments().forEach(c -> reviewers.add(c.getUserName()));

        // Print reviewers
        reviewers.forEach(System.out::println);
    }

    public static void main(String[] args) throws IOException {
        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.printReviewers();
        studyDashboard.printParticipants(15);

    }

}
```
최상위에 몰아져있던 변수를 변수가 사용되는 위치로 이동한게 전부인 리팩토링이다. 아주 간단한 리팩토링이지만  
코드를 한 단위로 이해할 수 있게 되고 변수를 최상위에 몰아넣었을 때는 보이지 않던 중복코드를 발견할 수 있게 되었다.  
그리고 중복된 코드나 구현을 표현하고 있는 코드에 `함수 추출하기(Extract Method)` 기법을 적용하기 수월해진다.

