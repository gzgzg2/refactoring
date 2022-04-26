
# 리팩토링 2. 변수 이름 바꾸기 (Rename Variable)
> 자주 사용되는 변수일수록 변수의 이름이 중요하다
>   - 람다식에서 사용하는 변수와 함수의 매개변수의 차이 
> 여러 함수에 걸쳐 쓰이는 필드 이름에는 더 많은 고민을하고 이름을 지어야한다.  
> 

## 1. 변수의 이름이 모호할 경우 
```java
public class StudyDashboard {

    private Set<String> usernames = new HashSet<>();

    private Set<String> reviews = new HashSet<>();

    /**
     * 스터디 리뷰 이슈에 작성되어 있는 리뷰어 목록과 리뷰를 읽어옵니다.
     * @throws IOException
     */
    private void loadReviews() throws IOException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        GHIssue issue = repository.getIssue(30);

        List<GHIssueComment> comments = issue.getComments();
        for (GHIssueComment comment : comments) {
            usernames.add(comments.getUserName());
            reviews.add(comments.getBody());
        }
    }

    public Set<String> getUsernames() {
        return usernames;
    }

    public Set<String> getReviews() {
        return reviews;
    }

    public static void main(String[] args) throws IOException {
        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.loadReviews();
        studyDashboard.getUsernames().forEach(System.out::println);
        studyDashboard.getReviews().forEach(System.out::println);
    }
}
```
특별히 문제점이 있다고 생각되지는 않는다. 하지만 `loadReviews()` 메소드를 살펴보면 분명 Review를 읽어오는 작업임에도 Review와 관련된 변수가 존재하지 않는다.
로직을 이해하는데에 있어 큰 문제는 아니지만 그래도 명확한 변수명이 모호한 변수명보다 코드 이해가 쉬울 것이다



## 2. 변수의 이름이 명확할 경우 
```java
public class StudyDashboard {

    private Set<String> usernames = new HashSet<>();

    private Set<String> reviews = new HashSet<>();

    /**
     * 스터디 리뷰 이슈에 작성되어 있는 리뷰어 목록과 리뷰를 읽어옵니다.
     * @throws IOException
     */
    private void loadReviews() throws IOException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        GHIssue issue = repository.getIssue(30);

        List<GHIssueComment> reviews = issue.getComments();
        for (GHIssueComment review : reviews) {
            usernames.add(review.getUserName());
            this.reviews.add(review.getBody());
        }
    }

    public Set<String> getUsernames() {
        return usernames;
    }

    public Set<String> getReviews() {
        return reviews;
    }

    public static void main(String[] args) throws IOException {
        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.loadReviews();
        studyDashboard.getUsernames().forEach(System.out::println);
        studyDashboard.getReviews().forEach(System.out::println);
    }
}
```
해당 예제에선 issue에 남겨진 comment가 후기를 의미하는 것이다. 변경전 변수명으론 issue의 comment가 무엇을 의미하는 지 확인하려면 for문 아래 로직을 분석해야만 했다. 
하지만 변수명이 변경되고 나서는 comment가 리뷰임을 명시하고 있으므로 for문 아래의 로직을 전부 분석하지 않고도 `issue.getComments()` 의 리턴값이 무엇을 의미하는 지 알 수 있게 되었다.
추가적으로 기존에 필드에 선언되어있던 reviews와 변경한 변수명이 동일하기 때문에 필드에 선언된 변수임을 명시하기 위해 this 키워드를 붙여주었다.
