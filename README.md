# 📖 스터디 약속을 부탁해, 스터디 타임


<p align="center">
<img src="https://user-images.githubusercontent.com/103854287/229304023-5cd76539-1c4a-450d-a79e-4d8b814c46c8.png" style="width: 50%; height: 40%;" />
</p>

## 🎉프로젝트 소개
> 모두를 위한 스터디 예약, 스터디 타임!• <b>백엔드</b> 레포지토리


## 😀팀원소개
<table>
  <tr>
    <td>
         <img src="https://user-images.githubusercontent.com/103854287/211192470-8aa1b1b8-0547-4da4-b674-3e08778bdf98.png" width="100px" />
    </td>
     <td>
         <img src="https://user-images.githubusercontent.com/103854287/211192470-8aa1b1b8-0547-4da4-b674-3e08778bdf98.png" width="100px" />
    </td>
  </tr>
  <tr>
    <td><b>김무건</b></td>
    <td><b>이광민</b></td>
  </tr>
</table>

## 🔍 개발 과정
- [무건 : Spring Security & JWT](https://pos04167.tistory.com)
- [무건 : 동시성 이슈](https://pos04167.tistory.com/177)
- [무건 : QueryDSL Read 성능 최적화](https://pos04167.tistory.com/178)
- [무건 : Refresh Token Redis로 저장하기](https://pos04167.tistory.com)
- [무건 : Data JPA N+1 이슈 해결하기](https://pos04167.tistory.com)



### 프로젝트 구조도
```bash
src
├── global 
│   ├── exception # 도메인별 에러 정의
│   ├── config
│   ├── redis
│   ├── util
│   ├── initializer
│   └── jwt 
│ 
└── Domain   
       └── domains 
           └── <도메인>  # 각도메인 ex : order ,ticket
             └── controller # 도메인 컨트롤러
             └── domain # 도메인 오브젝트
             └── repostiory # 도메인 리포지토리
             └── service # 도메인 서비스, 도메인 이벤트 핸들러
```

# 👨‍🔧기술 스택
<div align="left">
<div>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white">
<img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white">
</div>

<div>
<img src="https://img.shields.io/badge/MySQL-4479A1.svg?style=flat-square&logo=MySQL&logoColor=white">
<img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Redis&logoColor=white">
</div>

<div>
<img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=flat-square&logo=Amazon AWS&logoColor=white">
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white">
<img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=flat-square&logo=JSON Web Tokens&logoColor=white">
</div>

<div>
<img src="https://img.shields.io/badge/JUnit5-F3702A?style=flat-square&logo=#25A162&logoColor=white">
<img src="https://img.shields.io/badge/Mockito-FF4F8B?style=flat-square&logo=Amazon CloudWatch&logoColor=white">
<img src="https://img.shields.io/badge/Slack-4A154B?style=flat-square&logo=slack&logoColor=white">
</div>

</div>


# 🐌Git Commit Convention
<table>
  <tr>
    <td>
         ✨feat
    </td>
     <td>
        새로운 기능과 관련된 것을 의미
    </td>
  </tr>
  <tr>
    <td>
         🐛fix
    </td>
     <td>
        오류와 같은 것을 수정을 하였을 때 사용
    </td>
  </tr>
   <tr>
    <td>
         ✅test
    </td>
     <td>
        테스트를 추가하거나 수정
    </td>
  </tr>
  <tr>
    <td>
         📝docs
    </td>
     <td>
        문서와 관련하여 수정한 부분이 있을 때 사용
    </td>
  </tr>
    <tr>
    <td>
         🔥move
    </td>
     <td>
        파일, 코드의 이동
    </td>
  </tr>
    <tr>
    <td>
         💚build
    </td>
     <td>
         빌드 관련 파일을 수정
    </td>
  </tr>
    <tr>
    <td>
         ♻️refactor
    </td>
     <td>
       코드의 리팩토링을 의미
    </td>
  </tr>
</table>


# ✍ 프로젝트 목표
1. 문서화를 통한 협업

2. 테스트 코드

3. Git-Flow


# 🧊 ERD Diagram
![image](https://user-images.githubusercontent.com/103854287/230743169-928d74c7-2983-4780-8bfc-a600b641f9dd.png)


# 🏛️ Architecture
