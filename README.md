
### DB 구조
* user_search_keyword
  * keyword_no 검색어 번호(PK)
  * keyword 검색어
* user_search_keyword_history
  * seq 히스토리 번호 (PK)
  * keyword_no 검색어 번호 
  * search_date 검색 날짜

### Caffeine Cache
* 최근 검색된 검색어의 번호를 로컬캐시에 저장하는 용도로 사용

### Query DSL
* 인기 검색어 목록 조회 시, Spring JPA 로 구현하기 다소 복잡하여 queryDSL 사용

