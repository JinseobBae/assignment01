# 사전과제

# 핵심 문제해결 전략
 - ### 동시 요청에 대한 처리
    - JPA의 비관적 잠금을 이용 (PESSIMISTIC_READ) 
  
 - ### 엔티티 구성
    - 엔티티는 총 3개로 구성(상품, 상품상태, 투자이력)
    
 - ### Test Coverage 90%  
    - root의 test.sh로 통합테스트
    
 
# 요구사항 분석내용
 1. ### 전체 투자 상품 조회 API
    - 별도의 파라미터는 필요하지 않다.
    - 요청 시점기준으로 모집기간 내의 상품만 보여준다.
    - 모집중, 모집종료 상품 모두 보여준다.
    
 2. ### 투자하기 API
    - 헤더값으로 사용자 ID를 받는다.
    - 투자상품과 투자금액은 파라미터로 전달받는다.
    - 동시에 여러명이 투자가능하므로 데이터에 대한 보장이 필요하다.   
    - [개인정의] 한 명의 사용자가 여러번 투자 가능하다.
    - 해당 상품이 없으면 투자가 불가능하다.(NoinvestingItemException)
    - [개인정의] 최소 투자금액은 1원 이상이다. (MinimumAmountException)
    - 이미 모집완료된 상품에는 투자가 불가능하다. (AlreadySoldOutException)
    - [개인정의] 투자가능금액보다 높은 금액은 투자가 불가능하다. (AmountExceedException)
    
 3. ### 나의 투자상품 조회 API
    - 헤더값으로 사용자 ID를 받는다.
    - 사용자가 투자한 이력을 모두 보여준다.


# API 정의서
 1. ### 전체 투자 상품 조회 API
    - 요청 Url : /v1/investing
    - 요청 Method : GET  
    
 결과값

| 필드명 | 항목 | 설명 |
| --- | --- | --- |
| itemId | 상품ID | |
| itemTitle | 상품제목 | |
| totalInvestingAmount | 총 모집금액 | |
| nowInvestingAmount | 현재 모집금액 | |
| investorAmount | 투자자 수 | |
| itemStatus | 투자항목 상태 | "1" : 모집중 / "0" : 모집완료 |
| startedAt | 투자시작일시| yyyy-MM-dd'T'HH:mm:ss |
| finishedAt | 투자종료일시| yyyy-MM-dd'T'HH:mm:ss |

 2. ### 투자하기 API
    - 요청 Url : /v1/investing
    - 요청 Method : POST
    - 요청 Header 
      - X-USER-ID : 사용자 ID
    - 요청 파라미터(Body)
      - itemId : 상품 ID
      - investingAmount : 투자금액
    
결과값    

| 필드명 | 항목 | 설명 |
| --- | --- | --- |
| resultCode | 투자결과코드 | "1" : 성공 / "0" : 실패 |
| result | 투자결과 | success / failed |


 3. ### 나의 투자상품 조회 API
    - 요청 Url : /v1/my-investing
    - 요청 Method : GET
    - 요청 Header
     - X-USER-ID : 사용자 ID
    
결과값

| 필드명 | 항목 | 설명 |
| --- | --- | --- |
| itemId | 상품ID | |
| itemTitle | 상품제목 | |
| totalInvestingAmount | 총 모집금액 | |
| myInvestingAmount | 나의 투자금액 | |
| investingAt | 투자일시 | yyyy-MM-dd'T'HH:mm:ss|

# 환경
 - JDK8
 - Spring boot 2.4.3
 - H2 Database
 - JPA
 - Embedded Tomcat
 - Gradle


