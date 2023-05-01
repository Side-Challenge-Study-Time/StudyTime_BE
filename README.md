# 📖 스터디 약속을 부탁해, 스터디 타임


<p align="center">
<img src="https://user-images.githubusercontent.com/103854287/229304023-5cd76539-1c4a-450d-a79e-4d8b814c46c8.png" style="width: 50%; height: 40%;" />
</p>

<br>


# 🐣 Project Setup
### Receive Redis Image
```
 docker image pull redis
```
### Receive Redis Create
```
 docker network create redis-network
```
### Running the Redis server
```
 docker run --name local-redis -p 6379:6379 --network redis-network -v redis_temp:/data -d redis:latest redis-server --appendonly yes
```
### Redis-cli access
```
 docker run -it --network redis-network --rm redis:latest redis-cli -h local-redis
```

# 🌞 팀원소개
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
<br>

# 🔍 개발 과정
### Back End 블로깅
- [무건 : 인덱스 적용을 통해 쿼리 성능 튜닝 후 QueryDSL 성능 159->52ms(67.29%) 성능 개선](https://pos04167.tistory.com/178)
- [무건 : JMeter를 이용한 Redis 캐싱 전략 Read 성능 TPS 15.0->38.1/sec 개선](https://pos04167.tistory.com/188)
- [무건 : 프로젝트를 하면서 나는 Git Flow를 어떻게 사용을 했는가?](https://pos04167.tistory.com/186)
- [무건 : 나는 동시성 이슈를 어떻게 해결을 하였는가](https://pos04167.tistory.com/177)
- [무건 : 스프링 Argument Resolver 중복 로직 처리](https://pos04167.tistory.com/189)
- [무건 : Refresh Token Redis로 저장하기](https://pos04167.tistory.com/182)
- [무건 : Spring Security & JWT](https://pos04167.tistory.com/165)

### DevOps 블로깅
- [무건 : 백엔드 개발자는 왜 AWS를 배워야 하는가?](https://pos04167.tistory.com/167)
- [무건 : Jenkins CI/CD는 무엇인가?](https://pos04167.tistory.com/183)
- [무건 : Jenkins Pipeline을 통한 CI/CD](https://pos04167.tistory.com/195)
- [무건 : Jenkins Item FreeStyle CI/CD](https://pos04167.tistory.com/192)

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
<br>

## 👨‍기술 스택

<h3 align="center">어플리케이션</h3>  

<p align="center">

<img src="https://img.shields.io/badge/Java 11-008FC7?style=for-the-badge&logo=Java&logoColor=white"/>
<img src="https://img.shields.io/badge/spring 2.7.9-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"/>
<img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logo=JPA&logoColor=white"/>

<img src="https://img.shields.io/badge/-QueryDSL-blue?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"/>
<img src="https://img.shields.io/badge/Junit-25A162?style=for-the-badge&logo=Junit5&logoColor=white"/>
<img src="https://img.shields.io/badge/Mockito-FF4F8B?style=flat-square&logo=Amazon CloudWatch&logoColor=white">
<img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=flat-square&logo=JSON Web Tokens&logoColor=white">

</p>


<h3 align="center">DB</h3>  

<p align="center">  
<img src="https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white"/>
<img src="https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white"/>

</p>

<h3 align="center">인프라</h3>  

<p align="center">   

<img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"/>
<img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white"/>
<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white"/>
<img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=for-the-badge&logo=Amazon RDS&logoColor=white"/>
  
</p>

<h3 align="center">문서 / 협업</h3>  

<p align="center">   
  
<img src="https://img.shields.io/badge/-Spring%20Rest%20Docs-green?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"/>
<img src="https://img.shields.io/badge/Git-F05032.svg?style=for-the-badge&logo=Git&logoColor=white"/>
<img src="https://img.shields.io/badge/GitHub-181717.svg?style=for-the-badge&logo=GitHub&logoColor=white"/>
<img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white"/>
<img src="https://img.shields.io/badge/Postman-FF6C37.svg?style=for-the-badge&logo=Postman&logoColor=white"/>
  
</p><br>

# ✅API 명세서
[API 명세서 더 보기](https://documenter.getpostman.com/view/23650109/2s93Y3tfgG)

<br>
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

## ✳ [우리가 진행한 방식](https://pos04167.tistory.com/186)

<div style="width: 50%; height: auto;">

  <img src="https://user-images.githubusercontent.com/103854287/232238187-1cdf646d-b632-4f6d-ae28-82de7118e6a0.png">
  
</div>



<br>
<br>

# 🏛️ Architecture
![image](https://user-images.githubusercontent.com/103854287/232328695-004b310c-c6b7-4fa0-849b-842292dcb471.png)

<br>
<br>

# 🧊 ERD Diagram
![image](https://user-images.githubusercontent.com/103854287/230743169-928d74c7-2983-4780-8bfc-a600b641f9dd.png)

