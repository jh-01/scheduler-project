# 📅 일정관리 어플

---
## 🔧 API 목록
| 번호 | 기능          | Method | URL                                  | request     | response    | 
|----|-------------|--------|--------------------------------------|-------------|-------------|
| 1  | 일정 생성       | POST   | /api/schedule                        | 요청 body     | 일정 등록 정보    |
| 2  | 전체 일정 조회    | GET    | /api/schedule/{loginId}              | Path        | 일정 목록       |
| 3  | 선택 일정 조회    | GET    | /api/schedule/{scheduleId}           | Path        | 단일 일정 정보    |
| 4  | 선택 일정 수정    | PUT    | /api/schedule/modify/{scheduleId}    | Path + Body | 수정된 일정 정보   |
| 5  | 선택 일정 삭제    | DELETE | /api/schedule/delete/{scheduleId}    | Path + Body | 삭제 성공 메시지   |
| 6  | 아이디 중복 확인   | GET    | /api/users/isDuplicate/{loginId}      | Path        | 사용 가능 여부    |
| 7  | 사용자 조회      | GET    | /api/users/{loginId}                 | Path        | 사용자 정보      |
| 8  | 사용자 추가      | POST   | /api/users                           | Body        | 등록된 사용자 정보  |
| 9  | 사용자 정보 수정   | PATCH    | /api/users/modify/info      | Body | 수정된 사용자 정보  |
| 10 | 사용자 비밀번호 수정 | PATCH    | /api/users/modify/password | Body | 수정된 사용자 정보  |
| 11 | 사용자 아이디 수정  | PATCH    | /api/users/modify/loginId  | Body | 수정된 사용자 정보  |
| 12 | 사용자 삭제      | DELETE | /api/users/delete         | Body | 삭제 성공 메시지   |
| 13 | 전체 사용자 조회   | GET    | /api/users                           | -           | 사용자 목록      |
    

<br>

---

## 🗂 공통 사항
+ 모든 날짜 형식: YYYY-MM-DD
+ 모든 인증 정보(예: 비밀번호)는 요청 Body에 포함

<br>

---
## 📌 API 명세 상세
### 1. 일정 생성
- 일정을 생성하는 메서드
#### POST /api/schedule

#### 🔹 Request Body
```
{
    "title": "일정 제목",
    "contents": "일정 내용",
    "user_id": "유저 아이디",
    "password": "비밀번호"
}
```

##### 🔹 Response Body
```
{
    "schedule_id": 1,
    "title": "일정 제목",
    "contents": "일정 내용",
    "user_id": "유저 아이디",
    "createDate": "작성 날짜(YYYY-MM-DD)",
    "updateDate": "수정 날짜(YYYY-MM-DD)"
}
```
<br>

---
### 2. 전체 일정 조회
#### GET /api/schedule
- 전체 일정 조회

#### 🔹 Path Parameter
| Parameter | Required/Optional | Description  | Data Type     |
|-----------|-------------------|--------------|---------------|
| user_id   | Optional          | 유저 아이디       | String        |
| since     | Optional          | 기간의 시작       | LocalDateTime |
| until     | Optional          | 기간의 끝        | LocalDateTime |

#### 🔹 Response Body
```
{
    "data" : [
        {
            "schedule_id": "일정 고유번호",
            "title": "일정 제목",
            "contents": "일정 내용",
            "user_id": "유저 아이디",
            "createDate": "작성 날짜(YYYY-MM-DD)",
            "updateDate": "수정 날짜(YYYY-MM-DD)"
        },
        {
            "schedule_id": "일정 고유번호",
            "title": "일정 제목",
            "contents": "일정 내용",
            "user_id": "유저 아이디",
            "createDate": "작성 날짜(YYYY-MM-DD)",
            "updateDate": "수정 날짜(YYYY-MM-DD)"
        },
        ...
    ]
}
```
<br>

---
### 3. 선택 일정 조회
- 선택 일정 조회
#### GET /api/schedule/{scheduleId}

#### 🔹 Path Parameter
| Parameter  | Required/Optional | Description | Data Type |
|------------|-------------------|-------------|-----------|
| scheduleId | Required          | 일정 고유번호     | Int       |


#### 🔹 Response Body
```
{
    "data" : [
        {
            "schedule_id": "일정 고유번호",
            "title": "일정 제목",
            "contents": "일정 내용",
            "user_id": "유저 아이디",
            "createDate": "작성 날짜(YYYY-MM-DD)",
            "updateDate": "수정 날짜(YYYY-MM-DD)"
        }
    ]
}
```
<br>

---
### 4. 선택 일정 수정
- 선택 일정 수정
#### PUT /api/schedule/{scheduleId}

#### 🔹 Request Body
```
{
    "password": "비밀번호",
    "data" : {
        "title": "수정된 제목",
        "contents": "수정된 내용"
    }
}
```

