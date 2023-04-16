# 📖 스터디 약속을 부탁해, 스터디 타임


<p align="center">
<img src="https://user-images.githubusercontent.com/103854287/229304023-5cd76539-1c4a-450d-a79e-4d8b814c46c8.png" style="width: 50%; height: 40%;" />
</p>

<br>

### 팀원소개
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

<br>

## 🔍 개발 과정
### Back End 블로깅
- [무건 : 프로젝트를 하면서 나는 Git Flow를 어떻게 사용을 했는가?](https://pos04167.tistory.com/186)
- [무건 : JMeter를 이용한 Redis 캐싱 전략 Read 최적화](https://pos04167.tistory.com/188)
- [무건 : 나는 동시성 이슈를 어떻게 해결을 하였는가](https://pos04167.tistory.com/177)
- [무건 : Refresh Token Redis로 저장하기](https://pos04167.tistory.com/182)
- [무건 : QueryDSL Read 성능 최적화](https://pos04167.tistory.com/178)
- [무건 : Spring Security & JWT](https://pos04167.tistory.com/165)

### DevOps 블로깅
- [무건 : 백엔드 개발자는 왜 AWS를 배워야 하는가?](https://pos04167.tistory.com/167)
- [무건 : Jenkins CI/CD는 무엇인가?](https://pos04167.tistory.com/183)

<br>

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
       └── <도메인>  # 각도메인 ex : order ,ticket
             └── controller # 도메인 컨트롤러
             └── domain # 도메인 오브젝트
             └── repostiory # 도메인 리포지토리
             └── service # 도메인 서비스, 도메인 이벤트 핸들러
```

<br>

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

<br>

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

<br>


# ✍ 우리는 이렇게 Git Flow 전략을 사용하였습니다.

## ✳ [ 참고 ] [강남언니](https://blog.gangnamunni.com/post/understanding_git_flow/)

## ✳ [ 우리가 진행한 방식 ] [무건 :블로그 정리](https://pos04167.tistory.com/186)

![image](https://user-images.githubusercontent.com/103854287/232238187-1cdf646d-b632-4f6d-ae28-82de7118e6a0.png)


<br>


# 🧊 ERD Diagram
![image](https://user-images.githubusercontent.com/103854287/230743169-928d74c7-2983-4780-8bfc-a600b641f9dd.png)

<br>

# 🏛️ Architecture
![image](https://user-images.githubusercontent.com/103854287/232328175-ce12d16a-4953-4fd2-95ff-ae791ac91867.png)
