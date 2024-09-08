# Three Lines Economy API

세줄경제 스프링 부트 API 서버입니다.

## 💻 Coding Convention

<details>
 <summary> 🗂 패키지 구조 </summary>
 <div markdown="1">       

---

- 프로젝트 의존성 관리 방법 참조
  https://geminikims.medium.com/%EC%A7%80%EC%86%8D-%EC%84%B1%EC%9E%A5-%EA%B0%80%EB%8A%A5%ED%95%9C-%EC%86%8C%ED%94%84%ED%8A%B8%EC%9B%A8%EC%96%B4%EB%A5%BC-%EB%A7%8C%EB%93%A4%EC%96%B4%EA%B0%80%EB%8A%94-%EB%B0%A9%EB%B2%95-97844c5dab63

```markdown
🗂 .github

🗂 .run

🗂 common

🗂 {module}

    🗂 {module}-application

        🗂 src/main/kotlin/com/tle
    
            - support 🗂
        
                - exception 🗂
        
                - config 🗂
        
                - dto 🗂
        
                - enums 🗂
        
                - security 🗂
        
            - application 🗂
        
                - domain-1 🗂
    
                    - controller, request, response, etc..
                    
                    - if amount of package is too big, you can divide them into sub-packages with specific conceptualization.
    
                - domain-2 🗂
    
                - domain-3 🗂

        🗂 src/test/kotlin/com/tle

            - support 🗂
        
                - config 🗂
        
                - mock 🗂
        
            - application 🗂
        
                - domain-1 🗂
    
                    - same path with production package

                - domain-2 🗂
    
                - domain-3 🗂
    
    🗂 {module}-domain

        🗂 src/main/kotlin/com/tle
        
            - support 🗂
        
                - exception 🗂
        
                - config 🗂
        
                - dto 🗂
        
                - enums 🗂
        
            - domain 🗂
        
                - domain-1 🗂
    
                    - service, repository, entity, dto, exception, etc..
                    
                    - if amount of package is too big, you can divide them into sub-packages with specific conceptualization.
    
                - domain-2 🗂
    
                    - domain-3 🗂

        🗂 src/test/kotlin/com/tle

            - support 🗂
        
                - config 🗂
        
                - mock 🗂
        
            - domain 🗂
        
                - domain-1 🗂
    
                    - same path with production package                
    
                - domain-2 🗂
    
                - domain-3 🗂

    🗂 {module}-storage

        🗂 src/main/kotlin/com/tle
        
            - support 🗂
        
                - config 🗂
        
                - dto 🗂
        
                - enums 🗂
        
            - storage 🗂
        
                - domain-1 🗂
    
                    - service, repository, entity, dto, exception, etc..
                    
                    - if amount of package is too big, you can divide them into sub-packages with specific conceptualization.
    
                - domain-2 🗂
    
                    - domain-3 🗂

        🗂 src/test/kotlin/com/tle

            - support 🗂
        
                - config 🗂
        
                - mock 🗂
        
            - storage 🗂
        
                - domain-1 🗂
    
                    - same path with production package                
    
                - domain-2 🗂
    
                - domain-3 🗂

build.gradle

settings.gradle

gradlew

etc..
```

- 기본적인 프로젝트의 패키지 구조입니다.
- `common` : 공통으로 사용되는 패키지
    - 공통으로 사용되는 설정 및 클래스들이 위치하는 패키지입니다.
    - 하위에 상황에 맞는 패키지 구조를 추가할 수 있습니다.
- `module` : 비즈니스 도메인 단위 모듈
    - `module-application` : API 컨트롤러 모듈
        - 컨트롤러 레이어, 시큐리티, DTO, 예외 처리 등 API 관련 모듈
        - 요청과 응답은 반드시 application 모듈에서 정의합니다.
    - `module-domain` : 비즈니스 로직 모듈
        - 비즈니스 도메인 단위로 하나의 패키지에서 구성합니다.
    - `module-storage` : 데이터 저장소 모듈
        - 데이터 저장소와 관련된 로직을 처리하는 모듈
        - 비즈니스 도메인 단위로 하나의 패키지에서 구성합니다.
    - 패키지가 너무 비대해진 경우 하위에 구체적인 개념화를 통해 패키지 구조를 추가할 수 있습니다.

    <br>

 </div>
 </details>


