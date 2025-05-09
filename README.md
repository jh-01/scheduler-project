# 📅 일정관리 어플

---
## 🔧 API 목록
| 번호  | 기능         | Method  | URL                            | request         | response     | 
|-----|------------|---------|--------------------------------|-----------------|--------------|
| 1   | 일정 생성      | POST    | /api/schedule                  | 요청 body         | 일정 등록 정보     |
| 2   | 전체 일정 조회   | GET     | /api/schedule/{user_id}         | Path            | 일정 목록        |
| 3   | 선택 일정 조회   | GET     | /api/schedule/{scheduleId}     | Path            | 단일 일정 정보     |
| 4   | 선택 일정 수정   | PUT     | /api/schedule/{scheduleId}     | Path + Body     | 수정된 일정 정보    |
| 5   | 선택 일정 삭제   | DELETE  | /api/schedule/{scheduleId}     | Path + Body     | 삭제 성공 메시지    |
| 6   | 아이디 중복 확인  | GET     | /api/users/exists/{user_id}     | Path            | 사용 가능 여부     |
| 7   | 사용자 조회     | GET     | /api/users/{user_id}            | Path            | 사용자 정보       |
| 8   | 사용자 추가     | POST    | /api/users                     | Body            | 등록된 사용자 정보   |
| 9   | 사용자 정보 수정  | PUT     | /api/users/{user_id}            | Path + Body     | 수정된 사용자 정보   |
| 10  | 사용자 삭제     | DELETE  | /api/users/{user_id}            | Path + Body     | 삭제 성공 메시지    |
| 11  | 전체 사용자 조회  | GET     | /api/users                     | -               | 사용자 목록       |
| 12  | 비밀번호 검증    | POST    | /api/users/verifyPass/{user_id} | Path + Body     | 비밀번호 검증 메시지  |         

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
    "content": "일정 내용",
    "user_id": "유저 아이디",
    "password": "비밀번호"
}
```

##### 🔹 Response Body
```
{
    "id": 1,
    "title": "일정 제목",
    "content": "일정 내용",
    "user_id": "유저 아이디",
    "createDate": "작성 날짜(YYYY-MM-DD)",
    "updateDate": "수정 날짜(YYYY-MM-DD)"
}
```
<br>

---
### 2. 전체 일정 조회
#### GET /api/schedule/{user_id}
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
            "id": "일정 고유번호",
            "title": "일정 제목",
            "content": "일정 내용",
            "user_id": "유저 아이디",
            "createDate": "작성 날짜(YYYY-MM-DD)",
            "updateDate": "수정 날짜(YYYY-MM-DD)"
        },
        {
            "id": "일정 고유번호",
            "title": "일정 제목",
            "content": "일정 내용",
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
            "id": "일정 고유번호",
            "title": "일정 제목",
            "content": "일정 내용",
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

#### 🔹 Path Parameter
| Parameter  | Required/Optional | Description | Data Type |
|------------|-------------------|-------------|-----------|
| scheduleId | Required          | 일정 고유번호     | Int       |

#### 🔹 Request Body
```
{
    "password": "비밀번호",
    "data" : {
        "title": "수정된 제목",
        "content": "수정된 내용"
    }
}
```

#### 🔹 Response Body
```
{
    "data" : [
        {
            "id": 1,
            "title": "일정 제목",
            "content": "일정 내용",
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
| Parameter  | Required/Optional | Description | Data Type |
|------------|-------------------|-------------|-----------|
| scheduleId | Required          | 일정 고유번호     | Int       |

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
#### GET /api/users/exists/{user_id}

#### 🔹 Path Parameter
| Parameter | Required/Optional | Description  | Data Type |
|-----------|-------------------|--------------|-----------|
| user_id    | Required          | 유저 아이디       | String    |


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

#### GET /api/users/{user_id}

#### 🔹 Path Parameter
| Parameter | Required/Optional | Description | Data Type |
|-----------|-------------------|-------------|-----------|
| user_id    | Required          | 유저 아이디      | String    |


#### 🔹 Response Body
```
{
  "data": {
    "id": 1,
    "user_id": "사용자 아이디",
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
    "user_id": "사용자 아이디",
    "email": "이메일"
}
```

#### 🔹 Response Body
```
{
  "id": 1,
  "user_id": "사용자 아이디",
  "createDate": "2025-05-07",
  "updateDate": "2025-05-07"
}

```

<br>

---

### 9. 사용자 정보 수정
- 사용자 정보 수정

#### PUT /api/users/{user_id}

#### 🔹 Path Parameter
| Parameter | Required/Optional | Description | Data Type |
|-----------|-------------------|-------------|-----------|
| user_id    | Required          | 사용자 아이디     | String    |

#### 🔹 Request Body
```
{
    "email": "이메일",
    "password": "비밀번호"
}
```

#### 🔹 Response Body
```
{
  "id": 1,
  "user_id": "사용자 아이디",
  "createDate": "2025-05-07",
  "updateDate": "2025-05-07"
}
```
<br>

---

### 10. 사용자 삭제
- 사용자 삭제

#### DELETE /api/users/{user_id}

#### 🔹 Path Parameter
| Parameter  | Required/Optional | Description | Data Type |
|------------| --- |-------------|-----------|
| user_id | Required | 유저 아이디      | String    |

#### 🔹 Request Body
```
{
"password": "비밀번호"
}
```

#### 🔹 Response Body
```
{
    "message": "유저 삭제 성공"
}
```
<br>

---
### 11. 전체 사용자 조회
-  전체 사용자 조회

#### GET /api/users

#### 🔹 Response Body
```
{
    "data" : [
        {
            "id": 1
            "user_id": "유저 아이디",
            "createDate": "작성 날짜(YYYY-MM-DD)",
            "updateDate": "수정 날짜(YYYY-MM-DD)"
        },
        {
            "id": 2
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
### 12. 비밀번호 검증
-  비밀번호 검증

#### POST /api/users/verifyPass/{user_id}

#### 🔹 Path Parameter
| Parameter  | Required/Optional | Description | Data Type |
|------------| --- |-------------|-----------|
| user_id | Required | 유저 아이디      | String    |

#### 🔹 Request Body
```
{
"password": "비밀번호"
}
```

#### 🔹 Response Body
```
{
  "valid": false,
  "message": "비밀번호가 일치하지 않습니다."
}
```

<br>

---
## ERD
![Image](https://github.com/user-attachments/assets/0ea0721d-ca23-4212-9ef9-fd4745c21588)



