# 구현범위

과제 4개에 해당하는 기능
카테고리 저장, 조회
브랜드 저장, 조회
상품 저장, 조회
카테고리, 브랜드 당 하나의 상품만 존재함
controller 의 단위테스트 (mock 테스트)
service 의 단위테스트 (mock 테스트)
datasource 를 reader, writer 로 구분
swagger-ui 적용

# 실행방법

./gradlew product-api:bootRun --args=--spring.profiles.active=local

# swagger 접속

http://localhost:8080/docs/swagger

# 테스트

./gradlew test

# 추가해야 할 사항들

* cache 기능 추가 필요
* 최고가, 최저가를 조회해 오는 기능에서 성능 이슈가 발생한다면 multi thread 를 사용해서 구현할 필요도 있어 보임