#### 🔹 Response Body
```
{
    "data" : [
        {
            "schedule_id": 1,
            "title": "일정 제목",
            "contents": "일정 내용",
            "user_id": "유저 아이디",
            "createDate": "작성 날짜(YYYY-MM-DD)",
            "updateDate": "수정 날짜(YYYY-MM-DD)"
        }
    ]
}
```
<br>

---
### 5. 선택 일정 삭제
- 선택 일정 삭제
#### DELETE /api/schedule/{scheduleId}

#### 🔹 Path Parameter
| Parameter   | Required/Optional | Description | Data Type |
|-------------|-------------------|-------------|-----------|
| schedule_id | Required          | 일정 고유번호     | Int       |

#### 🔹 Request Body
```
{
"password": "비밀번호"
}
```

#### 🔹 Response Body
```
{
    "message": "삭제 완료"
}
```
<br>

---
### 6. 아이디 중복 확인
- 아이디 중복 확인
#### GET /api/users/exists/{loginId}

#### 🔹 Path Parameter
| Parameter | Required/Optional | Description  | Data Type |
|-----------|-------------------|--------------|-----------|
| loginId    | Required          | 유저 아이디       | String    |


#### 🔹 Response Body
```
{
    "available": true
}
```

<br>

---
### 7. 사용자 조회
- 사용자 조회

#### GET /api/users/{loginId}

#### 🔹 Path Parameter
| Parameter | Required/Optional | Description | Data Type |
|-----------|-------------------|-------------|-----------|
| loginId    | Required          | 유저 아이디      | String    |


#### 🔹 Response Body
```
{
  "data": {
    "user_id": 1,
    "loginId": "사용자 아이디",
    "nickname": "닉네임",
    "email": "이메일",
    "createDate": "2025-05-07",
    "updateDate": "2025-05-07"
  }
}
```

<br>

---
### 8. 사용자 추가
- 사용자 추가

#### POST /api/users

#### 🔹 Request Body
```
{
    "loginId": "사용자 아이디",
    "nickname": "닉네임",
    "email": "이메일",
    "password" : "비밀번호"
}
```

#### 🔹 Response Body
```
{
  "user_id": 1,
  "loginId": "사용자 아이디",
  "email": "이메일",
  "nickname": "닉네임",
  "createDate": "2025-05-07",
  "updateDate": "2025-05-07"
}

```

<br>

---

### 9. 사용자 정보 수정
- 사용자 정보 수정

#### PUT /api/users/modify/info

#### 🔹 Request Body
```
{
    "loginId": "로그인 아이디",
    "nickname": "닉네임",
    "email": "이메일",
    "password": "비밀번호"
}
```

#### 🔹 Response Body
```
{
  "user_id": 1,
  "loginId": "사용자 아이디",
  "nickname": "닉네임",
  "email": "이메일",
  "createDate": "2025-05-07",
  "updateDate": "2025-05-07"
}
```
<br>

---

### 10. 사용자 아이디 수정
- 사용자 아이디 수정

#### PUT /api/users/modify/loginId

#### 🔹 Request Body
```
{
    "tempLoginId": "현재 로그인 아이디",
    "newLoginId": "새 로그인 아이디",
    "password": "비밀번호"
}
```

#### 🔹 Response Body
```
{
  "user_id": 1,
  "loginId": "사용자 아이디",
  "nickname": "닉네임",
  "email": "이메일",
  "createDate": "2025-05-07",
  "updateDate": "2025-05-07"
}
```
<br>

---

### 11. 사용자 비밀번호 수정
- 사용자 비밀번호 수정

#### PUT /api/users/modify/password

#### 🔹 Request Body
```
{
    "loginId": "닉네임",
    "tempPassword": "현재 비밀번호",
    "newPassword": "새 비밀번호"
}
```

#### 🔹 Response Body
```
{
  "message": "비밀번호가 성공적으로 수정되었습니다."
}
```
<br>

---

### 12. 사용자 삭제
- 사용자 삭제

#### DELETE /api/users/delete

#### 🔹 Request Body
```
{
    "loginId": "로그인 아이디",
    "password": "비밀번호"
}
```

#### 🔹 Response Body
```
{
    "message": "삭제 완료"
}
```
<br>

---
### 13. 전체 사용자 조회
-  전체 사용자 조회

#### GET /api/users

#### 🔹 Response Body
```
{
    "data" : [
        {
            "user_id": 1
            "loginId": "유저 아이디",
            "nickname": "닉네임",
            "email": "이메일",
            "createDate": "작성 날짜(YYYY-MM-DD)",
            "updateDate": "수정 날짜(YYYY-MM-DD)"
        },
        {
            "user_id": 2
            "login_id": "유저 아이디",
            "nickname": "닉네임",
            "email": "이메일",
            "createDate": "작성 날짜(YYYY-MM-DD)",
            "updateDate": "수정 날짜(YYYY-MM-DD)"
        },
        ...
    ]
}
```
 
<br>

---
## ERD
![Image](https://github.com/user-attachments/assets/0ea0721d-ca23-4212-9ef9-fd4745c21588)



