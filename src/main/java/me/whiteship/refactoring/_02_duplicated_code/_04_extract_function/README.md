
# 리팩토링 4. 함수 추출하기 (Extract Function)
> - `의도`와 `구현` 분리하기
> - 무슨 일을 하는 코드인지 알아내려고 노력해야 하는 코드라면 해당 코드를 함수로 분리하고 함수 이름으로 "무슨 일을 하는지" 표현할 수 있다.
> - 거대한 함수 안에 들어있는 주석은 추출한 함수를 찾는데 있어서 좋은 단서가 될 수 있다.   
> 

## 1. 거대한 함수(추출 전)
```java
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
```
해당 클래스를 살펴보면 중복되는 코드 뿐만 아니라 하나의 함수 안에서 여러 역할을 구현하고 있다. 

이 경우 함수를 이해하려면 구현내용을 전부 분석해야한다. 또한 구현을 설명하기 위해 불필요한 주석이 적혀있는 것을 볼 수 있다. 

구현을 나타내는 코드는 추출하여 중복과 불필요한 주석을 제거하고 이해하기 쉽게 변경해야 한다. 



## 2. 함수의 구현을 의도로 표현하기(추출 후)
```java
 private void printParticipants(int eventId) throws IOException {
        GHIssue issue = getGhIssue(eventId);
        Set<String> participants = getUserNames(issue);
        print(participants);
    }

    private void printReviewers() throws IOException {
        GHIssue issue = getGhIssue(30);
        Set<String> reviewers = getUserNames(issue);
        print(reviewers);
    }

    private void print(Set<String> participants) {
        participants.forEach(System.out::println);
    }

    private Set<String> getUserNames(GHIssue issue) throws IOException {
        Set<String> participants = new HashSet<>();
        issue.getComments().forEach(c -> participants.add(c.getUserName()));
        return participants;
    }

    private GHIssue getGhIssue(int eventId) throws IOException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        GHIssue issue = repository.getIssue(eventId);
        return issue;
    }


    public static void main(String[] args) throws IOException {
        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.printReviewers();
        studyDashboard.printParticipants(15);
    }

```

구현을 분리하여 의도를 표현하였다. 리팩토링한 메서드를 살펴보면 불필요한 주석이 전부 제거되었다.

추출한 메서드명으로 메서드의 행동을 예측할 수 있기 때문이다. 메서드명은 메서드의 행위를 예측할 수 있는 아주 중요한 수단이다. 

뿐만 아니라 개발자가 메서드의 구현을 따로 분석해야하는 수고가 사라졌다. 