<details>
<summary> 🖋 네이밍 </summary>
<div markdown="1">       


---

**Class & Contructor**

- Class, Contructors는 **Pascal Case (=UpperCamelCase)**를 사용합니다.
- Class, Contructors에는 복수형을 사용하지 않습니다.

  <kbd>좋은 예</kbd>

    ```java
    CamelCase
    ```

  <kbd>나쁜 예</kbd>

    ```java
    camelCase
    ```

- 모든 클래스는 계층에 따라 suffix를 붙여야합니다.
    - Presentation Layer
        - `Controller`, `Response`, `Request`, etc..
    - Business Layer
        - `Service`, etc..
    - Implementation Layer
        - `Writer`, `Reader`, `Remover`, `Validator`, `Dto` etc..
        - 도메인 엔티티는 `Entity`를 붙이지 않습니다.
        - 도메인 엔티티에 책임 위임이 가능하다면 비즈니스 로직은 도메인 엔티티에 작성합니다.
    - Data Access Layer
        - `Repository`, `Entity`, etc..

        <br/>

**함수 & 변수 & 상수**

- 함수와 변수에는 **lowerCamelCase**를 사용합니다.

- 함수의 경우 **동사+명사**형태로 구성합니다.
    - ex) getUserInformation()

- 글자의 길이
    - 글자의 길이는 **20자 이내**로 제한합니다.
    - 4단어 이상이 들어가거나, 부득이하게 20자 이상이 되는 경우에는 **팀원과의 상의**를 거쳐야 합니다.

- flag로 사용되는 변수
    - Boolean의 경우 **조동사+flag** 종류로 구성합니다.
    - ex) isNum, hasNum

- 기본적인 데이터 핸들링에서는 Array 보다는 List를 지향합니다.
    - List를 사용하면 데이터의 추가, 삭제, 검색 등이 편리합니다.
    - ex) List<String> list = new ArrayList<>();

- 약칭의 사용
    - 약어는 되도록 사용하지 않습니다.

  <kbd>좋은 예</kbd>

    ```java
    String index;
    int count;
    List<String> list;
    boolean seoulToBucheon;
    ```

  <kbd>나쁜 예</kbd>

    ```java
    String idx;
    int cnt;
    String[] arr;
    boolean seoul2Bucheon;
    ```

<br>

</div>
</details>



 <details>
 <summary> 🏷 주석 </summary>
 <div markdown="1">       

---

- 한줄은 `//`로 적고, 그 이상은 `/** */`로 적습니다.

 ```java
 // 한줄 주석일 때
/**
 * 여러줄
 * 주석일 때
 */
 ```

- 기본적으로 주석은 남기지 않습니다.
    - 일반적으로 주석은 유지보수가 어렵기 때문입니다.
    - 주석의 대체 도구로 Git commit 메세지, 그리고 문서가 있습니다.
    - 현재 코드 상에서 작업 또는 리팩토링 해야하는 무언가를 기술부채로써 보류해둘 때는 TODO 주석을 권장합니다.
    - 특정 로직을 deprecate시키려는 경우 @Deprecated 어노테이션을 이용해주세요.
    - 클린 코드만으로 설명이 어려운 경우, 특히 **도메인 지식이 많이 요구되는 경우 등에는 주석을 남겨주세요**.

 <br>

 </div>
 </details>


<details>
<summary> 📎 기타 </summary>
<div markdown="1">       


---

- 탭 사이즈는 4로 사용합니다.
    - intellij 설정에서 indent를 검색하면 설정할 수 있습니다.
- 한 줄의 최대 길이는 80자로 제한합니다.
- 괄호 사용
    - (if, while, for)문 괄호 뒤에 한칸을 띄우고 사용합니다.
  ```java
     if (left == true) {
	   // logic
     }
     ```

