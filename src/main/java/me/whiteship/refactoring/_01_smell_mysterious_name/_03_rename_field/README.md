
# 리팩토링 3. 필드 이름 바꾸기 (Rename Field)
> 필드의 경우 사용되는 범위가 굉장히 넓기 때문에 이름이 중요
> 
> Record 자료 구조의 필드 이름은 프로그램 전반에 걸쳐 참조될 수 있기 때문에 매우 중요
> 

## 1. 필드의 이름이 모호할 경우 
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
usernames, reviews 필드만 보았을 때 username의 user가 무엇인지, 그리고 reviews가 어떠한 리뷰인지 전혀 알 수가 없다. 

아래 코드를 전부 읽어보면 어느용도로 사용되는지 파악할 수 있지만 소스코드가 복잡할 경우 해당 필드가 무엇을 의미하는지 알기 어려울 수 있다.


## 2. 필드의 이름이 명확할 경우 
```java
public class StudyDashboard {

    private Set<StudyReview> studyReviews = new HashSet<>();

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
            studyReviews.add(new StudyReview(review.getUserName(), review.getBody()));
        }
    }

    public Set<StudyReview> getStudyReviews() {
        return studyReviews;
    }

    public static void main(String[] args) throws IOException {
        StudyDashboard studyDashboard = new StudyDashboard();
        studyDashboard.loadReviews();
        studyDashboard.getStudyReviews().forEach(System.out::println);
    }
}

public record StudyReview(String reviewer, String review) { }
```
usernames와 reviews는 결국 리뷰의 작성자명과 리뷰를 저장하고 있으므로 리뷰와 작성자를 한 레코드로 묶은다음 해당 레코드 타입의 Set을 필드에 추가하였다. 
여기서 StudyReview 레코드 필드명을 살펴보면 username이 아닌 reviewer로 변경된 것을 확인할 수 있다. review는 기존과 동일하지만 레코드명이 StudyReview 이므로 해당 리뷰가 Study에 관련된 리뷰라는 것을 알 수 있다. 

## 3. 결론
이처럼 필드명이 명확할 경우 변수명이 명확할 때와 동일하게 해당 필드가 무엇을 의미하는 지 소스코드를 분석하지 않고도 알 수 있는 장점이 있다. (필드의 의미가 무엇인지 명확해진다.)

<br>

💡 [자바 레코드 관련](https://www.baeldung.com/java-record-keyword)


