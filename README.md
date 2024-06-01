## 1. 프로젝트 개요
### ✔ 프로젝트 소개
#### 배달 플랫폼 클론 코딩
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

### ✔ 가맹점 서버 API 명세
| 기능 | url |method |
| ------ | ------ | ------ |
| 회원가입 | /open-api/store-user/register |POST|
