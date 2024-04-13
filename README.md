# gotogether-backend

gotogether-frontend - backend go together (springboot - rest - jpa - security) 


https://devsunset.mooo.com:8282/swagger-ui/index.html

http://193.123.252.22:8282/swagger-ui/index.html

* mariadb

  docker run --restart=always --name mariadb -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=PASSWORD -v /workspace/app/mariadb:/var/lib/mysql/ mariadb
  
  mariadb docker shell 접속
  
  docker exec -it mariadb /bin/bash
  
  mysql -u root -p
  
  select now();
  
  set global time_zone = 'Asia/Seoul';
  
  set time_zone = 'Asia/Seoul';
  
  select now();

데이터베이스 확인

      SHOW DATABASES;

데이터베이스 생성

    CREATE DATABASE gotogether;

아이디 생성

    CREATE USER 'gotogether'@'%' IDENTIFIED BY 'PASSWORD';

사용자 권한 주기

    GRANT ALL PRIVILEGES ON gotogether.* TO 'gotogether'@'%';

새로고침

    FLUSH PRIVILEGES;
    
FeeDNS (무료 도메인)

    https://freedns.afraid.org/

 FreeSSL

    https://letsencrypt.org/
    sudo apt-get update
    sudo apt-get install certbot
    sudo certbot certonly --standalone -d yourdomain.com
    sudo openssl pkcs12 -export -in /etc/letsencrypt/live/yourdomain.com/fullchain.pem -inkey /etc/letsencrypt/live/yourdomain.com/privkey.pem -out gotogether.p12 -name gotogether -CAfile /etc/letsencrypt/live/devsunset.mooo.com/chain.pem -caname root

    sudo certbot renew

사설인증서 (PROD 환경만 https 설정 - 로컬에서 PROD 환경 테스트시 사설인증서 생성하여 사용 )

    keytool -genkeypair -alias gotogether -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore gotogether.p12 -validity 3650
   
 
Build

    ./gradlew bootJar

