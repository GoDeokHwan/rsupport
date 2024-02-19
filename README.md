# Rsupport 사전과제

## 실행 방법
1. Docker 설치 및 실행
   (설치 방법 : https://analytics4everything.tistory.com/213)
2. docker-compose.yml 파일 위치에서 명령어 실행
```shell
docker-compose up -d
```
3. Gradle bootrun
```shell
./gradlew bootRun
```

## Swagger
url : http://localhost:8180/swagger-ui/index.html


## 프로젝트 구조
```
domain : 도메인 Controller, Service
   ㄴ common : 공통 도메인
      ㄴ controller : 공통 컨트롤러
      ㄴ service : 공통 서비스 
   ㄴ notice : 공지사항 도메인
      ㄴ controller : 공지사항 컨트롤러
         ㄴ request : 공지사항 Controller에 쓰인 Request 정보
      ㄴ service : 공지사항 서비스
global : 전체적으로 영향을 주는 설정 및 컴포넌트
   ㄴ config : 설정
   ㄴ exception : 예외 정보
   ㄴ support : 도움이되는 컴포넌트 
   ㄴ util : 유틸 객체
model : 도메인에 쓰이는 모델 정보
   ㄴ entity : Entity 정보
      ㄴ dto : DTO 정보
   ㄴ mapper : Entity <-> DTO 로 변환 정보
   ㄴ repository : Repository 정보 
```

## 문제 해결 방법
1. 첨부파일 저장 방법
   1. 첨부파일 저장 API 
   ```shell
   curl --location 'http://localhost:8180/api/file' \
   --header 'Content-Type: multipart/form-data' \
   --form 'file=@"/Users/godeokhwan/Downloads/2.png"'
   ```
   2. 경로 주소 반환
      2. 첨부파일 저장 방식
         1. FileSupport로 만들어서 다향성 추가하여 File을 올리거나 제거하는 로직 생성
         2. S3로 저장하게 하거나 하는등 작업을 바로 바꿀 수 있게 구조화하였습니다.
   3. 공지사항 Restful API
      1. 공지사항 저장 API
      ```shell
      curl --location 'http://localhost:8180/api/notice' \
      --header 'Content-Type: application/json' \
      --data '{
      "title": "제목",
      "content": "내용~~~",
      "startDate": "2024-05-06T00:45:22",
      "attachments": [
      "/Users/godeokhwan/project/rsupport/attach/1708243271941/2.png"
      ]
      }'
      ```
         - title, content 필수값 지정 
      2. 공지사항 수정 API
         ```shell
         curl --location --request PATCH 'http://localhost:8180/api/notice/11' \
         --header 'Content-Type: application/json' \
         --data '{
         "title": "제목",
         "content": "내용~~~",
         "startDate": "2024-05-06T00:45:22",
         "attachments": [
         {
         "attachmentId": 15,
         "deleteFlag": true
         },
         {
         "file": "/Users/godeokhwan/project/rsupport/attach/1708242785308/2.png"
         }
         ]
         }'
         ```
      - title, content 필수값 지정 
      - attachments 부분에서 첨부파일을 삭제를 할 경우 attachmentId와 deleteFlag true가 필수 값입니다.
      - attachments 에서 신규 추가일 경우 file 주소만 필요합니다.
      - Redis Cache 에 사용되는 값이므로 Redis에서 제거 로직 추가
      3. 공지사항 삭제 API
      ```shell
      curl --location --request DELETE 'http://localhost:8180/api/notice/12' \
      --header 'Content-Type: application/json'
      ```
      - Redis Cache 에 사용되는 값이므로 Redis에서 제거 로직 추가
      4. 공지사항 상세조회 API
      ```shell
      curl --location --request GET 'http://localhost:8180/api/notice/4' \
      --header 'Content-Type: application/json'
      ```
      - 공지사항 상세조회시 view count 증가
      - 삭제되지 않는 공지사항만 조회 가능
      - Redis Cache를 적용하여 대용량 트래픽에 대응하였습니다.
      5. 공지사항 조회 API
      ```shell
      curl --location 'http://localhost:8180/api/notice?page=1&size=20'
      ```
      - 공지사항 삭제되지 않는 리스트 페이지 조회
      