- 띄어쓰기
  ```java
  val a = 5;  ( = 양쪽 사이로 띄어쓰기 하기)
  if (a == 3) {
	  // logic
  }
  ```
- 유용한 설정
    - inlay hints: 변수 타입 미리보기
      ![img.png](image/img.png)
    - Actions on Save: 저장(Ctrl+s)시 액션
      ![img_1.png](image/img_1.png)

</div>
</details>


</br>

## ✉️ Commit Messge Rules

<details>
<summary> Git Commit Message Rules </summary>
<div markdown="1">       


---

- 반영사항을 바로 확인할 수 있도록 작은 기능 하나라도 구현되면 커밋을 권장합니다.
- 기능 구현이 완벽하지 않을 땐, 각자 브랜치에 커밋을 해주세요.
  <br>

### 📜 커밋 메시지 명령어 모음

```
- feat    : 기능 (새로운 기능)
- fix     : 버그 (버그 수정)
- refactor: 리팩토링
- style   : 스타일 (코드 형식, 세미콜론 추가: 비즈니스 로직에 변경 없음)
- docs    : 문서 (문서 추가, 수정, 삭제)
- test    : 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없음)
- chore   : 기타 변경사항 (빌드 스크립트 수정 등)
```

<br>

### ℹ️ 커밋 메세지 형식

- `[커밋메세지] 설명` 형식으로 커밋 메시지를 작성합니다.

좋은 예 >

```
  [Feat] 메인뷰 조회 API 구현 완료
```

나쁜 예 >

```
  메인뷰 API 구현 성공
```

</div>
</details>
<br>

## <img width=3% img src="https://user-images.githubusercontent.com/63224278/124635517-7ef5ed00-dec2-11eb-9a42-6d6d5cc72dce.png" /> Github mangement

<details>
<summary> Gitflow </summary>
<div markdown="1">       


---

- 기본적으로 GitFlow 전략을 사용합니다.
- main 브랜치
- develop 브랜치
- default는 develop브랜치입니다.
- 기능 개발시 → develop 브랜치 하위에 `feat/제목` 으로 브랜치를 파서 관리합니다.
- 기능 개발이 완료되면 develop 브랜치로 Pull Request를 올립니다.
- 다른 팀원이 pr을 확인하고, 코드리뷰를 진행한 뒤 문제가 없으면 develop 브랜치에 병합합니다.
- main 브랜치는 배포용 브랜치로 사용합니다.
- 기능 개발 브랜치는 엔드포인트 별 1개의 브랜치를 원칙으로 합니다. PR은 하위 브랜치를 만들어 올립니다.

<br>

```
- main
  - hotfix/제목
  - develop
    - feat/제목
    - fix/제목
    - chore/제목
```

<br>

**각자 자신이 맡은 기능 구현에 성공시! 브랜치 다 쓰고 병합하는 방법**

- 브랜치 만듦

```bash
git branch 기능(or 이름 브랜치)
```

- 원격 저장소에 로컬 브랜치 push

```bash
git push --set-upstream origin 브랜치이름(feat/제목)
```

```bash
git push -u origin 브랜치이름(feat/제목)
```

- 브랜치 전환

```bash
git checkout feat/제목
```

- 코드 변경 (현재 **feat/제목** 브랜치)

```bash
git add .
git commit -m "커밋 메세지" origin feat/제목
```

- 푸시 (현재 **feat/제목** 브랜치)

```bash
git push origin feat/제목
```

- feat 브랜치에서 할 일 다 했으면 **개인** 브랜치로 전환

```bash
git checkout {name}
```

- 머지 (현재 **개인** 브랜치)

```bash
git merge feat/제목
```

- 다 쓴 브랜치 삭제 (local) (현재 **개인** 브랜치)

```bash
git branch -d feat/제목
```

- 다 쓴 브랜치 삭제 (remote) (현재 **개인** 브랜치)

```bash
git push origin :feat/제목
```

- main pull (현재 **develop** 브랜치)

```bash
git pull or git pull origin develop
```

- main push (현재 **develop** 브랜치)

```bash
git push or git push origin develop
```

</div>
</details>
