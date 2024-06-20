## 1. 프로젝트 개요
### ✔ 프로젝트 소개
#### 배달 플랫폼 클론 코딩
배포 주소 : http://www.my-delivery.store
---
## 2. 서비스
### ✔ API 명세
| 기능 | url |method |
| ------ | ------ | ------ |
| 회원가입 | /open-api/user/register |POST|
| 로그인 | /open-api/user/login |POST|
| 로그인 유저 정보 |/api/user/me |GET|
| 가게 등록 | /open-api/store/register |POST|
| 가게 조회 | /api/store/search |GET|
| 가게 메뉴 등록 | /open-api/store-menu/register |POST|
| 가게 메뉴 조회 | /api/store-menu/search |GET|
| 주문 | /api/user-order |POST|
| 특정 주문 조회 | /api/user-order/id/{orderId} |GET|
| 진행중인 주문 목록 조회 | /api/user-order/current |GET|
| 완료된 주문 목록 조회 | /api/user-order/history |GET|
| 리뷰 작성 | /api/review/register |GET|
| 리뷰 조회 | /api/review |GET|
| 리뷰 수정 | /api/review/id/{id}|GET|
---
### ✔ erd
![erd](https://github.com/uniqquej/delivery_service/assets/109218139/36ac76a6-dc58-43f2-8e85-8c3cd301425d)


### ✔ 개발환경
- <img src="https://img.shields.io/badge/Framework-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/3.2.2-515151?style=for-the-badge">
- <img src="https://img.shields.io/badge/Language-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/java-%23ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"><img src="https://img.shields.io/badge/17-515151?style=for-the-badge">

## 3. 동작 화면
### 고객사이트 주요 기능
![고객 로그인 1](https://github.com/uniqquej/delivery_service/assets/109218139/470fdcef-dea9-4d06-ad54-f2c0a98bec17)
![고객 로그인 2](https://github.com/uniqquej/delivery_service/assets/109218139/5b83490c-f661-430f-9a17-2287b24c29c2)
---
### 가맹점 사이트 주문 알림, 수락 (SSE 사용, 비동기 주문 알림 가능)
![가맹점 주문 수락](https://github.com/uniqquej/delivery_service/assets/109218139/11c76821-1426-45bd-90e6-3dcf96d63ae3)
![주문 수락 후 리뷰](https://github.com/uniqquej/delivery_service/assets/109218139/8152877c-9ae5-4981-a1dd-50cc7dc45e4d)
---
### 가맹점 메뉴 등록
![메뉴 등록](https://github.com/uniqquej/delivery_service/assets/109218139/2de94ebe-0b99-4409-a137-ba3559e48998)
---
### 주문 상태에 따른 고객 리뷰 작성
![리뷰 수정](https://github.com/uniqquej/delivery_service/assets/109218139/0a54a0a5-43e0-4f05-839e-508fc056fd14)
![마무리](https://github.com/uniqquej/delivery_service/assets/109218139/c5890396-5af6-4eea-9ced-9aefd3c4137f)
