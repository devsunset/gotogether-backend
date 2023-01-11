# gotogether-backend

gotogether-frontend - backend go together (springboot - rest - jpa - security) 


http://gotogether.ga/


* mariadb
  $ docker run --restart=always --name mariadb -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=PASSWORD -v /workspace/app/mariadb:/var/lib/mysql/ mariadb

  * mariadb docker shell 접속
  $ docker exec -it mariadb /bin/bash
  # mysql -u root -p
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
