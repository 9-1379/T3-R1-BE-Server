# T3-R1-BE-Server

## 프로젝트 환경
```
JDK 17
SPRING BOOT 3.2.3
Ubuntu 22.04.3 LTS
MariaDB 10.11.7
```
## 프로젝트 실행 방법 (Intellij) 
1. Git clone
2. `bhr` 폴더로 이동
```
$ cd bhr
```
3. 반드시 해당 폴더에서 프로젝트 실행 (`bhr` 폴더)
4. Intellij 내의 환경 변수 세팅
   (방법 참고 : https://lahezy.tistory.com/105 )
 ```
 // JWT 
 JWT_SECRET (영어로 아무거나 길게 치면 됨) 
 // DB (자신의 MariaDB 서버를 사용하거나, 저희 팀에게 문의해 주세요)
 SPRING_DATASOURCE_DRIVER_CLASS_NAME
 SPRING_DATASOURCE_PASSWORD
 SPRING_DATASOURCE_URL
 SPRING_DATASOURCE_USERNAME
 ```
4. 프로젝트 실행 (`BhrApplication.java` 파일 내의 `main` 메소드 실행)
