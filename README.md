# Data Extraction

- 사용자가 전달한 쿼리를 db로 전달하여 추출 후 전달하는 서버
  - 각 사용자의 요청은 스레드로 관리
  - 요청 로그관리 / 권한관리 / 긴 수행시간 쿼리 강제종료

## API설계
  - ./설계/API설계서

## DB설계
  - ./설계/db.png

## 다이어그램
  - ![diagram](설계/diagram.png)